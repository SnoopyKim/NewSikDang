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

public class FourCoupon extends AppCompatActivity {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    CouponAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fourcoupon);
        mRecyclerView = (RecyclerView) findViewById(R.id.coupon_recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ArrayList<Coupon> coupon = new ArrayList();
        coupon.add(new Coupon("일월십사일","2,000원","사용기간 : 2018/11/12~2018/11/25"));
        coupon.add(new Coupon("술에 맛들다","2,000원","사용기간 : 2018/11/01~2018/11/28"));
        // LinearLayout으로 설정
        mRecyclerView.setLayoutManager(mLayoutManager);
        // Animation Defualt 설정
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // Decoration 설정
        // mRecyclerView.addItemDecoration(new RecyclerViewDecoration(this, RecyclerViewDecoration.VERTICAL_LIST));

        // Adapter 생성
        mAdapter = new CouponAdapter(coupon);
        mRecyclerView.setAdapter(mAdapter);

    }
}
