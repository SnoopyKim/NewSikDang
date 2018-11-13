package project.com.newsikdang;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    final String TAG = "MainActivity";
    int mCurrentFragmentIndex;
    public final static int FRAGMENT_ONE = 0;
    public final static int FRAGMENT_TWO = 1;
    public final static int FRAGMENT_THREE = 2;
    public final static int FRAGMENT_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout frag1 = findViewById(R.id.frag1);
        frag1.setOnClickListener(this);
        LinearLayout frag2 = findViewById(R.id.frag2);
        frag2.setOnClickListener(this);
        LinearLayout frag3 = findViewById(R.id.frag3);
        frag3.setOnClickListener(this);
        LinearLayout frag4 = findViewById(R.id.frag4);
        frag4.setOnClickListener(this);
        mCurrentFragmentIndex = FRAGMENT_ONE;
        fragmentReplace(mCurrentFragmentIndex);
    }

    public void fragmentReplace(int reqNewFragmentIndex) {
        Fragment newFragment = null; Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);
        newFragment = getFragment(reqNewFragmentIndex);
        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager() .beginTransaction();
        transaction.replace(R.id.ll_fragment, newFragment);
        // Commit the transaction
        transaction.commit(); }

    private Fragment getFragment(int idx) {
        Fragment newFragment = null; switch (idx) {
            case FRAGMENT_ONE:
                newFragment = new OneFragment();
                break;
            case FRAGMENT_TWO:
                newFragment = new TwoFragment();
                break;
            case FRAGMENT_THREE:
                newFragment = new ThreeFragment();
                break;
            case FRAGMENT_FOUR:
                newFragment = new FourFragment();
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
            case R.id.frag1:
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.frag2:
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.frag3:
                mCurrentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.frag4:
                mCurrentFragmentIndex = FRAGMENT_FOUR;
                fragmentReplace(mCurrentFragmentIndex);
                break;
        }
    }
}
