package project.com.newsikdang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FourFragment extends Fragment implements View.OnClickListener {

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    ImageButton ibSetting;
    TextView tvUserName, tvUserEmail;
    public static final int sub = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourfragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(user.getUid());

        ibSetting = v.findViewById(R.id.ibSetting);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
            }
        });

        tvUserName = v.findViewById(R.id.frag4_username);
        tvUserName.setText(user.getDisplayName());
        tvUserEmail = v.findViewById(R.id.frag4_useremail);
        tvUserEmail.setText(user.getEmail());

//        내쿠폰함
        RelativeLayout llayout = v.findViewById(R.id.frag4_coupon);
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
    public void onClick(View v) {
    }
}