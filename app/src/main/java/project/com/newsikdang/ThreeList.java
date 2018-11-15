package project.com.newsikdang;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ThreeList extends AppCompatActivity {
    String frag3_username;
    String frag3_review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.threelist);
    }
    public String getFrag3_username() {return frag3_username;}
    public String getFrag3_review() {return frag3_review;}

    public ThreeList(String frag3_username, String frag3_review) {
        this.frag3_username = frag3_username;
        this.frag3_review = frag3_review;
    }
}
