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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import java.util.Comparator;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantActivity";

    FirebaseUser user;
    DatabaseReference restaurantRef, reviewRef, userRef;

    LinearLayout photoLayout;
    RelativeLayout photoNone;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ReviewAdapter reviewAdapter;

    List<String> photoList;
    List<Review> listReview;

    ImageView ivBack, ivSetting;

    TextView tvResName, tvCategory, tvResAddress, tvResPhone;
    TextView tvTime, tvTimeBreak, tvLastOrder, tvDayoff, tvParking, tvToilet;
    TextView tvDate, tvHeart, tvReview;

    TextView tvReviewCnt;
    Button btnHeart, btnSimple, btnDetail;

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

        ivBack = findViewById(R.id.iv_res_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivSetting = findViewById(R.id.iv_res_setting);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                intent.putExtra("resKey", resKey);
                intent.putStringArrayListExtra("photoList", (ArrayList<String>)photoList);
                intent.putExtra("resName", tvResName.getText());
                intent.putExtra("category", tvCategory.getText());
                intent.putExtra("address", tvResAddress.getText());
                intent.putExtra("tel", tvResPhone.getText());
                intent.putExtra("time", tvTime.getText());
                intent.putExtra("dayoff", tvDayoff.getText());
                intent.putExtra("last", tvLastOrder.getText());
                intent.putExtra("parking", tvParking.getText());
                intent.putExtra("toilet", tvToilet.getText());
                startActivity(intent);
            }
        });
        String userType = getIntent().getStringExtra("userType");
        if (userType != null) {
            //업주 사용자
            ivBack.setVisibility(View.GONE);
        } else {
            //일반 사용자
            ivSetting.setVisibility(View.GONE);
        }

        photoLayout = findViewById(R.id.ll_res_img);
        photoNone = findViewById(R.id.rl_res_img_none);
        photoList = new ArrayList<>();

        tvResName = findViewById(R.id.tv_res_name);
        tvCategory = findViewById(R.id.tv_res_category);
        tvResAddress = findViewById(R.id.tv_res_address);
        tvResPhone = findViewById(R.id.tv_res_phone);

        tvTime = findViewById(R.id.tv_res_time_value);
        tvDayoff = findViewById(R.id.tv_res_dayoff_value);
        tvLastOrder = findViewById(R.id.tv_res_last_value);
        tvParking = findViewById(R.id.tv_res_parking_value);
        tvToilet = findViewById(R.id.tv_res_toilet_value);

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
        userRef = FirebaseDatabase.getInstance().getReference("users").child("customer").child(user.getUid());

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
                    reviewAdapter.filter_detail(false);
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
                    reviewAdapter.filter_detail(true);
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        photoList.clear();
        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("photo").exists()) {
                    photoNone.setVisibility(View.GONE);
                    for (DataSnapshot data : dataSnapshot.child("photo").getChildren()) {
                        photoList.add(data.getValue().toString());
                        loadImages(data.getValue().toString());
                    }
                }

                tvResName.setText(dataSnapshot.child("name").getValue().toString());
                tvCategory.setText(dataSnapshot.child("category").getValue().toString());
                tvResAddress.setText(dataSnapshot.child("address").getValue().toString());
                if (dataSnapshot.child("tel").getValue().toString().equals("")) { tvResPhone.setText("정보 없음"); }
                else { tvResPhone.setText(dataSnapshot.child("tel").getValue().toString()); }

                if (dataSnapshot.child("detail").child("time").exists()) {
                    tvTime.setText(dataSnapshot.child("detail").child("time").getValue().toString());
                } else { tvTime.setText("정보 없음"); }
                if (dataSnapshot.child("detail").child("dayoff").exists()) {
                    tvDayoff.setText(dataSnapshot.child("detail").child("dayoff").getValue().toString());
                } else { tvDayoff.setText("정보 없음"); }
                if (dataSnapshot.child("detail").child("last").exists()) {
                    tvLastOrder.setText(dataSnapshot.child("detail").child("last").getValue().toString());
                } else { tvLastOrder.setText("정보 없음"); }
                if (dataSnapshot.child("detail").child("parking").exists()) {
                    tvParking.setText(dataSnapshot.child("detail").child("parking").getValue().toString());
                } else { tvParking.setText("정보 없음"); }
                if (dataSnapshot.child("detail").child("toilet").exists()) {
                    tvToilet.setText(dataSnapshot.child("detail").child("toilet").getValue().toString());
                } else { tvToilet.setText("정보 없음"); }

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

        loadReviews();
    }

    public void loadImages(String photo) {
        ImageView image = new ImageView(this);
        int size = photoLayout.getHeight();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size,size);
        lp.setMarginStart(5);
        image.setLayoutParams(lp);
        image.setScaleType(ImageView.ScaleType.FIT_CENTER);
        //add your drawable here like this image.setImageResource(R.drawable.redeight)or set like this imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        Glide.with(RestaurantActivity.this).load(photo).into(image);
        //set removeListener
        // Adds the view to the layout
        photoLayout.addView(image);
    }

    public void loadReviews() {
        listReview = new ArrayList<>();
        Query reviewQuery = reviewRef.orderByChild("restaurant").equalTo(resKey);
        reviewQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    long l_simple = 0, l_detail = 0;
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        boolean detail = Boolean.valueOf(dataSnapshot2.child("detail").getValue().toString());

                        String revKey = dataSnapshot2.getKey();
                        String uid = dataSnapshot2.child("uid").getValue().toString();
                        String name = dataSnapshot2.child("name").getValue().toString();
                        String profile = dataSnapshot2.child("uphoto").getValue().toString();
                        String text = dataSnapshot2.child("context").getValue().toString();
                        String date = dataSnapshot2.child("date").getValue().toString();
                        float star = Float.valueOf(dataSnapshot2.child("star_main").getValue().toString());

                        Review review;
                        if (!detail) {
                            l_simple += 1;
                            review = new Review(revKey, resKey, uid, name, profile, text, date, star, 0);
                        } else {
                            l_detail += 1;
                            float star_t = Float.valueOf(dataSnapshot2.child("star_taste").getValue().toString());
                            float star_c = Float.valueOf(dataSnapshot2.child("star_cost").getValue().toString());
                            float star_s = Float.valueOf(dataSnapshot2.child("star_service").getValue().toString());
                            float star_a = Float.valueOf(dataSnapshot2.child("star_ambiance").getValue().toString());
                            List<String> photo = new ArrayList<>();
                            for (DataSnapshot photoData : dataSnapshot2.child("photo").getChildren()) {
                                photo.add(photoData.getValue().toString());
                            }
                            review = new Review(revKey, resKey, uid, name, profile, text, photo, date, star, star_t, star_c, star_s, star_a, 0);
                        }

                        listReview.add(review);
                    }
                    tvReviewCnt.setText(String.valueOf(l_simple+l_detail));
                    btnSimple.setText("한줄리뷰 " + l_simple);
                    btnDetail.setText("상세리뷰 " + l_detail);

                    Collections.sort(listReview, new ReviewComparator());

                    reviewAdapter = new ReviewAdapter(listReview, getApplicationContext(), false);
                    recyclerView.setAdapter(reviewAdapter);

                    reviewAdapter.filter_detail(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
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

    public class ReviewComparator implements Comparator<Review> {
        @Override
        public int compare(Review r1, Review r2) {
            return r2.getDate().compareTo(r1.getDate());
        }
    }
}
