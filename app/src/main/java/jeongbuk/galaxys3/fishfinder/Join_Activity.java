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
        final String Chk_PW = chk_password.getText().toString().trim();

        if (TextUtils.isEmpty(Alias)) {
            nickname.setError("닉네임을 입력하세요");
            nickname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(ID)) {
            id.setError("아이디를 입력하세요");
            id.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(PW)) {
            password.setError("패스워드를 입력하세요");
            password.requestFocus();
            return;
        }
        if(!TextUtils.equals(PW, Chk_PW)){
            chk_password.setError("패스워드가 다릅니다!");
            chk_password.setText("");
            chk_password.requestFocus();
            return;
        }

        String url = "http://211.232.201.35/register.php";
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")) {
                    Toast.makeText(Join_Activity.this, "회원가입을 완료했습니다.", Toast.LENGTH_SHORT).show();
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
