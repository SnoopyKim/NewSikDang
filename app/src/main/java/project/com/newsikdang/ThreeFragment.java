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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeFragment extends Fragment implements View.OnClickListener {
    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    LinearLayoutManager layoutManager;

    DatabaseReference reviewRef;

    List<Review> listReview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.threefragment, container, false);

        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000");

        recyclerView = v.findViewById(R.id.recycler3);
        layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // LinearLayout으로 설정
        recyclerView.setLayoutManager(layoutManager);
        // Animation Default 설정
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // Decoration 설정
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });

        listReview = new ArrayList<>();

        Query reviewQuery = reviewRef.orderByChild("date").limitToLast(30);
        reviewQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String revKey = data.getKey();
                    String resKey = data.child("restaurant").getValue().toString();
                    String uid = data.child("uid").getValue().toString();
                    String name = data.child("name").getValue().toString();
                    String text = data.child("context").getValue().toString();
                    String date = data.child("date").getValue().toString();
                    Review review = new Review(revKey,resKey,uid,name,text,date);

                    listReview.add(review);
                }
                Collections.reverse(listReview);
                reviewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        // Adapter 생성
        reviewAdapter = new ReviewAdapter(listReview, getActivity(), true);
        recyclerView.setAdapter(reviewAdapter);

        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_review:

                break;
        }
    }
}