package project.com.newsikdang;

<<<<<<< HEAD
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
>>>>>>> refs/remotes/origin/songdahee
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD

    TextView tvTest;
    Button btnMap;

    static final String API_KEY = "72676c564e6e6f6d37326c75637a4a";
    static final String API_URL = "http://openapi.gwangjin.go.kr:8088";

=======
>>>>>>> refs/remotes/origin/songdahee
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        tvTest = findViewById(R.id.tvTest);

        btnMap = findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
            }
        });

        new RetrieveFeedTask().execute();
=======
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
>>>>>>> refs/remotes/origin/songdahee

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
