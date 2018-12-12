package project.com.newsikdang;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class OneInterChoise extends Dialog implements View.OnClickListener
{
    TextView tv1, tv2;
    public OneInterChoise(Context context)
     {
         super(context);
         requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
         getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
         setContentView(R.layout.activity_one_inter_choise);     //다이얼로그에서 사용할 레이아웃입니다.


     }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_inter_choise);

        tv1 = findViewById(R.id.inter_tv1);
        tv1.setOnClickListener(this);
        tv2 = findViewById(R.id.inter_tv2);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inter_tv1:
                Intent intent = new Intent(getContext(),AreaActivity.class);
                getContext().startActivity(intent);
                dismiss();
                break;
            case R.id.inter_tv2:
                dismiss();
                break;
        }
    }
}
