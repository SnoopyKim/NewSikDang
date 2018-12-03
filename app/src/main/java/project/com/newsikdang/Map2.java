package project.com.newsikdang;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.collection.LLRBNode;

import static com.facebook.FacebookSdk.getApplicationContext;

public class Map2 extends Fragment implements View.OnClickListener {

    Button b_btn1,b_btn2,b_btn3,b_btn4,b_btn5,b_btn6,b_btn7,b_btn8,b_btn9;

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

        return v;


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.b_btn1:
                b_btn1.setBackgroundResource(R.drawable.area_stroke); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on)); b_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));

                break;
            case R.id.b_btn2:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_stroke);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                b_btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                break;
            case R.id.b_btn3:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_stroke); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on)); b_btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));

                break;
            case R.id.b_btn4:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_stroke);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                b_btn5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));

                break;
            case R.id.b_btn5:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_stroke); b_btn6.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on)); b_btn6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                break;
            case R.id.b_btn6:
                b_btn1.setBackgroundResource(R.drawable.area_strokeoff); b_btn2.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn3.setBackgroundResource(R.drawable.area_strokeoff); b_btn4.setBackgroundResource(R.drawable.area_strokeoff);
                b_btn5.setBackgroundResource(R.drawable.area_strokeoff); b_btn6.setBackgroundResource(R.drawable.area_stroke);
                b_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn3.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn4.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                b_btn5.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off)); b_btn6.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                break;
        }
    }
}
