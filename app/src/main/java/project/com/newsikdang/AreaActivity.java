package project.com.newsikdang;

import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AreaActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "AreaActivity";

    RelativeLayout a_btn1, b_btn1, c_btn1, area_a, area_b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        a_btn1 = findViewById(R.id.a_btn1);
        b_btn1 = findViewById(R.id.b_btn1);
        area_a = findViewById(R.id.area_a);
        area_b = findViewById(R.id.area_b);

        area_a.setVisibility(View.VISIBLE);
        area_b.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.a_btn1:
                area_a.setVisibility(View.GONE);
                area_b.setVisibility(View.VISIBLE);
                break;
        }
    }
}
