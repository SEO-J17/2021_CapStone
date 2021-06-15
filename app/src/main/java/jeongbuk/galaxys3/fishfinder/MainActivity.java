package jeongbuk.galaxys3.fishfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private Button join;
    private Button login;
    private EditText id;
    private EditText pw;
    private CheckBox loginstate;
    private SharedPreferences sharedPreferences;
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("UserState", Context.MODE_PRIVATE);
        join = findViewById(R.id.btn_join);
        login = findViewById(R.id.btn_login);
        id = findViewById(R.id.write_id);
        pw = findViewById(R.id.write_pw);
        loginstate = findViewById(R.id.chk_login);

        //회원가입 버튼 이벤트
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Join_Activity.class);
                startActivity(intent);
            }
        });


        //로그인 버튼 이벤트
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //패스워드와 아이디를 파라미터로 받아 클래스로 전송하기.
                String ID = id.getText().toString().trim();
                String PW = pw.getText().toString().trim();
                if (TextUtils.isEmpty(ID) || TextUtils.isEmpty(PW)) {
                    Toast.makeText(MainActivity.this, "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    login(ID, PW);
                }
            }
        });

        //sharedPreference 자동로그인
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefLoginstate), "");
        if (loginStatus.equals("loggedin")) {
            startActivity(new Intent(MainActivity.this, Choice_Activity.class));
        }
        
    }

    private void login(final String id, final String pw) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("로그인중 입니다.");
        progressDialog.show();
         String url = "http://211.232.201.35/login.php";
          SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
            public void onResponse(String response) {
                if (response.equals("Login Success")) {
                    name = id;
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (loginstate.isChecked()) {
                        editor.putString(getResources().getString(R.string.prefLoginstate), "loggedin");
                    } else {
                        editor.putString(getResources().getString(R.string.prefLoginstate), "loggedout");
                    }
                    editor.apply();
                    Intent intent = new Intent(MainActivity.this, Choice_Activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
        }
    });

        smpr.addStringParam("id", id);
        smpr.addStringParam("password", pw);
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);
    }
}




//        String uRl = "http://58.236.108.52/login.php";
//        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.equals("Login Success")) {
//                    name = id;
//                    progressDialog.dismiss();
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    if (loginstate.isChecked()) {
//                        editor.putString(getResources().getString(R.string.prefLoginstate), "loggedin");
//                    } else {
//                        editor.putString(getResources().getString(R.string.prefLoginstate), "loggedout");
//                    }
//                    editor.apply();
//                    Intent intent = new Intent(MainActivity.this, Choice_Activity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<>();
//                param.put("id", id);
//                param.put("password", pw);
//                return param;
//            }
//        };
//
//
//        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getmInstance(MainActivity.this).addToRequestQueue(request);
//
//    }