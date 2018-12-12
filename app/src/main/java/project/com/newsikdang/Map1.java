package project.com.newsikdang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Map1 extends Fragment {

    Button a_btn1, a_btn2, a_btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_map1, container, false);

        a_btn1 = v.findViewById(R.id.a_btn1);
        a_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a_btn1.setBackgroundResource(R.drawable.area_stroke);
                a_btn1.setTextColor(getResources().getColor(R.color.area_on));
                a_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                a_btn2.setTextColor(getResources().getColor(R.color.area_off));
                a_btn3.setBackgroundResource(R.drawable.area_strokeoff);
                a_btn3.setTextColor(getResources().getColor(R.color.area_off));
            }
        });
        a_btn2 = v.findViewById(R.id.a_btn2);
        a_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a_btn1.setBackgroundResource(R.drawable.area_strokeoff);
                a_btn1.setTextColor(getResources().getColor(R.color.area_off));
                a_btn2.setBackgroundResource(R.drawable.area_stroke);
                a_btn2.setTextColor(getResources().getColor(R.color.area_on));
                a_btn3.setBackgroundResource(R.drawable.area_strokeoff);
                a_btn3.setTextColor(getResources().getColor(R.color.area_off));
            }
        });
        a_btn3 = v.findViewById(R.id.a_btn3);
        a_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a_btn1.setBackgroundResource(R.drawable.area_strokeoff);
                a_btn1.setTextColor(getResources().getColor(R.color.area_off));
                a_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                a_btn2.setTextColor(getResources().getColor(R.color.area_off));
                a_btn3.setBackgroundResource(R.drawable.area_stroke);
                a_btn3.setTextColor(getResources().getColor(R.color.area_on));
            }
        });

        return v;
    }

}
