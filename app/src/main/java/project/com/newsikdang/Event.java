package project.com.newsikdang;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Event extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference restaurantsRef;

    RecyclerView mRecyclerView;
    EventAdapter resAdapter;
    LinearLayoutManager mLayoutManager;

    String stCGG = "3040000";
    ArrayList<Restaurant> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("customer").child(user.getUid());
        restaurantsRef = database.getReference("restaurants");

        userRef.child("block").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> blockList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    blockList.add(data.getKey());
                }
                Query query = restaurantsRef.child(stCGG).orderByChild("date");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            if (blockList.indexOf(data.getKey()) != -1) { continue; }
                            String stResKey = data.getKey();
                            String stResName = data.child("name").getValue().toString();
                            String stResAddress = data.child("address").getValue().toString();
                            String stDate = data.child("date").getValue().toString();
                            long l_heart = data.child("heart").getChildrenCount();
                            long l_review = data.child("review").getChildrenCount();

                            items.add(new Restaurant(stResKey, stResName, stResAddress, "", stDate, 0, l_heart , l_review ));
                        }
                        Collections.reverse(items);
                        resAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        mRecyclerView = findViewById(R.id.event_recycler);
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
        resAdapter = new EventAdapter(items, this);
        mRecyclerView.setAdapter(resAdapter);

    }
}
