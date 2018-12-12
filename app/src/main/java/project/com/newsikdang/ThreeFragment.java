package project.com.newsikdang;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeFragment extends Fragment {
    RecyclerView recyclerView;
    FeedAdapter feedAdapter;
    LinearLayoutManager layoutManager;

    DatabaseReference reviewRef;

    List<Review> listReview;

    EditText etSearch;

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

        Query reviewQuery = reviewRef.orderByChild("date");
        reviewQuery.addListenerForSingleValueEvent(new ValueEventListener() {
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
                Collections.reverse(listReview);
                // Adapter 생성
                feedAdapter = new FeedAdapter(listReview, getActivity(), true);
                recyclerView.setAdapter(feedAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        etSearch = v.findViewById(R.id.hash_edit);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                String stSearch = editable.toString();
                feedAdapter.filter(stSearch);
            }
        });

        return v;
    }
}