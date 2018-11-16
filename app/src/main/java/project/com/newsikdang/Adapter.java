package project.com.newsikdang;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<ReslistActivity> mItems;
    Context mContext;
    public Adapter(ArrayList itemList) {
        mItems = itemList;
    }
    // 필수 오버라이드 : View 생성, ViewHolder 호출
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_reslist, parent, false);
        mContext = parent.getContext();
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }
    // 필수 오버라이드 : 재활용되는 View 가 호출, Adapter 가 해당 position 에 해당하는 데이터를 결합
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        // 해당 position 에 해당하는 데이터 결합
        holder.mRestxt1.setText(mItems.get(position).restxt1);
        holder.mRestxt2.setText(mItems.get(position).restxt2);
        holder.mRestxt3.setText(mItems.get(position).restxt3);
        holder.mRestxt4.setText(mItems.get(position).restxt4);

        // 이벤트처리 : 생성된 List 중 선택된 목록번호를 Toast로 출력
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Restaurant.class);
                intent.putExtra("key",mItems.get(position).resKey);
                mContext.startActivity(intent);
            }
        });
    }
    // 필수 오버라이드 : 데이터 갯수 반환
    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
