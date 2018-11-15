package project.com.newsikdang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FourFragment extends Fragment implements View.OnClickListener {

    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourfragment, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users").child(user.getUid());

        return v;
    }
    @Override
    public void onClick(View v) {
    }
}