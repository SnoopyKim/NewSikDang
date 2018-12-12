package project.com.newsikdang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class MapRestaurantAdapter extends RecyclerView.Adapter<MapRestaurantAdapter.ViewHolder> {

    //리뷰 데이터 리스트 두개 (하나는 백업용)
    //@Comment search results are dynamic element. So, Friends list back up to mFilter
    List<Restaurant> listRestaurant;
    Context context;

    FirebaseUser user;
    DatabaseReference restaurantRef, userRef;

    /**
     * @Name    ViewHolder
     * @Usage   Save views in Recycler view and link between variable and layout view(tag)
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlRestaurant;
        public ImageView ivPhoto;
        public TextView tvName, tvAddress, tvDate, tvEvent, tvHeart, tvReview;
        public Button btnHeart;
        public RatingBar rbStar;

        //순서대로 칸, 이름, 이미지를 레이아웃에서 불러와 생성
        public ViewHolder(View itemView) {
            super(itemView);
            setIsRecyclable(false);
            rlRestaurant = itemView.findViewById(R.id.rl_restaurant);
            ivPhoto = itemView.findViewById(R.id.iv_res_img);
            tvName = itemView.findViewById(R.id.tv_res_name);
            tvAddress = itemView.findViewById(R.id.tv_res_address);
            tvDate = itemView.findViewById(R.id.tv_res_day);
            tvEvent = itemView.findViewById(R.id.tv_res_event);
            tvHeart = itemView.findViewById(R.id.tv_res_heart);
            tvReview = itemView.findViewById(R.id.tv_res_review);
            btnHeart = itemView.findViewById(R.id.btn_res_heart);
            rbStar = itemView.findViewById(R.id.rb_res_star);
        }
    }

    // 커스텀 생성자로 리뷰 데이터 리스트를 받음
    public MapRestaurantAdapter(List<Restaurant> restaurants, Context context) {
        this.listRestaurant = restaurants;
        this.context = context;
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000");
        this.userRef = FirebaseDatabase.getInstance().getReference("users").child("customer").child(user.getUid());
    }

    //VIew생성 및 레이아웃 설정
    @Override
    public MapRestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_two__restaurant, parent, false);

        //set the view's size, margin, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * @Name    onBindViewHolder
     * @Usage   set holder's element to Firebase data
     * @Param   holder : custom viewholder , position : Friend's index in Friend list
     * @return  void
     * @Comment Because of hover event about viewholder, define setOnTouchListener. but can't implement
     * */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //이름과 리뷰 적기
        if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) return;
        final Restaurant restaurant = listRestaurant.get(position);

        if (!restaurant.getPhoto().equals("")) {
            Glide.with(context).load(restaurant.getPhoto()).into(holder.ivPhoto);
        }

        holder.tvName.setText(restaurant.getName());
        holder.tvAddress.setText(restaurant.getAddress());

        Calendar now = Calendar.getInstance();
        int date = Integer.parseInt(restaurant.getDate());
        Calendar date_cal = Calendar.getInstance();
        date_cal.set(date/10000,(date/100)%100-1,date%100);
        long dday = (now.getTimeInMillis()-date_cal.getTimeInMillis()) / (1000*60*60*24);
        holder.tvDate.setText(String.valueOf(dday));
        holder.rbStar.setRating(restaurant.getStar());
        holder.tvReview.setText(String.valueOf(restaurant.getReview()));
        if (!restaurant.getEvent()) { holder.tvEvent.setVisibility(View.GONE); }

        userRef.child("heart").child(restaurant.getResKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { holder.btnHeart.setSelected(true); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        holder.tvHeart.setText(String.valueOf(restaurant.getHeart()));
        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    restaurantRef.child(restaurant.getResKey()).child("heart").child(user.getUid()).setValue(true);
                    userRef.child("heart").child(restaurant.getResKey()).setValue(true);
                    restaurant.setHeart(restaurant.getHeart()+1);
                    holder.tvHeart.setText(String.valueOf(restaurant.getHeart()));
                    Toast.makeText(context,"좋아요 목록에 추가되었습니다.",Toast.LENGTH_SHORT).show();
                }
                else {
                    restaurantRef.child(restaurant.getResKey()).child("heart").child(user.getUid()).removeValue();
                    userRef.child("heart").child(restaurant.getResKey()).removeValue();
                    restaurant.setHeart(restaurant.getHeart()-1);
                    holder.tvHeart.setText(String.valueOf(restaurant.getHeart()));
                    Toast.makeText(context,"좋아요 목록에서 삭제되었습니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });


        holder.rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RestaurantActivity.class);
                intent.putExtra("resKey",restaurant.getResKey());
                context.startActivity(intent);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listRestaurant.size();
    }
}