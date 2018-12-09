package project.com.newsikdang;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Switch;

public class ResNotice extends Dialog implements View.OnClickListener {

    Switch sw_notice;
    ImageButton close;
    public ResNotice(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.
        setContentView(R.layout.activity_one_inter_choise);     //다이얼로그에서 사용할 레이아웃입니다.

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_notice);

        close = this.findViewById(R.id.close);
        close.setOnClickListener(this);

        sw_notice = this.findViewById(R.id.sw_notice);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close:
                dismiss();
                break;
        }
    }
}
