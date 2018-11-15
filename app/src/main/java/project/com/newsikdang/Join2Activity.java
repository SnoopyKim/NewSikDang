package project.com.newsikdang;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Hashtable;

public class Join2Activity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference ref;

    ImageView ivProfile;
    EditText etName;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join2);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("users");

        ivProfile = findViewById(R.id.ivProfile);

        etName = findViewById(R.id.etName);
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    btnStart.setEnabled(false);
                    btnStart.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnStart.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    btnStart.setEnabled(true);
                    btnStart.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                    btnStart.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });

        btnStart = findViewById(R.id.btnStart);
        btnStart.setEnabled(false);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = FirebaseAuth.getInstance().getCurrentUser();

                //작성한 이름을 Firebase 계정에 DisplayName으로 설정
                UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                        .setDisplayName(etName.getText().toString()).build();
                user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Hashtable<String, String> userInfo = new Hashtable<String, String>();
                        userInfo.put("email", user.getEmail());
                        userInfo.put("name", user.getDisplayName());

                        ref.child(user.getUid()).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Hashtable<String, String> userSetting = new Hashtable<String, String>();
                                userSetting.put("cgg", "3040000");
                                ref.child(user.getUid()).child("setting").setValue(userSetting);
                            }
                        });
                        Intent intent = new Intent(Join2Activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}
