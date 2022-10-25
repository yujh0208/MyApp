package com.example.mytest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        setTitle("JOIN");

        //R.id.button2 이 XML 파일에 선언이 꼭 되어야함.
        Button btnJoin = (Button) findViewById(R.id.btnJoin);
        TextView id = (EditText) findViewById(R.id.inputID);
        TextView pw = (EditText) findViewById(R.id.inputPW);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check_validation(id, pw);
            }
        });
        Button btnReturn = (Button) findViewById(R.id.btnReturn);

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

    }
//
//    void check_validation(TextView id, TextView pw) {
//         비밀번호 유효성 검사식1 : 숫자, 특수문자가 포함되어야 한다.
//        String val_symbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";
//        // 비밀번호 유효성 검사식2 : 영문자 대소문자가 적어도 하나씩은 포함되어야 한다.
//        String val_alpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";
//        // 정규표현식 컴파일
//        Pattern pattern_symbol = Pattern.compile(val_symbol);
//        Pattern pattern_alpha = Pattern.compile(val_alpha);
//
//        Matcher matcher_symbol = pattern_symbol.matcher(pw);
//        Matcher matcher_alpha = pattern_alpha.matcher(pw);
//
//        if (matcher_symbol.find() && matcher_alpha.find()) {
//            // email과 password로 회원가입 진행
//            //email_signIn(id, pw);
//            finish();
//        }else {
//            Toast.makeText(getApplicationContext(),"비밀번호로 부적절합니다", Toast.LENGTH_SHORT).show();
//        }
//    }
}
