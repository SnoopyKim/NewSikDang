package project.com.newsikdang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantActivity";

    FirebaseUser user;
    DatabaseReference restaurantRef, reviewRef, userRef;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ReviewAdapter reviewAdapter;

    List<Review> listReview;

    TextView tvResName, tvResAddress, tvResPhone;
    TextView tvDate, tvHeart, tvReview;

    TextView tvReviewCnt;
    Button btnHeart,btnSimple,btnDetail;

    RelativeLayout rlDetail, rlMenu;
    TableLayout tlTag;
    ImageView ivDetailMore, ivMenuMore, ivTagMore, ivReview;

    int detailHeight, menuHeight, tagHeight;

    String resKey;

    long heart_cnt, review_cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        user = FirebaseAuth.getInstance().getCurrentUser();

        tvResName = findViewById(R.id.tv_res_name);
        tvResAddress = findViewById(R.id.tv_res_address);
        tvResPhone = findViewById(R.id.tv_res_phone);
        tvDate = findViewById(R.id.tv_res_day);
        tvHeart = findViewById(R.id.tv_res_heart);
        tvReview = findViewById(R.id.tv_res_review_cnt);

        rlDetail = findViewById(R.id.rl_res_detail_view);
        rlMenu = findViewById(R.id.rl_res_menu_view);
        tlTag = findViewById(R.id.tl_res_hashtag);

        rlDetail.measure(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        detailHeight = rlDetail.getMeasuredHeight();
        rlMenu.measure(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        menuHeight = rlMenu.getMeasuredHeight();
        tlTag.measure(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        tagHeight = tlTag.getMeasuredHeight();

        resKey = getIntent().getStringExtra("resKey");

        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000").child(resKey);
        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000");
        userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvResName.setText(dataSnapshot.child("name").getValue().toString());
                tvResAddress.setText(dataSnapshot.child("address").getValue().toString());
                if (dataSnapshot.child("tel").getValue().toString().equals("")) { tvResPhone.setText("정보 없음"); }
                else { tvResPhone.setText(dataSnapshot.child("tel").getValue().toString()); }

                Calendar now = Calendar.getInstance();
                int date = Integer.parseInt(dataSnapshot.child("date").getValue().toString());
                Calendar date_cal = Calendar.getInstance();
                date_cal.set(date/10000,(date/100)%100-1,date%100);
                long dday = (now.getTimeInMillis()-date_cal.getTimeInMillis()) / (1000*60*60*24);
                tvDate.setText(String.valueOf(dday));

                heart_cnt = dataSnapshot.child("heart").getChildrenCount();
                tvHeart.setText(String.valueOf(heart_cnt));

                review_cnt = dataSnapshot.child("review").getChildrenCount();
                tvReview.setText(String.valueOf(review_cnt));
                tvReviewCnt.setText(String.valueOf(review_cnt));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        userRef.child("heart").child(resKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { btnHeart.setSelected(true); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        btnHeart = findViewById(R.id.btn_res_heart);
        btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    restaurantRef.child(resKey).child("heart").child(user.getUid()).setValue(true);
                    userRef.child("heart").child(resKey).setValue(true);
                    heart_cnt += 1;
                    tvHeart.setText(String.valueOf(heart_cnt));
                    Toast.makeText(RestaurantActivity.this,"좋아요 목록에 추가되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    restaurantRef.child(resKey).child("heart").child(user.getUid()).removeValue();
                    userRef.child("heart").child(resKey).removeValue();
                    heart_cnt -= 1;
                    tvHeart.setText(String.valueOf(heart_cnt));
                    Toast.makeText(RestaurantActivity.this,"좋아요 목록에서 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivDetailMore = findViewById(R.id.iv_res_detail_more);
        ivDetailMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                foldLayout(button, rlDetail);
            }
        });
        ivMenuMore = findViewById(R.id.iv_res_menu_more);
        ivMenuMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                foldLayout(button, rlMenu);
            }
        });
        ivTagMore = findViewById(R.id.iv_res_hashtag_more);
        ivTagMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                foldLayout(button, tlTag);
            }
        });

        tvReviewCnt = findViewById(R.id.tv_rev_cnt);
        btnSimple = findViewById(R.id.btn_rev_simple);
        btnSimple.setSelected(true);
        btnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btnSimple.isSelected()) {
                    btnSimple.setSelected(true);
                    btnSimple.setTextColor(getResources().getColor(R.color.white));
                    btnDetail.setSelected(false);
                    btnDetail.setTextColor(getResources().getColor(R.color.textBtn));
                }
            }
        });
        btnDetail = findViewById(R.id.btn_rev_detail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btnDetail.isSelected()) {
                    btnDetail.setSelected(true);
                    btnDetail.setTextColor(getResources().getColor(R.color.white));
                    btnSimple.setSelected(false);
                    btnSimple.setTextColor(getResources().getColor(R.color.textBtn));
                }
            }
        });

        ivReview = findViewById(R.id.icon_review);
        ivReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ReviewActivity.class);
                intent.putExtra("resName", tvResName.getText());
                intent.putExtra("resKey", resKey);
                startActivity(intent);
            }
        });

        //RecyclerView 사용하기 위한 사전 작업 (크기 고정, 어댑터 설정 등등)
        recyclerView = findViewById(R.id.rv_res_review);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listReview = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(listReview, this, false);
        recyclerView.setAdapter(reviewAdapter);

        Query reviewQuery = reviewRef.orderByChild("restaurant").equalTo(resKey);
        reviewQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        String revKey = dataSnapshot2.getKey();
                        String uid = dataSnapshot2.child("uid").getValue().toString();
                        String name = dataSnapshot2.child("name").getValue().toString();
                        String text = dataSnapshot2.child("context").getValue().toString();
                        String date = dataSnapshot2.child("date").getValue().toString();
                        Review review = new Review(revKey,resKey,uid,name,text,date);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        listReview.add(review);
                    }
                    Collections.reverse(listReview);
                    reviewAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        detailHeight = rlDetail.getHeight();
        menuHeight = rlMenu.getHeight();
        tagHeight = tlTag.getHeight();
    }

    public void foldLayout(View button, final View layout) {
        button.setSelected(!button.isSelected());

        Animation rotate;
        if (button.isSelected()) {
            //slide up
            rotate = AnimationUtils.loadAnimation(RestaurantActivity.this,R.anim.rotate_up);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }
                @Override
                public void onAnimationEnd(Animation animation) { layout.setVisibility(View.GONE); }
                @Override
                public void onAnimationRepeat(Animation animation) { }
            });

        } else {
            //slide down
            rotate = AnimationUtils.loadAnimation(RestaurantActivity.this,R.anim.rotate_down);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }
                @Override
                public void onAnimationEnd(Animation animation) { layout.setVisibility(View.VISIBLE); }
                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        }

        button.startAnimation(rotate);

    }
}
