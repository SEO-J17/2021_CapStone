package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;



public class Join_Activity extends AppCompatActivity {
    private EditText nickname;
    private EditText id;
    private EditText password;
    private EditText chk_password;
    public String Alias;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        nickname = (EditText) findViewById(R.id.edit_nickname);
        id = (EditText) findViewById(R.id.edit_id);
        password = (EditText) findViewById(R.id.edit_pw);
        chk_password = (EditText) findViewById(R.id.chk_pw);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    //회원가입 실행 함수
    private void registerUser() {
        Alias = nickname.getText().toString();
        final String PW = password.getText().toString().trim();
        final String ID = id.getText().toString().trim();

        if (TextUtils.isEmpty(Alias)) {
            nickname.setError("Please enter Nickname");
            nickname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ID)) {
            id.setError("Please enter ID");
            id.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(PW)) {
            password.setError("Please enter Password");
            password.requestFocus();
            return;
        }

        String url = "http://58.236.108.52/register.php";
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")) {
                    Toast.makeText(Join_Activity.this, "회원가입에 성공했습니다!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Join_Activity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(Join_Activity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Join_Activity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        smpr.addStringParam("nickname", Alias);
        smpr.addStringParam("id", ID);
        smpr.addStringParam("password", PW);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);
    }
}

//        //외부 ip주소를 통한 접속. 외부에서 접속 가능.
//        String url = "http://58.236.108.52/register.php";
//        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (response.equals("Successfully Registered")) {
//                    Toast.makeText(Join_Activity.this, "회원가입에 성공했습니다!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(Join_Activity.this, MainActivity.class));
//                    finish();
//                } else {
//                    Toast.makeText(Join_Activity.this, response, Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Join_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> param = new HashMap<>();
//                param.put("nickname", Alias);
//                param.put("id", ID);
//                param.put("password", PW);
//
//                return param;
//            }
//        };
//
//        //기본정책 사용, 30초 넘으면 재시도
//        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        MySingleton.getmInstance(Join_Activity.this).addToRequestQueue(request);    // // Add a request