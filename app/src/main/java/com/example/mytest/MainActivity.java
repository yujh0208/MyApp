package com.example.mytest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText login_id, login_password;
    private Button login_button, join_button, main_button;
    private AlertDialog dialog;
    private AlertDialog.Builder dialog2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login);

        login_id = findViewById( R.id.ID );
        login_password = findViewById( R.id.PW );

        main_button = findViewById(R.id.goMain);
        main_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(intent);
            }
        });

        join_button = findViewById( R.id.join );
        join_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MainActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        });

        login_button = findViewById( R.id.login);
        login_button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserId = login_id.getText().toString();
                String UserPW = login_password.getText().toString();

                Response.Listener<String> responseLisner = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){//로그인 성공시
                                String UserId = jsonResponse.getString( "UserId" );
                                String UserPW = jsonResponse.getString( "UserPW" );
                                String UserName = jsonResponse.getString( "UserName" );
                                String UserNumber = jsonResponse.getString( "UserNumber" );
                                String UserAddr = jsonResponse.getString( "UserAddr" );
                                String UserAgree = jsonResponse.getString( "UserAgree" );

                                Toast.makeText(getApplicationContext(), String.format("%s님 로그인되셨습니다.", UserName), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity.this, ThirdActivity.class);
                                intent.putExtra( "UserId", UserId );
                                intent.putExtra( "UserPW", UserPW );
                                intent.putExtra( "UserName", UserName );
                                intent.putExtra( "UserNumber", UserNumber );
                                intent.putExtra( "UserAddr", UserAddr );
                                intent.putExtra( "UserAgree", UserAgree );

                                MainActivity.this.startActivity(intent);

                            }else {//여기 안되는거 구현해야함.
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyAlertDialogStyle);
                                dialog = builder.setMessage("아이디나 비밀번호를 확인해주세요")
                                        .setNegativeButton("다시 시도", null)
                                        .create();
                                dialog.show();
                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(UserId, UserPW, responseLisner);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });
    }
}
