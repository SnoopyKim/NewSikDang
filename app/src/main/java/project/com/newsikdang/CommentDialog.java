package project.com.newsikdang;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentDialog extends Dialog {

    DatabaseReference revRef;

    EditText etComment;
    TextView tv1, tv2;

    public CommentDialog(@NonNull Context context, String revKey) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   //다이얼로그의 타이틀바를 없애주는 옵션입니다.
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  //다이얼로그의 배경을 투명으로 만듭니다.

        revRef = FirebaseDatabase.getInstance().getReference("reviews").child("3040000").child(revKey);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comment);     //다이얼로그에서 사용할 레이아웃입니다.

        etComment = findViewById(R.id.et_dialog_comment);

        tv1 = findViewById(R.id.inter_tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stComment = etComment.getText().toString();
                if (stComment.equals("")) { Toast.makeText(getContext(), "답글 내용을 작성해주세요!", Toast.LENGTH_SHORT).show(); }
                else {
                    revRef.child("comment").setValue(stComment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "답글을 달았습니다.", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                    });
                }
            }
        });
        tv2 = findViewById(R.id.inter_tv2);
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

}
