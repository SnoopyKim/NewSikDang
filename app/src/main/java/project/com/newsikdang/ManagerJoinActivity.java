package project.com.newsikdang;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Hashtable;

public class ManagerJoinActivity extends AppCompatActivity {
    private String TAG = "ManagerJoinActivity";

    FirebaseAuth auth;
    DatabaseReference managerRef,restaurantRef;

    EditText etSnum, etDate, etEmail, etPW, etPW2;
    Button btnJoin;

    boolean b_snum=false, b_date=false, b_email=false, b_pw=false, b_pw2=false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_join);

        auth = FirebaseAuth.getInstance();
        managerRef = FirebaseDatabase.getInstance().getReference("users").child("manager");
        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child("3040000");

        etSnum = findViewById(R.id.etSnum);
        etSnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_snum = false;
                    btnJoin.setEnabled(false);
                    btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnJoin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_snum = true;
                    if (b_date && b_email && b_pw && b_pw2) {
                        btnJoin.setEnabled(true);
                        btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnJoin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
        etDate = findViewById(R.id.etDate);
        etDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_date = false;
                    btnJoin.setEnabled(false);
                    btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnJoin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_date = true;
                    if (b_snum && b_email && b_pw && b_pw2) {
                        btnJoin.setEnabled(true);
                        btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnJoin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
        etEmail = findViewById(R.id.etEmail);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_email = false;
                    btnJoin.setEnabled(false);
                    btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnJoin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_email = true;
                    if (b_snum && b_date && b_pw && b_pw2) {
                        btnJoin.setEnabled(true);
                        btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnJoin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
        etPW = findViewById(R.id.etPW);
        etPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_pw = false;
                    btnJoin.setEnabled(false);
                    btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnJoin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_pw = true;
                    if (b_snum && b_date && b_email && b_pw2) {
                        btnJoin.setEnabled(true);
                        btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnJoin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });
        etPW2 = findViewById(R.id.etPW2);
        etPW2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_pw2 = false;
                    btnJoin.setEnabled(false);
                    btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnJoin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_pw2 = true;
                    if (b_snum && b_date && b_email && b_pw) {
                        btnJoin.setEnabled(true);
                        btnJoin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnJoin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });

        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setEnabled(false);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stPW = etPW.getText().toString();
                String stPW2 = etPW2.getText().toString();

                if (!stPW.equals(stPW2)) {
                    Toast.makeText(getApplicationContext(), "비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    etPW2.requestFocus();

                } else {
                    Query query = restaurantRef.orderByChild("date").equalTo(Integer.valueOf(etDate.getText().toString()));
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String stSnum = etSnum.getText().toString();
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    if (data.child("snum").getValue().toString().equals(stSnum)) {
                                        joinUser(data);
                                        return;
                                    }
                                }
                                Toast.makeText(getApplicationContext(),"업소일련번호를 확인하세요.",Toast.LENGTH_SHORT).show();
                            } else { Toast.makeText(getApplicationContext(),"영업자시작일을 확인하세요.", Toast.LENGTH_SHORT).show(); }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) { }
                    });

                }
            }
        });
    }

    public void joinUser(final DataSnapshot data) {
        auth.createUserWithEmailAndPassword(etEmail.getText().toString(),etPW.getText().toString())
                .addOnCompleteListener(ManagerJoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "등록에 실패하였습니다",Toast.LENGTH_SHORT).show();

                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                            switch (errorCode) {

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(getApplicationContext(), "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                    etEmail.setError("The email address is badly formatted.");
                                    etEmail.requestFocus();
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    Toast.makeText(getApplicationContext(), "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                    etPW.setError("password is incorrect ");
                                    etPW.requestFocus();
                                    etPW.setText("");
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    Toast.makeText(getApplicationContext(), "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                    etEmail.setError("The email address is already exists.");
                                    etEmail.requestFocus();
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(getApplicationContext(), "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                    etEmail.setError("The email address is already in use by another account.");
                                    etEmail.requestFocus();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(getApplicationContext(), "The given password is invalid.", Toast.LENGTH_LONG).show();
                                    etPW.setError("The password is invalid it must 6 characters at least");
                                    etPW.requestFocus();
                                    break;
                            }

                            Log.w(TAG,task.getException().getMessage());

                        } else {

                            final FirebaseUser user = task.getResult().getUser();

                            Hashtable<String, String> userInfo = new Hashtable<String, String>();
                            userInfo.put("restaurant", data.getKey());
                            userInfo.put("email", user.getEmail());
                            userInfo.put("name", data.child("name").getValue().toString());

                            managerRef.child(user.getUid()).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"회원가입 되셨습니다.",Toast.LENGTH_SHORT).show();
                                    setResult(104);
                                    finish();
                                }
                            });

                        }
                    }
                });
    }
}
