package project.com.newsikdang;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Restaurant extends AppCompatActivity {

    DatabaseReference restaurantRef;

    TextView tvResName, tvResAddress, tvResPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);

        tvResName = findViewById(R.id.res_name);
        tvResAddress = findViewById(R.id.res_address);
        tvResPhone = findViewById(R.id.res_number);

        String key = getIntent().getStringExtra("key");

        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants")
                .child("3040000").child(key);

        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tvResName.setText(dataSnapshot.child("name").getValue().toString());
                tvResAddress.setText(dataSnapshot.child("address").getValue().toString());
                tvResPhone.setText(dataSnapshot.child("tel").getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}
