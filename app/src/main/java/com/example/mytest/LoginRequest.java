package com.example.mytest;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    //서버 URL 설정(php 파일 연동)
    final static private String URL = "http://juhee28.dothome.co.kr/login_User.php";
    private Map<String, String> map;

    public LoginRequest(String UserId, String UserPW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("UserId", UserId);
        map.put("UserPW", UserPW);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}