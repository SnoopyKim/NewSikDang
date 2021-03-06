package project.com.newsikdang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

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

public class TwoFragment extends Fragment {

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private DatabaseReference restaurantsRef;

    Map map;

    RelativeLayout loading;
    RecyclerView mRecyclerView;
    MapRestaurantAdapter resAdapter;
    LinearLayoutManager mLayoutManager;

    String stCGG = "3040000";
    ArrayList<Restaurant> items = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.twofragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child("customer").child(user.getUid());
        restaurantsRef = database.getReference("restaurants");

        loading = v.findViewById(R.id.rl_loading);

        userRef.child("block").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> blockList = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    blockList.add(data.getKey());
                }
                Query query = restaurantsRef.child(stCGG).orderByChild("date").limitToLast(10);
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
                            boolean event = data.child("event").exists();
                            items.add(new Restaurant(stResKey, stResName, stResAddress, stPhoto, stDate, star, l_heart , l_review, event));
                            map.addMarker(stResName,stResAddress);
                        }
                        map.setLocation(true);
                        Collections.reverse(items);
                        resAdapter.notifyDataSetChanged();
                        loading.setVisibility(View.GONE);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        mRecyclerView = v.findViewById(R.id.map_recycler);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // LinearLayout으로 설정
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Adapter 생성
        resAdapter = new MapRestaurantAdapter(items, getContext());
        mRecyclerView.setAdapter(resAdapter);

        map = new Map();
        // replace fragment
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.ll_map, map);
        // Commit the transaction
        transaction.commit();

        return v;
    }

}