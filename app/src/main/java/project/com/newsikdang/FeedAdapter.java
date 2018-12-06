package project.com.newsikdang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by snoopy on 2017-04-01.
 */
/**
 * @Name    FriendAdapter
 * @Usage   Friend list adapter
 *           manage each view
 *           search list
 *           show info dialog
 * @Layout  my_friend_view.xml
 * */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    //리뷰 데이터 리스트 두개 (하나는 백업용)
    //@Comment search results are dynamic element. So, Friends list back up to mFilter
    List<Review> listReview;
    List<Review> listRevFilter;
    Context context;

    FirebaseUser user;
    DatabaseReference userRef, reviewRef, restaurantRef;

    boolean clickable;

    /**
     * @Name    ViewHolder
     * @Usage   Save views in Recycler view and link between variable and layout view(tag)
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlReview;
        public TextView tvResName, tvRevName, tvContext, tvResDate, tvRevDate, tvResHeart, tvRevHeart, tvReview, tvEvent;
        public Button btnResHeart, btnRevHeart;
        public RatingBar rbResStar, rbStar, rbTaste, rbCost, rbService, rbAmbiance;
        public CircleImageView ivProfile;
        public ViewPager photoPager;
        public TextView tvIndicator;

        //순서대로 칸, 이름, 이미지를 레이아웃에서 불러와 생성
        public ViewHolder(View itemView) {
            super(itemView);
            rlReview = itemView.findViewById(R.id.rl_review);
            tvResName = itemView.findViewById(R.id.tv_res_name);
            tvRevName = itemView.findViewById(R.id.frag3_username);
            tvContext = itemView.findViewById(R.id.frag3_review);
            tvResDate = itemView.findViewById(R.id.tv_res_day);
            tvRevDate = itemView.findViewById(R.id.tv_rev_date);
            tvResHeart = itemView.findViewById(R.id.tv_res_heart);
            tvRevHeart = itemView.findViewById(R.id.tv_rev_heart);
            btnResHeart = itemView.findViewById(R.id.btn_res_heart);
            btnRevHeart = itemView.findViewById(R.id.btn_rev_heart);
            tvReview = itemView.findViewById(R.id.feed_starnumber);
            rbResStar = itemView.findViewById(R.id.feed_star);
            rbStar = itemView.findViewById(R.id.rb_rev_star);
            rbTaste = itemView.findViewById(R.id.rb_rev_taste);
            rbCost = itemView.findViewById(R.id.rb_rev_cost);
            rbService = itemView.findViewById(R.id.rb_rev_service);
            rbAmbiance = itemView.findViewById(R.id.rb_rev_ambiance);
            ivProfile = itemView.findViewById(R.id.frag3_userimg);
            photoPager = itemView.findViewById(R.id.pager);
            tvIndicator = itemView.findViewById(R.id.tv_indicator);
            tvEvent = itemView.findViewById(R.id.tv_res_event);
        }
    }

    // 커스텀 생성자로 리뷰 데이터 리스트를 받음
    public FeedAdapter(List<Review> reviews, Context context, boolean clickable) {
        this.listReview = reviews;
        this.listRevFilter = new ArrayList<>();
        this.listRevFilter.addAll(reviews);
        this.context = context;
        this.clickable = clickable;
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        this.reviewRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000");
        this.restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000");
    }

    @Override
    public int getItemViewType(int position) {
        if (listReview.get(position).isDetail()) { return 1; }
        else { return 0; }
    }

    //VIew생성 및 레이아웃 설정
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        if (viewType == 0) { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag3_review_simple, parent, false); }
        else { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.frag3_review_detail, parent, false); }

        //set the view's size, margin, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //이름과 리뷰 적기
        final Review review = listReview.get(position);

        restaurantRef.child(review.getResKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot data) {
                String stResName = data.child("name").getValue().toString();
                String stDate = data.child("date").getValue().toString();
                long l_heart = data.child("heart").getChildrenCount();
                long l_review = data.child("review").getChildrenCount();
                float star;
                if (data.child("star").exists()) {
                    star = Float.valueOf(data.child("star").getValue().toString());
                } else { star = 0; }
                boolean event;
                if (data.child("event").exists()) {
                    event = true;
                } else { event = false; }

                holder.tvResName.setText(stResName);

                Calendar now = Calendar.getInstance();
                int date = Integer.parseInt(stDate);
                Calendar date_cal = Calendar.getInstance();
                date_cal.set(date/10000,(date/100)%100-1,date%100);
                long dday = (now.getTimeInMillis()-date_cal.getTimeInMillis()) / (1000*60*60*24);
                holder.tvResDate.setText(String.valueOf(dday));
                holder.rbResStar.setRating(star);
                holder.tvReview.setText(String.valueOf(l_review));
                if (!event) { holder.tvEvent.setVisibility(View.GONE); }
                holder.tvResHeart.setText(String.valueOf(l_heart));
                userRef.child("heart").child(review.getResKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) { holder.btnResHeart.setSelected(true); }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        holder.tvRevName.setText(review.getName());
        holder.tvContext.setText("");
        for (String word : review.getText().split(" ")) {
            if (word.charAt(0)=='#') {
                Spannable span = new SpannableString(word);
                span.setSpan(new ForegroundColorSpan(context.getColor(R.color.btnAbled)),0,span.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tvContext.append(span);
                holder.tvContext.append(" ");
            } else {
                holder.tvContext.append(word+" ");
            }
        }
        holder.tvRevDate.setText(review.getDate());
        holder.rbStar.setRating(review.getStar());
        Glide.with(context).load(review.getUserProfile()).into(holder.ivProfile);
        if (review.isDetail()) {
            holder.rbTaste.setRating(review.getStartaste());
            holder.rbCost.setRating(review.getStarcost());
            holder.rbService.setRating(review.getStarservice());
            holder.rbAmbiance.setRating(review.getStarambiance());

            final SlidingImageAdapter imageAdapter = new SlidingImageAdapter(context, review.getPhoto());
            holder.photoPager.setAdapter(imageAdapter);
            holder.tvIndicator.setText(1+" / "+imageAdapter.getCount());
            holder.photoPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
                @Override
                public void onPageSelected(int position) {
                    holder.tvIndicator.setText((position+1)+" / "+imageAdapter.getCount());
                }
                @Override
                public void onPageScrollStateChanged(int state) { }
            });
        }

        userRef.child("heart").child(review.getRevKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { holder.btnRevHeart.setSelected(true); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        reviewRef.child(review.getRevKey()).child("heart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) { holder.tvRevHeart.setText(String.valueOf(dataSnapshot.getChildrenCount())); }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        holder.btnRevHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    reviewRef.child(review.getRevKey()).child("heart").child(user.getUid()).setValue(true);
                    userRef.child("heart").child(review.getRevKey()).setValue(true);
                    review.setHeart(review.getHeart()+1);
                    holder.tvRevHeart.setText(String.valueOf(review.getHeart()));
                } else {
                    reviewRef.child(review.getRevKey()).child("heart").child(user.getUid()).removeValue();
                    userRef.child("heart").child(review.getRevKey()).removeValue();
                    review.setHeart(review.getHeart()-1);
                    holder.tvRevHeart.setText(String.valueOf(review.getHeart()));
                }
            }
        });

        if (clickable) {
            holder.rlReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, RestaurantActivity.class);
                    intent.putExtra("resKey", listReview.get(position).getResKey());
                    context.startActivity(intent);
                }
            });
        }

    }

    public void filter(String search) {
        listReview.clear();
        if (search.equals("")) { listReview.addAll(listRevFilter); }
        else {
            for (Review review : listRevFilter) {
                for (String word : review.getText().split(" ")) {
                    if (word.charAt(0)=='#' && word.substring(1).toLowerCase().contains(search.toLowerCase())) {
                        listReview.add(review);
                    }
                }
            }
        }

        //Communicate list view with adapter. Saying "data set Changed!"
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listReview.size();
    }
}