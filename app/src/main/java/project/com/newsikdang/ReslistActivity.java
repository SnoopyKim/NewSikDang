package project.com.newsikdang;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ReslistActivity extends AppCompatActivity {
    String restxt1;
    String restxt2;
    String restxt3;
    String restxt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reslist);
    }
    public String getRestxt1() {return restxt1;}
    public String getRestxt2() {return restxt2;}
    public String getRestxt3() {return restxt3;}
    public String getRestxt4() {return restxt4;}

    public ReslistActivity(String restxt1, String restxt2, String restxt3, String restxt4) {
        this.restxt1 = restxt1;
        this.restxt2 = restxt2;
        this.restxt3 = restxt3;
        this.restxt4 = restxt4;
    }
}
