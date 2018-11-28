package project.com.newsikdang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    //리뷰 데이터 리스트 두개 (하나는 백업용)
    //@Comment search results are dynamic element. So, Friends list back up to mFilter
    List<Review> listReview;
    List<Review> listFilter;
    Context context;

    FirebaseUser user;
    DatabaseReference userRef, reviewRef;

    boolean clickable;

    /**
     * @Name    ViewHolder
     * @Usage   Save views in Recycler view and link between variable and layout view(tag)
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlReview;
        public TextView tvName, tvContext, tvDate, tvHeart;
        public Button btnHeart;
        public RatingBar rbStar, rbTaste, rbCost, rbService, rbAmbiance;

        //순서대로 칸, 이름, 이미지를 레이아웃에서 불러와 생성
        public ViewHolder(View itemView) {
            super(itemView);
            rlReview = itemView.findViewById(R.id.rl_review);
            tvName = itemView.findViewById(R.id.frag3_username);
            tvContext = itemView.findViewById(R.id.frag3_review);
            tvDate = itemView.findViewById(R.id.tv_rev_date);
            tvHeart = itemView.findViewById(R.id.tv_rev_heart);
            btnHeart = itemView.findViewById(R.id.btn_rev_heart);
            rbStar = itemView.findViewById(R.id.rb_rev_star);
            rbTaste = itemView.findViewById(R.id.rb_rev_taste);
            rbCost = itemView.findViewById(R.id.rb_rev_cost);
            rbService = itemView.findViewById(R.id.rb_rev_service);
            rbAmbiance = itemView.findViewById(R.id.rb_rev_ambiance);
        }
    }

    // 커스텀 생성자로 리뷰 데이터 리스트를 받음
    public ReviewAdapter(List<Review> reviews, Context context, boolean clickable) {
        this.listReview = reviews;
        this.listFilter = new ArrayList<>();
        this.listFilter.addAll(reviews);
        this.context = context;
        this.clickable = clickable;
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        this.userRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
        this.reviewRef = FirebaseDatabase.getInstance().getReference("users").child("3040000");
    }

    @Override
    public int getItemViewType(int position) {
        if (listReview.get(position).isDetail()) { return 1; }
        else { return 0; }
    }

    //VIew생성 및 레이아웃 설정
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        if (viewType == 0) { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_simple, parent, false); }
        else { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_detail, parent, false); }

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
        final Review review = listReview.get(position);

        holder.tvName.setText(review.getName());
        holder.tvContext.setText(review.getText());
        holder.tvDate.setText(review.getDate());
        holder.rbStar.setRating(review.getStar());
        if (review.isDetail()) {
            holder.rbTaste.setRating(review.getStartaste());
            holder.rbCost.setRating(review.getStarcost());
            holder.rbService.setRating(review.getStarservice());
            holder.rbAmbiance.setRating(review.getStarambiance());
        }

        userRef.child("heart").child(review.getRevKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { holder.btnHeart.setSelected(true); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        reviewRef.child(review.getRevKey()).child("heart").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.tvHeart.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {
                    reviewRef.child(review.getRevKey()).child("heart").child(user.getUid()).setValue(true);
                    userRef.child("heart").child(review.getRevKey()).setValue(true);
                    review.setHeart(review.getHeart()+1);
                    holder.tvHeart.setText(String.valueOf(review.getHeart()));
                } else {
                    reviewRef.child(review.getRevKey()).child("heart").child(user.getUid()).removeValue();
                    userRef.child("heart").child(review.getRevKey()).removeValue();
                    review.setHeart(review.getHeart()-1);
                    holder.tvHeart.setText(String.valueOf(review.getHeart()));
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

        /*
        String stPhoto = mFriend.get(position).getPhoto();
        if (stPhoto.equals("None")) {
            //친구의 이미지 정보가 없을 경우 지정해둔 기본 이미지로
            Drawable defaultImg = context.getResources().getDrawable(R.drawable.ic_person_black_24dp);
            holder.ivUser.setImageDrawable(defaultImg);
        } else {
            Glide.with(context).load(stPhoto)
                    .placeholder(R.drawable.ic_person_black_24dp)
                    .into(holder.ivUser);
        }
        */
    }

    /**
     * @Name    filter
     * @Usage   search friends list
     * @Param   charText : search text <- Tabactivity's changeET's event catch value
     * @return  void
     * @Comment mFilter : backup, mFilter : showing at user
     * */

    public void filter_detail(boolean detail) {
        //친구 데이터 리스트를 하나 비운 뒤, 입력한 문자에 따라 백업용으로 다시 친구 데이터 리스트를 만듬
        listReview.clear();
        Log.d("filter", "listFilter" + listFilter);
        for (Review review : listFilter) {
            if (review.isDetail() == detail) {
                Log.d("filter", "review_if "+ review);
                listReview.add(review);
            }
            Log.d("filter", "review_else "+ review);
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