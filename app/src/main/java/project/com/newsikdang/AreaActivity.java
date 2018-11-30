package project.com.newsikdang;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AreaActivity extends FragmentActivity implements View.OnClickListener {
    final String TAG = "MainActivity";
    int mCurrentFragmentIndex;

    public final static int FRAGMENT_ONE = 0;
    public final static int FRAGMENT_TWO = 1;
    public final static int FRAGMENT_THREE = 2;

    RelativeLayout a_btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);

        a_btn1 = findViewById(R.id.a_btn1);
        a_btn1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}
