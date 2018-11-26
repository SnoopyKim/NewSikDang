package project.com.newsikdang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    FirebaseUser user;
    DatabaseReference reviewRef, restaurantRef, userRef;

    Button btnExit, btnSubmit;

    TextView tvResName;
    TextView tvTabSimple, tvTabDetail;

    EditText etContext;

    RelativeLayout rlStarDetail, rlImage;

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
                String stContext = etContext.getText().toString();
                addReview(stContext);
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
