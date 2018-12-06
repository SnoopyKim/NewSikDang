package project.com.newsikdang;


import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

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

import static com.facebook.FacebookSdk.getApplicationContext;

public class OneFragment extends Fragment implements View.OnClickListener {
    private final String TAG = "OneFragment";

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference restaurantsRef;

    Button btn1, btn2, btn3, btn4; //관심구역 1,2,3,4
    ImageView event_button1;

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
        userRef = database.getReference("users").child("customer").child(user.getUid());
        restaurantsRef = database.getReference("restaurants");

        btn1 = v.findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = v.findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = v.findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = v.findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        event_button1 = v.findViewById(R.id.event_button1);
        event_button1.setOnClickListener(this);

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
                            long l_heart = data.child("heart").getChildrenCount();
                            long l_review = data.child("review").getChildrenCount();
                            float star;
                            if (data.child("star").exists()) {
                                star = Float.valueOf(data.child("star").getValue().toString());
                            } else { star = 0; }
                            String stPhoto;
                            if (data.child("photo").exists()) {
                                stPhoto = data.child("photo").child(String.valueOf(0)).getValue().toString();
                            } else { stPhoto = ""; }
                            boolean event;
                            if (data.child("event").exists()) {
                                event = true;
                            } else { event = false; }
                            items.add(new Restaurant(stResKey, stResName, stResAddress, stPhoto, stDate, star, l_heart, l_review, event));
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

        btn1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        btn1.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeon));
                        btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.maincolor));
                        btn2.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn3.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn4.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        break;
                    }
                }
                return false;
            }
        });
        btn2.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        btn1.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn2.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeon));
                        btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.maincolor));
                        btn3.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn4.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        break;
                    }
                }
                return false;
            }
        });
        btn3.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        btn1.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn2.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn3.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeon));
                        btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.maincolor));
                        btn4.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        break;
                    }
                }
                return false;
            }
        });
        btn4.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        btn1.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn2.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn3.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeoff2));
                        btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.D6));
                        btn4.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.area_strokeon));
                        btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.maincolor));
                        break;
                    }
                }
                return false;
            }
        });

//        스피너
        Spinner spinner = v.findViewById(R.id.spinner);
        SpinnerAdapter spinneradapter;

//        데이터
        List<String> data = new ArrayList<>();
        data.add("최신순"); data.add("별점순"); data.add("거리순");
        spinneradapter = new SpinnerAdapter(getContext(), data);
        spinner.setAdapter(spinneradapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
        return v;
    }

    @Override
    public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn1:
                        break;
                    case R.id.btn2:
                        items.clear();
                        resAdapter.notifyDataSetChanged();
                        break;
                    case R.id.btn3:
                        break;
                    case R.id.btn4:
                        break;
                    case R.id.event_button1:
                        Intent intent = new Intent(getActivity(), Event.class);
                        startActivity(intent);
                        break;
        }
    }
}
