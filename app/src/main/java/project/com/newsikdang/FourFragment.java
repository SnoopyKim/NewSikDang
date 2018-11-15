package project.com.newsikdang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FourFragment extends Fragment implements View.OnClickListener {

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    ImageButton ibSetting;
    TextView tvUserName, tvUserEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourfragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(user.getUid());

        ibSetting = v.findViewById(R.id.ibSetting);
        ibSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
            }
        });

        tvUserName = v.findViewById(R.id.frag4_username);
        tvUserName.setText(user.getDisplayName());
        tvUserEmail = v.findViewById(R.id.frag4_useremail);
        tvUserEmail.setText(user.getEmail());

        return v;
    }
    @Override
    public void onClick(View v) {
    }
}