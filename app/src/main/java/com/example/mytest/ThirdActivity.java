package com.example.mytest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    private Button btnReturn, showInfo;
    private TextView showID, showPW, showNAME, showNUM, showADDR,showAGREE;
    private TableLayout showTable;
    AlertDialog.Builder dialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        setTitle("MAIN");

        showID = (TextView) findViewById(R.id.showID);
        showPW = (TextView) findViewById(R.id.showPW);
        showNAME = (TextView) findViewById(R.id.showNAME);
        showNUM = (TextView) findViewById(R.id.showNUM);
        showADDR = (TextView) findViewById(R.id.showADDR);
        showAGREE = (TextView) findViewById(R.id.showAGREE);
        Intent intent = getIntent();
        String UserId = intent.getStringExtra("UserId");
        String UserPW = intent.getStringExtra("UserPW");
        String UserName = intent.getStringExtra("UserName");
        String UserNumber = intent.getStringExtra("UserNumber");
        String UserAddr = intent.getStringExtra("UserAddr");
        String UserAgree = intent.getStringExtra("UserAgree");

        btnReturn = (Button) findViewById(R.id.btnReturn2);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        showTable = (TableLayout) findViewById(R.id.showTable);
        showInfo = (Button) findViewById(R.id.showInfo);
        showInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UserId == null) {//회원 정보 없을때
                    showTable.setVisibility(View.INVISIBLE);
                    AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this,R.style.MyAlertDialogStyle);
                    dialog = builder.setMessage("회원가입하시겠습니까")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ThirdActivity.this, RegisterActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    return;
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else { // 회원 정보 있을때

                    showTable.setVisibility(View.VISIBLE);

                    showID.setText(UserId);
                    showPW.setText(UserPW);
                    showNAME.setText(UserName);
                    showNUM.setText(UserNumber);
                    showADDR.setText(UserAddr);
                    showAGREE.setText(UserAgree);

                }
            }
        });
    }
}