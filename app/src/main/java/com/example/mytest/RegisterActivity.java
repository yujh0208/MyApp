package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText join_id, join_password, join_name, join_number,join_address, join_pwck;
    private String UserAgree;
    private Button btnJoin,PWCKButton, validateButton,btnReturn;
    private AlertDialog dialog;
    private boolean validate = false;
    private boolean PWCK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_join);

        //아이디값 찾아주기
        join_id = findViewById( R.id.inputID );
        join_password = findViewById( R.id.inputPW );
        join_pwck = findViewById(R.id.PWCheck);
        join_name = findViewById( R.id.inputName );
        join_number = findViewById( R.id.inputNumber );
        join_address = findViewById( R.id.inputAddress );

        RadioGroup AgreeGroup = (RadioGroup)findViewById(R.id.AgreeGroup);
        int agreeGroupID = AgreeGroup.getCheckedRadioButtonId();
        UserAgree = ((RadioButton)findViewById(R.id.agree)).getText().toString();//초기화 값을 지정해줌

        //라디오버튼이 눌리면 값을 바꿔주는 부분
        AgreeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton agreeButton = (RadioButton)findViewById(i);
                UserAgree = agreeButton.getText().toString();
            }
        });
        //돌아가기 버튼 구현
        btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        PWCKButton = findViewById(R.id.PWCKButton);
        PWCKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserPW = join_password.getText().toString();
                String CheckPW = join_pwck.getText().toString();
                if (PWCK) {
                    return; //검증 완료
                }
                if(UserPW.equals(CheckPW)) {
                    //비밀번호 유효성 검사 8-20자리, 영어, 문자 포함
                    if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!?@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", UserPW))
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                        dialog = builder.setMessage("비밀번호 형식을 지켜주세요.").setPositiveButton("확인", null).create();
                        dialog.show();
                    } else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                        dialog = builder.setMessage("사용할 수 있는 비밀번호입니다.").setPositiveButton("확인", null).create();
                        dialog.show();
                        PWCK = true; //검증 완료
                        PWCKButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                    dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create();
                    dialog.show();
                    return;
                }
            }
        });


        //아이디 중복 체크
        validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String UserId = join_id.getText().toString();
                if (validate) {
                    return; //검증 완료
                }
                if (UserId.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                    dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create();
                                dialog.show();
                                join_id.setEnabled(false); //아이디값 고정
                                validate = true; //검증 완료
                                validateButton.setBackgroundColor(getResources().getColor(R.color.colorGray));
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                                dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(UserId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);
            }
        });
        //회원가입 버튼 클릭 시 수행
        btnJoin = findViewById(R.id.btnJoin);
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = join_id.getText().toString();
                String UserPW = join_password.getText().toString();
                String UserName = join_name.getText().toString();
                String UserNumber = join_number.getText().toString();
                String UserAddr = join_address.getText().toString();

                if(!validate){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                    dialog = builder.setMessage("아이디 확인을 해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                }
                if(!PWCK){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                    dialog = builder.setMessage("비밀번호 확인을 해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );


                            //회원가입 성공시
                            if(UserId.equals("")||UserPW.equals("")||UserName.equals("")||UserNumber.equals("")||UserAddr.equals("")){
                                if (!success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.MyAlertDialogStyle);
                                    dialog = builder.setMessage("빈 칸 없이 입력해주세요")
                                            .setNegativeButton("확인",null)
                                            .create();
                                    dialog.show();
                                    Toast.makeText(getApplicationContext(), "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //회원가입 실패시
                            } else {
                                Toast.makeText(getApplicationContext(), String.format("%s님 가입을 환영합니다.", UserName), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(UserId, UserPW, UserName, UserNumber, UserAddr, UserAgree, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(dialog != null){
            dialog.dismiss();
            dialog = null;
        }
    }
}