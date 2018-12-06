package project.com.newsikdang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FourFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private StorageReference storageRef;

    CircleImageView ivProfile;
    TextView tvUserName, tvUserEmail, tvLogout;
    TextView tvLikeCnt, tvUnlikeCnt, tvReviewCnt;
    public static final int sub = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourfragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("customer").child(user.getUid());
        storageRef = FirebaseStorage.getInstance().getReference("users").child(user.getUid()).child("profile.png");


        ivProfile = v.findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imageIntent = new Intent();
                imageIntent.addCategory(Intent.CATEGORY_OPENABLE);
                imageIntent.setType("image/*");
                imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imageIntent, "사진을 선택하세요"), 0);

            }
        });
        if (user.getPhotoUrl()!=null) {
            Glide.with(FourFragment.this).load(user.getPhotoUrl()).into(ivProfile);
        }

        tvUserName = v.findViewById(R.id.frag4_username);
        tvUserName.setText(user.getDisplayName());
        tvUserEmail = v.findViewById(R.id.frag4_useremail);
        tvUserEmail.setText(user.getEmail());
        tvLogout = v.findViewById(R.id.tv_logout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
                Intent intent = new Intent(getActivity(),IntroActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        tvLikeCnt = v.findViewById(R.id.tv_like_cnt);
        tvUnlikeCnt = v.findViewById(R.id.tv_unlike_cnt);
        tvReviewCnt = v.findViewById(R.id.tv_review_cnt);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvLikeCnt.setText(String.valueOf(dataSnapshot.child("heart").getChildrenCount()));
                tvUnlikeCnt.setText(String.valueOf(dataSnapshot.child("block").getChildrenCount()));
                tvReviewCnt.setText(String.valueOf(dataSnapshot.child("review").getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        //관심목록
        RelativeLayout rl_att_1 = v.findViewById(R.id.attention_1);
        rl_att_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AreaActivity.class);
                startActivity(intent);
            }
        });

//        내쿠폰함
        ImageView llayout = v.findViewById(R.id.frag4_coupon);
        llayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FourCoupon.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
            }
        });

//        좋아요 목록
        final LinearLayout llayout1 = v.findViewById(R.id.frag4_like);
        llayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FourLike.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
            }
        });
        llayout1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        llayout1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        llayout1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });

//        싫어요목록
        final LinearLayout llayout2 = v.findViewById(R.id.frag4_unlike);
        llayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FourUnlike.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
            }
        });
        llayout2.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        llayout2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        llayout2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });



//        내리뷰목록
        final LinearLayout llayout3 = v.findViewById(R.id.frag4_review);
        llayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FourReview.class);
                startActivityForResult(intent,sub);//액티비티 띄우기
            }
        });
        llayout3.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        llayout3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        llayout3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });

        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //저장소(갤러리)에서 선택한 사진을 bitmap형식으로 바꿔 그려주고 uploadImage함수 호출
        if (requestCode==0 && data != null) {
            Uri image = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);

                uploadImage(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * @Name    uploadImage
     * @Usage   upload image(JPEG) local to server
     * @Param   layout inflater,viewgroup bundle
     * @return  void
     * */
    public void uploadImage(final Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("업로드중...");
        progressDialog.show();

        //가공한 사진 데이터를 Firebase 내 저장소에 등록(올리기)
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                //올려진 사진 데이터를 저장소에서 Uri-String형식으로 받은 후 DB에 저장
                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        userRef.child("photo").setValue(String.valueOf(downloadUrl));

                        //선택했던 사진을 Firebase 계정에 PhotoUri로 설정
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(downloadUrl).build();
                        user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "사진 업로드가 잘 됐습니다", Toast.LENGTH_SHORT).show();
                                    Glide.with(FourFragment.this).load(user.getPhotoUrl()).into(ivProfile);
                                } else {
                                    Toast.makeText(getApplicationContext(), "사진 업로드에 실패했습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}