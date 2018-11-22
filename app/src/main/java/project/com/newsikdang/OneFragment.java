package project.com.newsikdang;


import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

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
import java.util.List;

public class OneFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "OneFragment";

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference restaurantsRef;

    Button btn1;

    RecyclerView mRecyclerView;
    RestaurantAdapter resAdapter;
    LinearLayoutManager mLayoutManager;

    String stCGG = "3040000";
    ArrayList<Restaurant> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.onefragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(user.getUid());
        restaurantsRef = database.getReference("restaurants");

        btn1 = v.findViewById(R.id.btn1);
        btn1.setText(stCGG);
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

                            items.add(new Restaurant(stResKey, stResName, stResAddress, "", stDate, 0, 0 , 0 ));
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

        mRecyclerView = v.findViewById(R.id.recycler1);
        mLayoutManager = new LinearLayoutManager(this.getContext());
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
        resAdapter = new RestaurantAdapter(items, getContext());
        mRecyclerView.setAdapter(resAdapter);

//        스피너
        Spinner spinner = v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return v;
    }
    @Override
    public void onClick(View v) {
    }
}
