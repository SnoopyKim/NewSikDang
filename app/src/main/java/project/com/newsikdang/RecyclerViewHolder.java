package project.com.newsikdang;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    public TextView mRestxt1;
    public TextView mRestxt2;
    public TextView mRestxt3;
    public TextView mRestxt4;
    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mRestxt1 = (TextView) itemView.findViewById(R.id.restxt1);
        mRestxt2 = (TextView) itemView.findViewById(R.id.restxt2);
        mRestxt3 = (TextView) itemView.findViewById(R.id.restxt3);
        mRestxt4 = (TextView) itemView.findViewById(R.id.restxt4);
    }
}
