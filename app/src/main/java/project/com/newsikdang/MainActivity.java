package project.com.newsikdang;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

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

        ImageButton imgbtn1 = findViewById(R.id.imgbtn1);
        imgbtn1.setOnClickListener(this);
        ImageButton imgbtn2 = findViewById(R.id.imgbtn2);
        imgbtn2.setOnClickListener(this);
        ImageButton imgbtn3 = findViewById(R.id.imgbtn3);
        imgbtn3.setOnClickListener(this);
        ImageButton imgbtn4 = findViewById(R.id.imgbtn4);
        imgbtn4.setOnClickListener(this);
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
            case R.id.imgbtn1:
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.imgbtn2:
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.imgbtn3:
                mCurrentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(mCurrentFragmentIndex);
                break;
            case R.id.imgbtn4:
                mCurrentFragmentIndex = FRAGMENT_FOUR;
                fragmentReplace(mCurrentFragmentIndex);
                break;
        }
    }
}
