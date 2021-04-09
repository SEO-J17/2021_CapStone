package jeongbuk.galaxys3.fishfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private Button join;
    private Button login;
    private EditText id;
    private EditText pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       

        join = findViewById(R.id.btn_join);
        login = findViewById(R.id.btn_login);
        id = findViewById(R.id.edit_id);
        pw = findViewById(R.id.edit_pw);


        //회원가입 버튼 이벤트
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Join_Activity.class);
                startActivity(intent);
            }
        });


        //로그인 버튼 이벤트



    }
}