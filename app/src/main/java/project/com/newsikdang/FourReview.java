package project.com.newsikdang;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FourReview extends AppCompatActivity {
    FirebaseUser user;
    DatabaseReference userRef, reviewRef;

    RecyclerView mRecyclerView;
    ReviewAdapter reviewAdapter;
    LinearLayoutManager mLayoutManager;

    ArrayList<Review> listReview = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourreview);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("users").child("customer").child(user.getUid());
        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000");

        Query query = reviewRef.orderByChild("uid").equalTo(user.getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    boolean detail = Boolean.valueOf(data.child("detail").getValue().toString());
                    String revKey = data.getKey();
                    String resKey = data.child("restaurant").getValue().toString();
                    String uid = data.child("uid").getValue().toString();
                    String name = data.child("name").getValue().toString();
                    String profile = data.child("uphoto").getValue().toString();
                    String text = data.child("context").getValue().toString();
                    String date = data.child("date").getValue().toString();
                    float star = Float.valueOf(data.child("star_main").getValue().toString());

                    Review review;
                    if (!detail) {
                        review = new Review(revKey, resKey, uid, name, profile, text, date, star, 0);
                    } else {
                        float star_t = Float.valueOf(data.child("star_taste").getValue().toString());
                        float star_c = Float.valueOf(data.child("star_cost").getValue().toString());
                        float star_s = Float.valueOf(data.child("star_service").getValue().toString());
                        float star_a = Float.valueOf(data.child("star_ambiance").getValue().toString());
                        List<String> photo = new ArrayList<>();
                        for (DataSnapshot photoData : data.child("photo").getChildren()) {
                            photo.add(photoData.getValue().toString());
                        }
                        review = new Review(revKey, resKey, uid, name, profile, text, photo, date, star, star_t, star_c, star_s, star_a, 0);
                    }

                    listReview.add(review);
                }
                Collections.sort(listReview, new ReviewComparator());
                reviewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        mRecyclerView = findViewById(R.id.review_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // LinearLayout으로 설정
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Animation Defualt 설정
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Decoration 설정
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
        // Adapter 생성
        reviewAdapter = new ReviewAdapter(listReview, this, true);
        mRecyclerView.setAdapter(reviewAdapter);

    }

    public class ReviewComparator implements Comparator<Review> {
        @Override
        public int compare(Review r1, Review r2) {
            return r2.getDate().compareTo(r1.getDate());
        }
    }
}
