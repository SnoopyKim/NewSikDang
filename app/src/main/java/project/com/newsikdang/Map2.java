package project.com.newsikdang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Map2 extends Fragment implements View.OnClickListener {

    RelativeLayout b_btn1, b_btn2,b_btn3,b_btn4,b_btn5,b_btn6, area_choice;
    TextView area_tv1,area_tv2,area_tv3,area_tv4,area_tv5,area_tv6;
    TextView tv_map1, tv_map2,tv_map3, tv_map4,tv_map5, tv_map6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_map2, container, false);

        b_btn1 = v.findViewById(R.id.b_btn1);
        b_btn1.setOnClickListener(this);
        b_btn2 = v.findViewById(R.id.b_btn2);
        b_btn2.setOnClickListener(this);
        b_btn3 = v.findViewById(R.id.b_btn3);
        b_btn3.setOnClickListener(this);
        b_btn4 = v.findViewById(R.id.b_btn4);
        b_btn4.setOnClickListener(this);
        b_btn5 = v.findViewById(R.id.b_btn5);
        b_btn5.setOnClickListener(this);
        b_btn6 = v.findViewById(R.id.b_btn6);
        b_btn6.setOnClickListener(this);
        area_tv1 = v.findViewById(R.id.area_tv1);area_tv2 = v.findViewById(R.id.area_tv2);
        area_tv3 = v.findViewById(R.id.area_tv3);area_tv4 = v.findViewById(R.id.area_tv4);
        area_tv5 = v.findViewById(R.id.area_tv5);area_tv6 = v.findViewById(R.id.area_tv6);
        tv_map1 = v.findViewById(R.id.tv_map1);tv_map2 = v.findViewById(R.id.tv_map2);
        tv_map3 = v.findViewById(R.id.tv_map3);tv_map4 = v.findViewById(R.id.tv_map4);
        tv_map5 = v.findViewById(R.id.tv_map5);tv_map6 = v.findViewById(R.id.tv_map6);
        area_choice = v.findViewById(R.id.area_choice);

        return v;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.b_btn1:
                b_btn1.setBackgroundResource(R.drawable.area_stroke); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                area_tv1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on)); area_tv2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map1.setTextColor(getApplicationContext().getResources().getColor(R.color.c69)); tv_map2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));

                break;
            case R.id.b_btn2:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_stroke);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                area_tv1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                area_tv3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map2.setTextColor(getApplicationContext().getResources().getColor(R.color.c69));
                tv_map3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                break;
            case R.id.b_btn3:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_stroke); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                area_tv1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on)); area_tv4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map3.setTextColor(getApplicationContext().getResources().getColor(R.color.c69)); tv_map4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));

                break;
            case R.id.b_btn4:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_stroke);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                area_tv1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                area_tv5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map4.setTextColor(getApplicationContext().getResources().getColor(R.color.c69));
                tv_map5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                break;
            case R.id.b_btn5:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_stroke); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                area_tv1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on)); area_tv6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map5.setTextColor(getApplicationContext().getResources().getColor(R.color.c69)); tv_map6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                break;
            case R.id.b_btn6:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_stroke);
                area_tv1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_tv5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); area_tv6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                tv_map1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                tv_map5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); tv_map6.setTextColor(getApplicationContext().getResources().getColor(R.color.c69));
                break;
            case R.id.area_choice:
                getActivity().finish();
                break;
        }
    }



}
