package project.com.newsikdang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference restaurantRef, reviewRef, userRef;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ReviewAdapter reviewAdapter;

    List<Review> listReview;

    TextView tvResName, tvResAddress, tvResPhone;
    TextView tvDate, tvHeart, tvReview;

    TextView tvReviewCnt;
    EditText etReview;
    Button btnHeart,btnSimple,btnDetail,btnReview;

    RelativeLayout rlDetail, rlMenu;
    TableLayout tlTag;
    ImageView ivDetailMore, ivMenuMore, ivTagMore;

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

        final int detailHeight = rlDetail.getHeight();
        final int menuHeight = rlMenu.getHeight();
        final int tagHeight = tlTag.getHeight();

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
                button.setSelected(!button.isSelected());
                Animation rotate, scale;
                if (button.isSelected()) {
                    //slide up
                    rotate = AnimationUtils.loadAnimation(RestaurantActivity.this,R.anim.rotate_up);
                    scale = AnimationUtils.loadAnimation(RestaurantActivity.this,R.anim.scale_down);
                    Log.d("Scale", "rlDetail height " + rlDetail.getHeight());

                } else {
                    //slide down
                    rotate = AnimationUtils.loadAnimation(RestaurantActivity.this,R.anim.rotate_down);
                    scale = AnimationUtils.loadAnimation(RestaurantActivity.this,R.anim.scale_up);
                    Log.d("Scale", "rlDetail height " + rlDetail.getHeight());
                }
                if (rotate!=null && scale!=null) {
                    button.startAnimation(rotate);
                    rlDetail.startAnimation(scale);
                }
            }
        });
        ivMenuMore = findViewById(R.id.iv_res_menu_more);
        ivTagMore = findViewById(R.id.iv_res_hashtag_more);

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

        etReview = findViewById(R.id.et_review);
        btnReview = findViewById(R.id.btn_review);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stReview = etReview.getText().toString();
                if (stReview.equals("")) {
                    Toast.makeText(RestaurantActivity.this, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RestaurantActivity.this, "리뷰가 등록되었습니다", Toast.LENGTH_SHORT).show();

                    addReview(stReview);
                }
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

    private void addReview(final String review) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(new Date());

        Hashtable<String, String> reviewInfo = new Hashtable<String, String>();
        reviewInfo.put("restaurant", resKey);
        reviewInfo.put("uid", user.getUid());
        reviewInfo.put("name", user.getDisplayName());
        reviewInfo.put("context", review);
        reviewInfo.put("date", formattedDate);

        final String revkey = reviewRef.push().getKey();
        reviewRef.child(revkey).setValue(reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    etReview.setText("");
                    review_cnt += 1;
                    tvReviewCnt.setText(String.valueOf(review_cnt));

                    Review newReview = new Review(revkey, resKey, user.getUid(), user.getDisplayName(), review, formattedDate);
                    listReview.add(newReview);
                    reviewAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(RestaurantActivity.this,"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        restaurantRef.child("review").child(revkey).setValue(true);
        userRef.child("review").child(revkey).setValue(true);
    }

    class MoreAnimation extends Animation {
        RelativeLayout rl_view;
        int initialHeight;
        boolean show;

        public MoreAnimation(RelativeLayout view, int height, boolean show) {
            this.rl_view = view;
            this.initialHeight = height;
            this.show = show;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;

            if (!show) { newHeight = (int)(initialHeight * (1-interpolatedTime)); }
            else { newHeight = (int)(initialHeight * interpolatedTime); }

            rl_view.removeAllViews();
            rl_view.getLayoutParams().height = newHeight;
            rl_view.requestLayout();
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
