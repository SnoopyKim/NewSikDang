package project.com.newsikdang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CouponAdapter extends RecyclerView.Adapter<CouponHolder>  {
    private ArrayList<Coupon> mCoupon;
    Context mContext;
    public CouponAdapter(ArrayList couponList){
        mCoupon = couponList;
    }

    @Override
    public CouponHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon, parent, false);
        mContext = parent.getContext();
        CouponHolder holder = new CouponHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(CouponHolder holder, final int position) {
        holder.cRestaurant.setText(mCoupon.get(position).crestaurant);
        holder.cPrice.setText(mCoupon.get(position).cprice);
        holder.cDate.setText(mCoupon.get(position).cdate);
    }
        @Override
        public int getItemCount () { return mCoupon.size(); }
}

