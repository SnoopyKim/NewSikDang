package project.com.newsikdang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    //리뷰 데이터 리스트 두개 (하나는 백업용)
    //@Comment search results are dynamic element. So, Friends list back up to mFilter
    List<Restaurant> listRestaurant;
    Context context;

    /**
     * @Name    ViewHolder
     * @Usage   Save views in Recycler view and link between variable and layout view(tag)
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rlRestaurant;
        public TextView tvName, tvAddress, tvDate, tvStar, tvHeart, tvReview;
        public Button btnHeart;
        public RatingBar rbStar;

        //순서대로 칸, 이름, 이미지를 레이아웃에서 불러와 생성
        public ViewHolder(View itemView) {
            super(itemView);
            rlRestaurant = itemView.findViewById(R.id.rl_restaurant);
            tvName = itemView.findViewById(R.id.tv_res_name);
            tvAddress = itemView.findViewById(R.id.tv_res_address);
            tvDate = itemView.findViewById(R.id.tv_res_day);
            tvStar = itemView.findViewById(R.id.tv_res_star);
            tvHeart = itemView.findViewById(R.id.tv_res_heart);
            tvReview = itemView.findViewById(R.id.tv_res_review);
            btnHeart = itemView.findViewById(R.id.btn_res_heart);
            rbStar = itemView.findViewById(R.id.rb_res_star);
        }
    }

    // 커스텀 생성자로 리뷰 데이터 리스트를 받음
    public RestaurantAdapter(List<Restaurant> restaurants, Context context) {
        this.listRestaurant = restaurants;
        this.context = context;
    }

    //VIew생성 및 레이아웃 설정
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant, parent, false);

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
        holder.tvName.setText(listRestaurant.get(position).getName());
        holder.tvAddress.setText(listRestaurant.get(position).getAddress());

        Calendar now = Calendar.getInstance();
        int date = Integer.parseInt(listRestaurant.get(position).getDate());
        Calendar date_cal = Calendar.getInstance();
        date_cal.set(date/10000,(date/100)%100-1,date%100);
        long dday = (now.getTimeInMillis()-date_cal.getTimeInMillis()) / (1000*60*60*24);
        holder.tvDate.setText(String.valueOf(dday));

        holder.btnHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                button.setSelected(!button.isSelected());
                int cnt_like = Integer.parseInt(holder.tvHeart.getText().toString());
                if (button.isSelected()) { holder.tvHeart.setText(String.valueOf(cnt_like+1)); }
                else { holder.tvHeart.setText(String.valueOf(cnt_like-1)); }
            }
        });

        holder.rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,RestaurantActivity.class);
                intent.putExtra("resKey",listRestaurant.get(position).getResKey());
                context.startActivity(intent);
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
                        stFriendUid = listRestaurant.get(position).getKey();
                        stFriendEmail = listRestaurant.get(position).getEmail();
                        stFriendname = listRestaurant.get(position).getName();
                        stFriendPhoto = listRestaurant.get(position).getPhoto();

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
        return listRestaurant.size();
    }


}