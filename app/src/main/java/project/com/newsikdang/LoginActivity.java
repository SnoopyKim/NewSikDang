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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";

    EditText etID, etPW;
    Button btnLogin;

    FirebaseAuth mAuth;

    boolean b_id = false, b_pw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etID = findViewById(R.id.etID);
        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals("")) {
                    b_id = false;
                    etID.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnLogin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_id = true;
                    etID.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                    if (b_pw) {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnLogin.setTextColor(getResources().getColor(R.color.white));
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
                    etPW.setBackgroundTintList(getResources().getColorStateList(R.color.gray));
                    btnLogin.setEnabled(false);
                    btnLogin.setBackgroundTintList(getResources().getColorStateList(R.color.btnDisabled));
                    btnLogin.setTextColor(getResources().getColor(R.color.textBtn));
                } else {
                    b_pw = true;
                    etPW.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                    if (b_id) {
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundTintList(getResources().getColorStateList(R.color.btnAbled));
                        btnLogin.setTextColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setEnabled(false);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etID.getText().toString();
                String password = etPW.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            setResult(101);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
