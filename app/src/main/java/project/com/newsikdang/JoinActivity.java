package project.com.newsikdang;

import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class JoinActivity extends AppCompatActivity {

    private String TAG = "JoinActivity";

    FirebaseAuth auth;

    EditText etEmail, etPW, etPW2;
    Button btnJoin;

    boolean b_email = false, b_pw = false, b_pw2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        auth = FirebaseAuth.getInstance();

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
                    if (b_pw && b_pw2) {
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
                    if (b_email && b_pw2) {
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
                    if (b_email && b_pw) {
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
                String stEmail = etEmail.getText().toString();
                String stPW = etPW.getText().toString();
                String stPW2 = etPW2.getText().toString();

                if (!stPW.equals(stPW2)) {
                    Toast.makeText(JoinActivity.this, "비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    etPW2.requestFocus();
                } else {

                    auth.createUserWithEmailAndPassword(stEmail,stPW)
                            .addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(JoinActivity.this, "등록에 실패하였습니다",Toast.LENGTH_SHORT).show();

                                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                        switch (errorCode) {

                                            case "ERROR_INVALID_EMAIL":
                                                Toast.makeText(JoinActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                                etEmail.setError("The email address is badly formatted.");
                                                etEmail.requestFocus();
                                                break;

                                            case "ERROR_WRONG_PASSWORD":
                                                Toast.makeText(JoinActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                                etPW.setError("password is incorrect ");
                                                etPW.requestFocus();
                                                etPW.setText("");
                                                break;

                                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                                Toast.makeText(JoinActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                                etEmail.setError("The email address is already exists.");
                                                etEmail.requestFocus();
                                                break;

                                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                                Toast.makeText(JoinActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                                etEmail.setError("The email address is already in use by another account.");
                                                etEmail.requestFocus();
                                                break;

                                            case "ERROR_WEAK_PASSWORD":
                                                Toast.makeText(JoinActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                                etPW.setError("The password is invalid it must 6 characters at least");
                                                etPW.requestFocus();
                                                break;
                                        }

                                        Log.w(TAG,task.getException().getMessage());

                                    } else {

                                        Toast.makeText(JoinActivity.this,"회원가입 되셨습니다.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(JoinActivity.this, Join2Activity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}
