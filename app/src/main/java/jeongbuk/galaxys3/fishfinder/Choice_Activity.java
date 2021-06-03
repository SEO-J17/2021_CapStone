package jeongbuk.galaxys3.fishfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice_Activity.this, Camera_Activity.class);
                startActivity(intent);
            }
        });

        board.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Choice_Activity.this, Board_Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK ) {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Quit").setMessage("종료하시겠습니까?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick( DialogInterface dialog, int which) {
                    moveTaskToBack(true); // 본Activity finish후 다른 Activity가 뜨는 걸 방지.
                    finish();
                    //application 프로세스를 강제 종료
                    android.os.Process.killProcess(android.os.Process.myPid() );
                }
            }).setNegativeButton( "No", null ).show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
