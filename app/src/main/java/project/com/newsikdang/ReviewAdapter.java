package project.com.newsikdang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

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

    //Firebase관련
    FirebaseUser user;

    /**
     * @Name    ViewHolder
     * @Usage   Save views in Recycler view and link between variable and layout view(tag)
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvContext, tvDate, tvHeart;
        public Button btnHeart;
        public RatingBar rbStar;

        //순서대로 칸, 이름, 이미지를 레이아웃에서 불러와 생성
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.frag3_username);
            tvContext = itemView.findViewById(R.id.frag3_review);
            tvDate = itemView.findViewById(R.id.tv_rev_date);
            tvHeart = itemView.findViewById(R.id.tv_rev_heart);
            btnHeart = itemView.findViewById(R.id.btn_rev_heart);
            rbStar = itemView.findViewById(R.id.rb_rev_star);
        }
    }

    // 커스텀 생성자로 리뷰 데이터 리스트를 받음
    public ReviewAdapter(List<Review> reviews, Context context) {
        this.listReview = reviews;
        this.listFilter = new ArrayList<>();
        this.listFilter.addAll(reviews);
        this.context = context;
    }

    //VIew생성 및 레이아웃 설정
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.threelist, parent, false);

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
        holder.tvName.setText(listReview.get(position).getName());
        holder.tvContext.setText(listReview.get(position).getText());
        holder.tvDate.setText(listReview.get(position).getDate());


        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                if (button.isSelected()) {

                } else {

                }
            }
        });

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