package project.com.newsikdang;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FourFragment extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fourfragment, container, false);
        Button button = v.findViewById(R.id.bt_ok4);
        button.setOnClickListener(this); return v; }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_ok4:
                Toast.makeText(getActivity(), "FourFragment", Toast.LENGTH_SHORT) .show();
                break; }
    }
}