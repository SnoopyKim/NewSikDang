package project.com.newsikdang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Restaurant extends AppCompatActivity {

    FirebaseUser user;
    DatabaseReference restaurantRef;
    DatabaseReference reviewRef;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ReviewAdapter reviewAdapter;

    List<Review> listReview;

    TextView tvResName, tvResAddress, tvResPhone;

    EditText etReview;
    Button btnReview;

    String resKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        user = FirebaseAuth.getInstance().getCurrentUser();

        tvResName = findViewById(R.id.res_name);
        tvResAddress = findViewById(R.id.res_address);
        tvResPhone = findViewById(R.id.res_number);

        resKey = getIntent().getStringExtra("key");

        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000").child(resKey);
        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("304000");

        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvResName.setText(dataSnapshot.child("name").getValue().toString());
                tvResAddress.setText(dataSnapshot.child("address").getValue().toString());
                tvResPhone.setText(dataSnapshot.child("tel").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        etReview = findViewById(R.id.et_review);
        btnReview = findViewById(R.id.btn_review);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stReview = etReview.getText().toString();
                if (stReview.equals("")) {
                    Toast.makeText(Restaurant.this, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Restaurant.this, stReview, Toast.LENGTH_SHORT).show();
                    addReview(stReview);
                }
            }
        });

        //RecyclerView 사용하기 위한 사전 작업 (크기 고정, 어댑터 설정 등등)
        recyclerView = (RecyclerView) findViewById(R.id.rv_review);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        listReview = new ArrayList<>();
        reviewAdapter = new ReviewAdapter(listReview, this);
        recyclerView.setAdapter(reviewAdapter);

        Query reviewQuery = reviewRef.orderByChild("restaurant").equalTo(resKey);
        reviewQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                        String key = dataSnapshot2.getKey();
                        String email = dataSnapshot2.child("email").getValue().toString();
                        String name = dataSnapshot2.child("name").getValue().toString();
                        String text = dataSnapshot2.child("context").getValue().toString();
                        Review review = new Review(key,email,name,text);

                        // [START_EXCLUDE]
                        // Update RecyclerView
                        listReview.add(review);
                    }
                    reviewAdapter.notifyDataSetChanged();
                } else {
                    //리뷰데이터 없음
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
        reviewInfo.put("email", user.getEmail());
        reviewInfo.put("name", user.getDisplayName());
        reviewInfo.put("context", review);
        reviewInfo.put("date", formattedDate);

        final String revkey = reviewRef.push().getKey();
        reviewRef.child(revkey).setValue(reviewInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    etReview.setText("");

                    Review newReview = new Review(revkey, user.getEmail(), user.getDisplayName(), review, formattedDate);
                    listReview.add(newReview);
                    reviewAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(Restaurant.this,"리뷰 작성에 실패했습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
