package project.com.newsikdang;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreeRecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView mfrag3_username;
    public TextView mfrag3_review;

    public ThreeRecyclerViewHolder(View itemView) {
        super(itemView);
        mfrag3_username = (TextView) itemView.findViewById(R.id.frag3_username);
        mfrag3_review = (TextView) itemView.findViewById(R.id.frag3_review);
    }
}
