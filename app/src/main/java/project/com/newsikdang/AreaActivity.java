package project.com.newsikdang;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
        transaction.commit(); }


    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
        switch (idx) {
            case FRAGMENT_ONE:
                newFragment = new map_1();
                break;
            case FRAGMENT_TWO:
                newFragment = new map_2();
                break;
            case FRAGMENT_THREE:
                newFragment = new map_3();
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
            case R.id.a_btn1:
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                a_btn1.setEnabled(false);
                break;
        }
    }
}
