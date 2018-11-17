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

        reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("304000");

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
                    String email = data.child("email").getValue().toString();
                    String name = data.child("name").getValue().toString();
                    String text = data.child("context").getValue().toString();
                    Review review = new Review(revKey,resKey,email,name,text);

                    listReview.add(review);
                }
                Collections.reverse(listReview);
                reviewAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        // Adapter 생성
        reviewAdapter = new ReviewAdapter(listReview, getActivity());
        recyclerView.setAdapter(reviewAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View reviewBlock = rv.findChildViewUnder(e.getX(),e.getY());
                if (reviewBlock != null && e.getAction()==1) {
                    int position = rv.getChildAdapterPosition(reviewBlock);

                    Intent intent = new Intent(getContext(),Restaurant.class);
                    intent.putExtra("resKey",listReview.get(position).resKey);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) { }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { }
        });
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