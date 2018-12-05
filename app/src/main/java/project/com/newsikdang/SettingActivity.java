package project.com.newsikdang;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";

    DatabaseReference resRef;
    StorageReference storageRef;

    ImageView ivBack;
    TextView tvTitle;

    LinearLayout photoLayout, menuPhotoLayout;

    EditText etName, etCategory, etAddress, etTel, etTime, etTimeBreak, etLast, etDayoff, etEvent;
    Spinner spParking, spToilet;

    RecyclerView rvMenu;
    LinearLayoutManager layoutManager;
    MenuAdapter menuAdapter;

    ArrayList<Menu> menuList;
    List<Uri> listPhoto = new ArrayList<>();
    List<Uri> listMenuPhoto = new ArrayList<>();

    boolean check_detail=false, check_photo = false, check_menu = false, check_menuPhoto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent data = getIntent();

        resRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000").child(data.getStringExtra("resKey"));
        storageRef = FirebaseStorage.getInstance().getReference("restaurants").child("304000").child(data.getStringExtra("resKey"));

        ivBack = findViewById(R.id.iv_setting_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvTitle = findViewById(R.id.tv_setting_title);
        tvTitle.setText(data.getStringExtra("resName"));

        photoLayout = findViewById(R.id.ll_setting_img);
        menuPhotoLayout = findViewById(R.id.ll_setting_menu_img);
        ArrayList<String> photoList = (ArrayList<String>)data.getSerializableExtra("photoList");
        for (String photo : photoList) { loadImages(photoLayout, photo); }
        menuList = (ArrayList<Menu>)data.getSerializableExtra("menuList");
        ArrayList<String> menuPhotoList = (ArrayList<String>)data.getSerializableExtra("menuPhotoList");
        for (String photo : menuPhotoList) { loadImages(menuPhotoLayout, photo); }

        etName = findViewById(R.id.et_setting_res_name);
        etName.setText(data.getStringExtra("resName"));
        etCategory = findViewById(R.id.et_setting_category);
        etCategory.setText(data.getStringExtra("category"));
        etAddress = findViewById(R.id.et_setting_address);
        etAddress.setText(data.getStringExtra("address"));
        etTel = findViewById(R.id.et_setting_tel);
        etTel.setText(data.getStringExtra("tel"));
        etTime = findViewById(R.id.et_setting_time);
        etTime.setText(data.getStringExtra("time"));
        etTimeBreak = findViewById(R.id.et_setting_break);
        etLast = findViewById(R.id.et_setting_last);
        etLast.setText(data.getStringExtra("last"));
        etDayoff = findViewById(R.id.et_setting_dayoff);
        etDayoff.setText(data.getStringExtra("dayoff"));
        etEvent = findViewById(R.id.et_setting_event);
        etEvent.setText(data.getStringExtra("event"));

        spParking = findViewById(R.id.sp_setting_parking);
        List<String> parkingList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.parking)));
        SpinnerAdapter parkingAdapter = new SpinnerAdapter(this, parkingList);
        spParking.setAdapter(parkingAdapter);
        spParking.setSelection(parkingAdapter.getItemposition(data.getStringExtra("parking")));

        spToilet = findViewById(R.id.sp_setting_toilet);
        List<String> toiletList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.toilet)));
        SpinnerAdapter toiletAdapter = new SpinnerAdapter(this, toiletList);
        spToilet.setAdapter(toiletAdapter);
        spToilet.setSelection(toiletAdapter.getItemposition(data.getStringExtra("toilet")));

        Button btnAddPhoto = findViewById(R.id.btn_setting_add_img);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "사진을 고르세요"), 1);
            }
        });

        Button btnAddMenuPhoto = findViewById(R.id.btn_setting_add_menu_img);
        btnAddMenuPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "사진을 고르세요"), 2);
            }
        });

        rvMenu = findViewById(R.id.rv_setting_menu);
        layoutManager = new LinearLayoutManager(this);
        rvMenu.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(menuList, this, true);
        rvMenu.setAdapter(menuAdapter);

        Button btnAddMenu = findViewById(R.id.btn_setting_add_menu);
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuList.add(new Menu("",""));
                menuAdapter.notifyItemInserted(menuList.size()-1);
                Log.d(TAG, "onClick: menuList: " + menuList);
                rvMenu.invalidate();
            }
        });

        Button btnApply = findViewById(R.id.btn_setting_apply);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode: " + requestCode + " resultCode: " + resultCode + " data: " + data);
        if (requestCode==1 && data!=null) {
            final LinearLayout layout = findViewById(R.id.ll_setting_img);
            ClipData clipData = data.getClipData();
            int size = layout.getHeight();
            if (clipData == null) {
                final Uri fileUri = data.getData();
                listPhoto.add(fileUri);

                ImageView image = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size,size);
                lp.setMarginStart(10);
                image.setLayoutParams(lp);
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //add your drawable here like this image.setImageResource(R.drawable.redeight)or set like this imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                image.setImageURI(fileUri);
                //set removeListener
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listPhoto.remove(fileUri);
                        layout.removeView(view);
                    }
                });
                // Adds the view to the layout
                layout.addView(image);

            } else {
                int totalItemSelected = clipData.getItemCount();
                Log.d(TAG, "onActivityResult: totalItemSelected = " + totalItemSelected);

                for (int i = 0; i < totalItemSelected; i++) {
                    final Uri fileUri = clipData.getItemAt(i).getUri();
                    listPhoto.add(fileUri);

                    ImageView image = new ImageView(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size,size);
                    lp.setMarginStart(10);
                    image.setLayoutParams(lp);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    //add your drawable here like this image.setImageResource(R.drawable.redeight)or set like this imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    image.setImageURI(fileUri);
                    //set removeListener
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listPhoto.remove(fileUri);
                            layout.removeView(view);
                        }
                    });
                    // Adds the view to the layout
                    layout.addView(image);
                }
            }
        } else if (requestCode==2 && data!=null) {
            final LinearLayout layout = findViewById(R.id.ll_setting_menu_img);
            ClipData clipData = data.getClipData();
            int size = layout.getHeight();
            if (clipData == null) {
                final Uri fileUri = data.getData();
                listMenuPhoto.add(fileUri);

                ImageView image = new ImageView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size,size);
                lp.setMarginStart(10);
                image.setLayoutParams(lp);
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //add your drawable here like this image.setImageResource(R.drawable.redeight)or set like this imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                image.setImageURI(fileUri);
                //set removeListener
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listMenuPhoto.remove(fileUri);
                        layout.removeView(view);
                    }
                });
                // Adds the view to the layout
                layout.addView(image);

            } else {
                int totalItemSelected = clipData.getItemCount();
                Log.d(TAG, "onActivityResult: totalItemSelected = " + totalItemSelected);

                for (int i = 0; i < totalItemSelected; i++) {
                    final Uri fileUri = clipData.getItemAt(i).getUri();
                    listMenuPhoto.add(fileUri);

                    ImageView image = new ImageView(this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size,size);
                    lp.setMarginStart(10);
                    image.setLayoutParams(lp);
                    image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    //add your drawable here like this image.setImageResource(R.drawable.redeight)or set like this imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    image.setImageURI(fileUri);
                    //set removeListener
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listMenuPhoto.remove(fileUri);
                            layout.removeView(view);
                        }
                    });
                    // Adds the view to the layout
                    layout.addView(image);
                }
            }
        }
    }

    public void loadImages(LinearLayout layout, String photo) {
        ImageView image = new ImageView(this);
        int size = layout.getHeight();
        if (layout == photoLayout) { size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics()); }
        else if (layout == menuPhotoLayout) { size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 120, getResources().getDisplayMetrics()); }
        Log.d(TAG, "loadImages: size: "+size);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size,size);
        lp.setMarginStart(5);
        image.setLayoutParams(lp);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //add your drawable here like this image.setImageResource(R.drawable.redeight)or set like this imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        Log.d(TAG, "loadImages: photo"+photo);
        Glide.with(SettingActivity.this).load(photo).into(image);
        //set removeListener
        // Adds the view to the layout
        layout.addView(image);
    }
    private void loadData() {
        resRef.child("name").setValue(etName.getText().toString());
        resRef.child("category").setValue(etCategory.getText().toString());
        resRef.child("address").setValue(etAddress.getText().toString());
        resRef.child("tel").setValue(etTel.getText().toString());
        if (etEvent.getText().toString().equals("")) { resRef.child("event").removeValue(); }
        else { resRef.child("event").setValue(etEvent.getText().toString()); }

        Hashtable<String, String> resDetailInfo = new Hashtable<String, String>();
        resDetailInfo.put("time", etTime.getText().toString());
        resDetailInfo.put("break", etTimeBreak.getText().toString());
        resDetailInfo.put("last", etLast.getText().toString());
        resDetailInfo.put("dayoff", etDayoff.getText().toString());
        resDetailInfo.put("parking", spParking.getSelectedItem().toString());
        resDetailInfo.put("toilet", spToilet.getSelectedItem().toString());
        resRef.child("detail").setValue(resDetailInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                check_detail = true;
                Log.d(TAG, "onSuccess: detail");
                if (check_menu && check_photo && check_menuPhoto) { finish(); }
            }
        });


        if (menuList.size() == 0) { check_menu = true; }
        if (listPhoto.size() == 0) { check_photo = true; }
        if (listMenuPhoto.size() == 0) { check_menuPhoto = true; }

        for (int i=0; i<menuList.size(); i++) {
            final int f_i = i;
            Hashtable<String,String> menuInfo = new Hashtable<>();
            menuInfo.put("name",menuList.get(i).getName());
            menuInfo.put("cost",menuList.get(i).getCost());
            resRef.child("menu").child(String.valueOf(i)).setValue(menuInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    if (f_i == menuList.size()-1) {
                        check_menu = true;
                        Log.d(TAG, "onSuccess: menu");
                        if (check_detail && check_photo && check_menuPhoto) { finish();}
                    }
                }
            });
        }
        final List<String> listPhotoString = new ArrayList<>();
        for (int i = 0; i<listPhoto.size(); i++) {
            final int index = i;
            storageRef.child(String.valueOf(i)).putFile(listPhoto.get(i)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (!task.isSuccessful()) { Toast.makeText(getApplicationContext(),"업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show(); return; }
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (!task.isSuccessful()) { Toast.makeText(getApplicationContext(),"업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show(); return; }
                            String stUri = task.getResult().toString();
                            listPhotoString.add(stUri);
                            if (index == listPhoto.size()-1) {
                                resRef.child("photo").setValue(listPhotoString).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            check_photo = true;
                                            Log.d(TAG, "onSuccess: photo");
                                            if (check_detail && check_menu && check_menuPhoto) { finish(); }
                                        } else {
                                            Toast.makeText(getApplicationContext(),"업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
        final List<String> listMenuPhotoString = new ArrayList<>();
        for (int i = 0; i<listMenuPhoto.size(); i++) {
            final int index = i;
            storageRef.child("menu").child(String.valueOf(i)).putFile(listMenuPhoto.get(i)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (!task.isSuccessful()) { Toast.makeText(getApplicationContext(),"업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show(); return; }
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (!task.isSuccessful()) { Toast.makeText(getApplicationContext(),"업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show(); return; }
                            String stUri = task.getResult().toString();
                            listMenuPhotoString.add(stUri);
                            if (index == listMenuPhoto.size()-1) {
                                resRef.child("menuPhoto").setValue(listMenuPhotoString).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            check_menuPhoto = true;
                                            Log.d(TAG, "onSuccess: menuPhoto");
                                            if (check_detail && check_menu && check_photo) { finish(); }
                                        } else {
                                            Toast.makeText(getApplicationContext(),"업데이트에 실패했습니다.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
    }
}
