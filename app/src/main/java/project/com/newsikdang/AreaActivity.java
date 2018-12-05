package project.com.newsikdang;

import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.facebook.FacebookSdk.getApplicationContext;
import static project.com.newsikdang.MainActivity.FRAGMENT_FOUR;

public class AreaActivity extends FragmentActivity implements View.OnClickListener {
    private static String TAG = "AreaActivity";
    int mCurrentFragmentIndex;
    public final static int FRAGMENT_ONE = 0;
    public final static int FRAGMENT_TWO = 1;

    Button area_btn1, area_btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_area);

        area_btn1 = findViewById(R.id.area_btn1);
        area_btn1.setOnClickListener(this);
        area_btn2 = findViewById(R.id.area_btn2);
        area_btn2.setOnClickListener(this);

        mCurrentFragmentIndex = FRAGMENT_ONE;
        fragmentReplace(mCurrentFragmentIndex);
    }

    public void fragmentReplace(int reqNewFragmentIndex) {
        Fragment newFragment = null; Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);
        newFragment = getFragment(reqNewFragmentIndex);
        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager() .beginTransaction();
        transaction.replace(R.id.area_fragment, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
        switch (idx) {
            case FRAGMENT_ONE:
                newFragment = new Map1();
                break;
            case FRAGMENT_TWO:
                newFragment = new Map2();
                break;
            default:
                Log.d(TAG, "Unhandle case");
                break;
        }
        return newFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.area_btn1:
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                area_btn1.setEnabled(false);
                area_btn2.setEnabled(true);
//                배경색변경
                area_btn1.setBackgroundResource(R.drawable.area_strokeon);
                area_btn2.setBackgroundResource(R.drawable.area_strokeoff2);
//                글자색변경
                area_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                area_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                break;
            case R.id.area_btn2:
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
                area_btn1.setEnabled(true);
                area_btn2.setEnabled(false);
//                배경색변경
                area_btn1.setBackgroundResource(R.drawable.area_strokeoff2);
                area_btn2.setBackgroundResource(R.drawable.area_strokeon);
//                글자색변경
                area_btn1.setTextColor(getApplicationContext().getResources().getColor(R.color.area_off));
                area_btn2.setTextColor(getApplicationContext().getResources().getColor(R.color.area_on));
                break;
        }
    }
}
