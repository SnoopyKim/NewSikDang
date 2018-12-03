package project.com.newsikdang;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView tvTitle;

    EditText etName, etCategory, etAddress, etTel, etTime, etTimeBreak, etLast, etDayoff;
    Spinner spParking, spToilet;

    List<Uri> listPhoto = new ArrayList<>();
    List<Uri> listMenuPhoto = new ArrayList<>();

    boolean check_photo = false, check_menu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent data = getIntent();

        resRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000").child(data.getStringExtra("resKey"));
        storageRef = FirebaseStorage.getInstance().getReference("restaurants").child("304000").child(data.getStringExtra("resKey"));

        tvTitle = findViewById(R.id.tv_setting_title);
        tvTitle.setText(data.getStringExtra("resName"));

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

    private void loadData() {
        resRef.child("name").setValue(etName.getText().toString());
        resRef.child("category").setValue(etCategory.getText().toString());
        resRef.child("address").setValue(etAddress.getText().toString());
        resRef.child("tel").setValue(etTel.getText().toString());

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
                if (check_photo && check_menu) {
                    finish();
                }
            }
        });

        if (listPhoto.size() == 0) { check_photo = true; }
        if (listMenuPhoto.size() == 0) { check_menu = true; }

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
                                            if (check_menu) { finish(); }
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
            storageRef.child(String.valueOf(i)).putFile(listMenuPhoto.get(i)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                resRef.child("photo").setValue(listMenuPhotoString).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            check_menu = true;
                                            if (check_photo) { finish(); }
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
