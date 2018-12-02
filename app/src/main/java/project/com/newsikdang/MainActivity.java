package project.com.newsikdang;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends FragmentActivity implements View.OnClickListener{
    final String TAG = "MainActivity";
    int mCurrentFragmentIndex;
    public final static int FRAGMENT_ONE = 0;
    public final static int FRAGMENT_TWO = 1;
    public final static int FRAGMENT_THREE = 2;
    public final static int FRAGMENT_FOUR = 3;

    FirebaseUser user;

    LinearLayout frag1, frag2, frag3, frag4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("userUid",user.getUid());

        frag1 = findViewById(R.id.frag1);
        frag1.setOnClickListener(this);
        frag2 = findViewById(R.id.frag2);
        frag2.setOnClickListener(this);
        frag3 = findViewById(R.id.frag3);
        frag3.setOnClickListener(this);
        frag4 = findViewById(R.id.frag4);
        frag4.setOnClickListener(this);

        mCurrentFragmentIndex = FRAGMENT_ONE;
        fragmentReplace(mCurrentFragmentIndex);

        frag1.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        frag1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        frag1.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });
        frag2.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        frag2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        frag2.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });
        frag3.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        frag3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        frag3.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });
        frag4.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event){
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:{
                        frag4.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.lightgray));
                        break;
                    }
                    case MotionEvent.ACTION_UP:{
                        frag4.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                        break;
                    }
                }
                return false;
            }
        });
    }



    public void fragmentReplace(int reqNewFragmentIndex) {
        Fragment newFragment = null; Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);
        newFragment = getFragment(reqNewFragmentIndex);
        // replace fragment
        final FragmentTransaction transaction = getSupportFragmentManager() .beginTransaction();
        transaction.replace(R.id.ll_fragment, newFragment);
        // Commit the transaction
        transaction.commit();
    }

    private Fragment getFragment(int idx) {
        Fragment newFragment = null;
        switch (idx) {
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

        ImageView img1 = findViewById(R.id.frag1_img);
        ImageView img2 = findViewById(R.id.frag2_img);
        ImageView img3 = findViewById(R.id.frag3_img);
        ImageView img4 = findViewById(R.id.frag4_img);


        switch (v.getId()) {
            case R.id.frag1:
                img1.setImageResource(R.drawable.tab_heart_red);
                img2.setImageResource(R.drawable.tab_gps);
                img3.setImageResource(R.drawable.tab_feed);
                img4.setImageResource(R.drawable.tab_user);
                mCurrentFragmentIndex = FRAGMENT_ONE;
                fragmentReplace(mCurrentFragmentIndex);
                frag1.setEnabled(false);
                frag2.setEnabled(true);
                frag3.setEnabled(true);
                frag4.setEnabled(true);
                break;
            case R.id.frag2:
                img1.setImageResource(R.drawable.tab_heart);
                img2.setImageResource(R.drawable.tab_gps_red);
                img3.setImageResource(R.drawable.tab_feed);
                img4.setImageResource(R.drawable.tab_user);
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
                frag1.setEnabled(true);
                frag2.setEnabled(false);
                frag3.setEnabled(true);
                frag4.setEnabled(true);
                break;
            case R.id.frag3:
                img1.setImageResource(R.drawable.tab_heart);
                img2.setImageResource(R.drawable.tab_gps);
                img3.setImageResource(R.drawable.tab_feed_red);
                img4.setImageResource(R.drawable.tab_user);
                mCurrentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(mCurrentFragmentIndex);
                frag1.setEnabled(true);
                frag2.setEnabled(true);
                frag3.setEnabled(false);
                frag4.setEnabled(true);
                break;
            case R.id.frag4:
                img1.setImageResource(R.drawable.tab_heart);
                img2.setImageResource(R.drawable.tab_gps);
                img3.setImageResource(R.drawable.tab_feed);
                img4.setImageResource(R.drawable.tab_user_red);
                mCurrentFragmentIndex = FRAGMENT_FOUR;
                fragmentReplace(mCurrentFragmentIndex);
                frag1.setEnabled(true);
                frag2.setEnabled(true);
                frag3.setEnabled(true);
                frag4.setEnabled(false);
                break;
        }
    }
}
