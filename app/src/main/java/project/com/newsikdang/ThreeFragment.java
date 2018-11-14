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

public class ThreeFragment extends Fragment implements View.OnClickListener {
    RecyclerView mRecyclerView;
    ThreeAdapter mAdapter;
    LinearLayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.threefragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler3);
        mLayoutManager = new LinearLayoutManager(this.getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        ArrayList<ThreeList> items = new ArrayList();
        items.add(new ThreeList("User1004", "맛있쪙"));
        items.add(new ThreeList("User1005", "맛없쪙"));
        items.add(new ThreeList("User1006", "맛있쪙"));
        items.add(new ThreeList("User1007", "맛없쪙"));

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
        mAdapter = new ThreeAdapter(items);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }
    @Override
    public void onClick(View v) {
    }
}