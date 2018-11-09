package project.com.newsikdang;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ThreeFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.threefragment, container, false);
        Button button = v.findViewById(R.id.bt_ok3);
        button.setOnClickListener(this); return v; }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok3:
                Toast.makeText(getActivity(), "ThreeFragment", Toast.LENGTH_SHORT) .show();
                break; }
    }
}