package project.com.newsikdang;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CouponHolder extends RecyclerView.ViewHolder {
    public TextView cRestaurant;
    public TextView cPrice;
    public TextView cDate;
    public CouponHolder(View couponView){
        super(couponView);
        cRestaurant = couponView.findViewById(R.id.coupon_restaurant);
        cPrice = couponView.findViewById(R.id.coupon_price);
        cDate = couponView.findViewById(R.id.coupon_date);
    }
}
