package project.com.newsikdang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class ReviewActivity extends AppCompatActivity {
    private static final String TAG = "ReviewActivity";

    FirebaseUser user;
    DatabaseReference reviewRef, restaurantRef, userRef;

    Button btnExit, btnSubmit;

    TextView tvResName;
    TextView tvTabSimple, tvTabDetail;

    EditText etContext;

    RelativeLayout rlStarDetail, rlImage;

    RatingBar rbMain, rbTaste, rbService, rbCost, rbAmbiance;

    String resKey;

    boolean detail = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        resKey = getIntent().getStringExtra("resKey");

        user = FirebaseAuth.getInstance().getCurrentUser();

        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000").child(resKey);
        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000");
        userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());

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

    }

    private void addSimpleReview() {

        String stUid = user.getUid();
        String stName = user.getDisplayName();
        String stContext = etContext.getText().toString();
        if (stContext.equals("")) {
            Toast.makeText(getApplicationContext(), "리뷰 내용을 작성해주세요", Toast.LENGTH_SHORT).show();
            return;
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
        reviewInfo.put("name", stName);
        reviewInfo.put("star_main", String.valueOf(rating));
        reviewInfo.put("context", stContext);
        reviewInfo.put("date", formattedDate);

        final String revkey = reviewRef.push().getKey();
        reviewRef.child(revkey).setValue(reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"리뷰를 등록했습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        restaurantRef.child("review").child(revkey).setValue(true);
        userRef.child("review").child(revkey).setValue(true);

    }
    private void addDetailReview() {

        String stUid = user.getUid();
        String stName = user.getDisplayName();
        String stContext = etContext.getText().toString();
        if (stContext.equals("")) {
            Toast.makeText(getApplicationContext(), "리뷰 내용을 작성해주세요", Toast.LENGTH_SHORT).show();
            return;
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

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String formattedDate = df.format(new Date());

        Hashtable<String, String> reviewInfo = new Hashtable<String, String>();
        reviewInfo.put("restaurant", resKey);
        reviewInfo.put("detail", "true");
        reviewInfo.put("uid", stUid);
        reviewInfo.put("name", stName);
        reviewInfo.put("star_main", String.valueOf(rating_main));
        reviewInfo.put("star_taste", String.valueOf(rating_taste));
        reviewInfo.put("star_cost", String.valueOf(rating_cost));
        reviewInfo.put("star_service", String.valueOf(rating_service));
        reviewInfo.put("star_ambiance", String.valueOf(rating_ambiance));
        reviewInfo.put("context", stContext);
        reviewInfo.put("date", formattedDate);

        final String revkey = reviewRef.push().getKey();
        reviewRef.child(revkey).setValue(reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"리뷰를 등록했습니다.",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        restaurantRef.child("review").child(revkey).setValue(true);
        userRef.child("review").child(revkey).setValue(true);
    }
}
