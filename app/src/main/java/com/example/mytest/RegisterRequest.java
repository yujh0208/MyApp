package com.example.mytest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://juhee28.dothome.co.kr/Register.php";
    private Map<String, String> map;
    //private Map<String, String>parameters;


    public RegisterRequest(String UserId, String UserPW, String UserName, String UserNumber, String UserAddr, String UserAgree, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파마미터들을 전송함
        map = new HashMap<>();
        map.put("UserId", UserId);
        map.put("UserPW", UserPW);
        map.put("UserName", UserName);
        map.put("UserNumber", UserNumber);
        map.put("UserAddr", UserAddr);
        map.put("UserAgree", UserAgree);
    }

    @Override

    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
