package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Choice_Activity extends AppCompatActivity {
    private ImageButton logout;
    private ImageButton board;      //게시판 버튼
    private ImageButton camera;     //어종인식 버튼


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        final SharedPreferences sharedPreferences = getSharedPreferences("UserState",0);

        logout = (ImageButton) findViewById(R.id.img_btn_logout);
        board  = (ImageButton) findViewById(R.id.img_btn_board);
        camera = (ImageButton) findViewById(R.id.img_btn_camera);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefLoginstate), "loggedout");
                editor.apply();
                Intent intent = new Intent(Choice_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
