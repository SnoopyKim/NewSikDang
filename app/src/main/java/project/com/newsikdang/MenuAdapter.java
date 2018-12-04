package project.com.newsikdang;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    //리뷰 데이터 리스트 두개 (하나는 백업용)
    //@Comment search results are dynamic element. So, Friends list back up to mFilter
    List<Menu> listMenu;
    Context context;

    boolean setting;

    /**
     * @Name    ViewHolder
     * @Usage   Save views in Recycler view and link between variable and layout view(tag)
     * */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public EditText etName, etCost;
        public TextView tvName, tvCost;

        //순서대로 칸, 이름, 이미지를 레이아웃에서 불러와 생성
        public ViewHolder(View itemView) {
            super(itemView);
            etName = itemView.findViewById(R.id.et_menu_name);
            etCost = itemView.findViewById(R.id.et_menu_cost);
            tvName = itemView.findViewById(R.id.tv_menu_name);
            tvCost = itemView.findViewById(R.id.tv_menu_cost);
        }
    }

    // 커스텀 생성자로 리뷰 데이터 리스트를 받음
    public MenuAdapter(List<Menu> menu, Context context, boolean setting) {
        this.listMenu = menu;
        this.context = context;
        this.setting = setting;
    }

    //VIew생성 및 레이아웃 설정
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v;
        if (setting) { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_setting, parent, false); }
        else { v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu, parent, false); }

        //set the view's size, margin, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //이름과 리뷰 적기
        final Menu menu = listMenu.get(position);

        if (setting) {
            holder.etName.setText(menu.getName());
            holder.etName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    menu.setName(editable.toString());
                }
            });
            holder.etCost.setText(menu.getCost());
            holder.etCost.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                @Override
                public void afterTextChanged(Editable editable) {
                    menu.setCost(editable.toString());
                }
            });
        } else {
            holder.tvName.setText(menu.getName());
            holder.tvCost.setText(menu.getCost());
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listMenu.size();
    }


}