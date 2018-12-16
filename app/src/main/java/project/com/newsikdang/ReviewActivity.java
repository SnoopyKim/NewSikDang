package project.com.newsikdang;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    private static final String TAG = "ReviewActivity";

    FirebaseUser user;
    DatabaseReference reviewRef, restaurantRef, userRef, hashRef;
    StorageReference storageRef;

    Button btnExit, btnSubmit, btnAddimg;

    TextView tvResName;
    TextView tvTabSimple, tvTabDetail;

    EditText etContext;

    RelativeLayout rlStarDetail, rlImage;

    RatingBar rbMain, rbTaste, rbService, rbCost, rbAmbiance;

    String resKey;

    List<Uri> listPhoto = new ArrayList<>();

    boolean detail=false, check_info=false, check_tag=false;

    int hashTagIsComing = 0;
    float star;
    long rev_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        resKey = getIntent().getStringExtra("resKey");

        user = FirebaseAuth.getInstance().getCurrentUser();

        hashRef = FirebaseDatabase.getInstance().getReference("hashtags");
        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000").child(resKey);
        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000");
        userRef = FirebaseDatabase.getInstance().getReference("users").child("customer").child(user.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("reviews");

        btnExit = findViewById(R.id.btn_rev_exit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSubmit = findViewById(R.id.btn_rev_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detail) { addDetailReview(); }
                else { addSimpleReview(); }
            }
        });

        tvResName = findViewById(R.id.tv_rev_resName);
        tvResName.setText(getIntent().getStringExtra("resName"));

        etContext = findViewById(R.id.et_rev_context);
        etContext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String startChar;
                try{ startChar = Character.toString(s.charAt(start)); }
                catch(Exception ex){ startChar = ""; }
                if (startChar.equals("#")) {
                    etContext.getText().setSpan(new ForegroundColorSpan(getColor(R.color.btnAbled)), start, start+count, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    hashTagIsComing++;
                }
                if (startChar.equals(" ")){ hashTagIsComing = 0; }
                if (hashTagIsComing != 0) {
                    etContext.getText().setSpan(new ForegroundColorSpan(getColor(R.color.btnAbled)), start, start+count, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    hashTagIsComing++;
                }
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        tvTabSimple = findViewById(R.id.tv_rev_tab_simple);
        tvTabSimple.setSelected(true);
        tvTabSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTabDetail.isSelected()) {
                    detail = false;

                    tvTabSimple.setSelected(true);
                    tvTabSimple.setTextColor(getColor(R.color.btnAbled));
                    tvTabDetail.setSelected(false);
                    tvTabDetail.setTextColor(getColor(R.color.textBtn));
                    rlStarDetail.setVisibility(View.GONE);
                    rlImage.setVisibility(View.GONE);
                }
            }
        });
        tvTabDetail = findViewById(R.id.tv_rev_tab_detail);
        tvTabDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTabSimple.isSelected()) {
                    detail = true;

                    tvTabDetail.setSelected(true);
                    tvTabDetail.setTextColor(getColor(R.color.btnAbled));
                    tvTabSimple.setSelected(false);
                    tvTabSimple.setTextColor(getColor(R.color.textBtn));
                    rlStarDetail.setVisibility(View.VISIBLE);
                    rlImage.setVisibility(View.VISIBLE);
                }
            }
        });

        rlStarDetail = findViewById(R.id.rl_rev_star_detail);
        rlStarDetail.setVisibility(View.GONE);
        rlImage = findViewById(R.id.rl_rev_img);
        rlImage.setVisibility(View.GONE);

        rbMain = findViewById(R.id.rb_rev_star_main);
        rbTaste = findViewById(R.id.rb_rev_taste);
        rbCost = findViewById(R.id.rb_rev_cost);
        rbService = findViewById(R.id.rb_rev_service);
        rbAmbiance = findViewById(R.id.rb_rev_ambiance);

        btnAddimg = findViewById(R.id.btn_rev_add_img);
        btnAddimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "사진을 고르세요"), 1);
            }
        });

        restaurantRef.child("review").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { rev_cnt = dataSnapshot.getChildrenCount(); }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        restaurantRef.child("star").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    star = Float.valueOf(dataSnapshot.getValue().toString());
                } else { star = 0; }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "requestCode: " + requestCode + " resultCode: " + resultCode + " data: " + data);
        if (requestCode == 1 && data!=null) {
            final LinearLayout layout = findViewById(R.id.ll_rev_img);
            ClipData clipData = data.getClipData();
            int size = rlImage.getWidth();
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
                    Glide.with(getApplicationContext()).load(fileUri).into(image);
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
        }
    }

    private void addSimpleReview() {

        String stUid = user.getUid();
        String stName = user.getDisplayName();
        String stPhoto = String.valueOf(user.getPhotoUrl());
        String stContext = etContext.getText().toString();
        if (stContext.equals("")) {
            Toast.makeText(getApplicationContext(), "리뷰 내용을 작성해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] words = stContext.split(" ");
        ArrayList<String> hashtagList = new ArrayList<>();
        for (String word : words) {
            if (word.charAt(0)=='#') { hashtagList.add(word.substring(1)); }
        }

        float rating = rbMain.getRating();
        if (rating == 0) {
            Toast.makeText(getApplicationContext(), "별점 평가를 진행해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(new Date());

        Hashtable<String, String> reviewInfo = new Hashtable<String, String>();
        reviewInfo.put("restaurant", resKey);
        reviewInfo.put("detail", "false");
        reviewInfo.put("uid", stUid);
        reviewInfo.put("uphoto", stPhoto);
        reviewInfo.put("name", stName);
        reviewInfo.put("star_main", String.valueOf(rating));
        reviewInfo.put("context", stContext);
        reviewInfo.put("date", formattedDate);

        final String revkey = reviewRef.push().getKey();
        reviewRef.child(revkey).setValue(reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    check_info = true;
                    if (check_tag) {
                        Toast.makeText(getApplicationContext(), "리뷰를 등록했습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        for (String tag : hashtagList) {
            hashRef.child(tag).push().setValue(revkey);
            Log.d(TAG, "addSimpleReview: tag: " + tag);
            if (tag.equals(hashtagList.get(hashtagList.size()-1))) {
                restaurantRef.child("hashtag").child(tag).push().setValue(revkey).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        check_tag = true;
                        if (check_info) {
                            Toast.makeText(getApplicationContext(), "리뷰를 등록했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            } else { restaurantRef.child("hashtag").child(tag).push().setValue(revkey); }
        }
        float new_star = (star*rev_cnt+rating) / (rev_cnt+1);
        restaurantRef.child("star").setValue(new_star);
        restaurantRef.child("review").child(revkey).setValue(true);
        userRef.child("review").child(revkey).setValue(true);

    }
    private void addDetailReview() {

        String stUid = user.getUid();
        String stName = user.getDisplayName();
        String stPhoto = String.valueOf(user.getPhotoUrl());
        String stContext = etContext.getText().toString();
        if (stContext.equals("")) {
            Toast.makeText(getApplicationContext(), "리뷰 내용을 작성해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] words = stContext.split(" ");
        ArrayList<String> hashtagList = new ArrayList<>();
        for (String word : words) {
            if (word.charAt(0)=='#') { hashtagList.add(word.substring(1)); }
        }

        float rating_main = rbMain.getRating();
        float rating_taste = rbTaste.getRating();
        float rating_cost = rbCost.getRating();
        float rating_service = rbService.getRating();
        float rating_ambiance = rbAmbiance.getRating();
        if (rating_main==0 || rating_taste==0 || rating_cost==0 || rating_service==0 || rating_ambiance==0) {
            Toast.makeText(getApplicationContext(), "별점 평가를 진행해주세요", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listPhoto.isEmpty()) {
            Toast.makeText(getApplicationContext(), "이미지를 첨부해주세요", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(new Date());

        Hashtable<String, String> reviewInfo = new Hashtable<String, String>();
        reviewInfo.put("restaurant", resKey);
        reviewInfo.put("detail", "true");
        reviewInfo.put("uid", stUid);
        reviewInfo.put("uphoto", stPhoto);
        reviewInfo.put("name", stName);
        reviewInfo.put("star_main", String.valueOf(rating_main));
        reviewInfo.put("star_taste", String.valueOf(rating_taste));
        reviewInfo.put("star_cost", String.valueOf(rating_cost));
        reviewInfo.put("star_service", String.valueOf(rating_service));
        reviewInfo.put("star_ambiance", String.valueOf(rating_ambiance));
        reviewInfo.put("context", stContext);
        reviewInfo.put("date", formattedDate);

        final String revkey = reviewRef.push().getKey();
        final List<String> listPhotoString = new ArrayList<>();
        for (int i = 0; i<listPhoto.size(); i++) {
            final int index = i;
            storageRef.child(revkey).child(String.valueOf(i)).putFile(listPhoto.get(i)).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (!task.isSuccessful()) { Toast.makeText(getApplicationContext(),"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show(); return; }
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (!task.isSuccessful()) { Toast.makeText(getApplicationContext(),"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show(); return; }
                            String stUri = task.getResult().toString();
                            listPhotoString.add(stUri);
                            if (index == listPhoto.size()-1) {
                                reviewRef.child(revkey).child("photo").setValue(listPhotoString).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            check_info = true;
                                            if (check_tag) {
                                                Toast.makeText(getApplicationContext(), "리뷰를 등록했습니다.", Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(),"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }
        if (hashtagList.size() == 0)  { check_tag = true; }
        for (String tag : hashtagList) {
            hashRef.child(tag).push().setValue(revkey);
            Log.d(TAG, "addSimpleReview: tag: " + tag);
            if (tag.equals(hashtagList.get(hashtagList.size()-1))) {
                restaurantRef.child("hashtag").child(tag).push().setValue(revkey).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        check_tag = true;
                        if (check_info) {
                            Toast.makeText(getApplicationContext(), "리뷰를 등록했습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            } else { restaurantRef.child("hashtag").child(tag).push().setValue(revkey); }
        }
        float new_star = (star*rev_cnt+rating_main) / (rev_cnt+1);
        restaurantRef.child("star").setValue(new_star);
        reviewRef.child(revkey).setValue(reviewInfo);
        restaurantRef.child("review").child(revkey).setValue(true);
        userRef.child("review").child(revkey).setValue(true);
    }
}
