package project.com.newsikdang;


import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class OneFragment extends Fragment implements View.OnClickListener {
    RecyclerView mRecyclerView;
    Adapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.onefragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler1);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<ReslistActivity> items = new ArrayList();
        items.add(new ReslistActivity("일월십사일", "357", "43", "9.8"));
        items.add(new ReslistActivity("술에맛들다", "357", "43", "9.8"));
        items.add(new ReslistActivity("재훈이", "358", "43", "9.7"));
        items.add(new ReslistActivity("바보", "359", "43", "9.6"));
        items.add(new ReslistActivity("싸가지", "357", "43", "9.8"));

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
        mAdapter = new Adapter(items);
        mRecyclerView.setAdapter(mAdapter);

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
