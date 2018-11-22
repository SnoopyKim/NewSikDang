package project.com.newsikdang;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
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
        public RatingBar rbStar;

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

    //VIew생성 및 레이아웃 설정
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_simple, parent, false);

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

        userRef.child("heart").child(review.getRevKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) { holder.btnHeart.setSelected(true); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        reviewRef.child(review.getRevKey()).child("heart").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                review.setHeart(review.getHeart()+1);
                Log.d("Heart", "onChildAdded: " + review.getHeart());
                holder.tvHeart.setText(String.valueOf(review.getHeart()));
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                review.setHeart(review.getHeart()-1);
                holder.tvHeart.setText(String.valueOf(review.getHeart()));
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
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
                } else {
                    reviewRef.child(review.getRevKey()).child("heart").child(user.getUid()).removeValue();
                    userRef.child("heart").child(review.getRevKey()).removeValue();
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


        /* //View(칸) 클릭 시 -> 나중에 써먹을지도?
        holder.overall.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    //마우스를 눌렀을 때
                    case MotionEvent.ACTION_DOWN:
                        //holder.overall.setBackgroundColor(Color.parseColor("#F5F5F5"));

                        break;
                    //마우스를 땠을 때
                    case MotionEvent.ACTION_UP:
                        //set color back to default
                        holder.overall.setBackgroundColor(Color.WHITE);

                        //변수들의 값을 설정
                        stFriendUid = listReview.get(position).getKey();
                        stFriendEmail = listReview.get(position).getEmail();
                        stFriendname = listReview.get(position).getName();
                        stFriendPhoto = listReview.get(position).getPhoto();

                        break;
                }
                return true;
            }
        });
        */
    }

    /**
     * @Name    filter
     * @Usage   search friends list
     * @Param   charText : search text <- Tabactivity's changeET's event catch value
     * @return  void
     * @Comment mFilter : backup, mFilter : showing at user
     * */
    /*
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        //친구 데이터 리스트를 하나 비운 뒤, 입력한 문자에 따라 백업용으로 다시 친구 데이터 리스트를 만듬
        mFriend.clear();
        if (charText.length() == 0) {
            mFriend.addAll(mFilter);
        } else {
            for (Friend friend : mFilter) {
                String name = friend.getName();
                if (name.toLowerCase().contains(charText)) {
                    mFriend.add(friend);
                }
            }
        }
        //Communicate list view with adapter. Saying "data set Changed!"
        notifyDataSetChanged();
    }
    */

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listReview.size();
    }


}