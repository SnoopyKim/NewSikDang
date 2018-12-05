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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FourUnlike extends AppCompatActivity {
    private final String TAG = "FourUnlike";

    FirebaseUser user;
    DatabaseReference userRef, restaurantRef;

    RecyclerView mRecyclerView;
    RestaurantAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    ArrayList<Restaurant> listUnlike = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourunlike);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userRef = FirebaseDatabase.getInstance().getReference("users").child("customer").child(user.getUid());
        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000");

        userRef.child("block").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    restaurantRef.child(data.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String stResKey = dataSnapshot.getKey();
                                String stResName = dataSnapshot.child("name").getValue().toString();
                                String stResAddress = dataSnapshot.child("address").getValue().toString();
                                String stDate = dataSnapshot.child("date").getValue().toString();
                                long l_heart = dataSnapshot.child("heart").getChildrenCount();
                                long l_review = dataSnapshot.child("review").getChildrenCount();
                                float star;
                                if (dataSnapshot.child("star").exists()) {
                                    star = Float.valueOf(dataSnapshot.child("star").getValue().toString());
                                } else { star = 0; }
                                String stPhoto;
                                if (dataSnapshot.child("photo").exists()) {
                                    stPhoto = dataSnapshot.child("photo").child(String.valueOf(0)).getValue().toString();
                                } else { stPhoto = ""; }
                                boolean event;
                                if (dataSnapshot.child("event").exists()) {
                                    event = true;
                                } else { event = false; }
                                listUnlike.add(new Restaurant(stResKey, stResName, stResAddress, stPhoto, stDate, star, l_heart, l_review, event));
                                mAdapter.notifyItemInserted(mAdapter.getItemCount());
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.unlike_recycler);
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
        mAdapter = new RestaurantAdapter(listUnlike, this);
        mRecyclerView.setAdapter(mAdapter);

    }
}