package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;


public class Join_Activity extends AppCompatActivity {
            EditText nickname;
            EditText id;
            EditText password;

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_join);
                //progressBar = (ProgressBar) findViewById(R.id.progressBar);
                nickname = (EditText) findViewById(R.id.edit_nickname);
                id = (EditText) findViewById(R.id.edit_id);
                password = (EditText) findViewById(R.id.edit_pw);

                findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registerUser();
                    }
                });

            }

            private void registerUser() {
                final String Alias = nickname.getText().toString().trim();
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
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Successfully Registered")) {
                            Toast.makeText(Join_Activity.this, response, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Join_Activity.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Join_Activity.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Join_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    protected Map<String, String> getParams() throws AuthFailureError{
                        HashMap<String,String> param = new HashMap<>();
                        param.put("nickname", Alias);
                        param.put("id", ID);
                        param.put("password", PW);

                        return param;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(Join_Activity.this).addToRequestQueue(request);

            }
}
