package jeongbuk.galaxys3.fishfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.BinderThread;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;


public class Write_Activity extends AppCompatActivity {
    private Button content_regsiter;
    private ImageButton back;
    private ImageButton gallery;
    private EditText content;
    private EditText title;
    private ImageView imgview;
    private String imgpath;
    private MainActivity author = new MainActivity();
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        content_regsiter = (Button)findViewById(R.id.btn_content_regsiter);
        back = (ImageButton)findViewById(R.id.img_btn_back);
        gallery = (ImageButton)findViewById(R.id.img_btn_gallery);
        content = (EditText)findViewById(R.id.edit_content);
        title = (EditText)findViewById(R.id.edit_title);
        imgview = (ImageView)findViewById(R.id.board_view);
        String Writer = "testid";

        content_regsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Title = title.getText().toString();
                String Content = content.getText().toString();
                RegisterContent(Title, Content, Writer);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                //사진의 경로 객체 얻어오기
                Uri fileUri = data.getData();
                if(fileUri != null){
                    imgview.setImageURI(fileUri);
                    imgpath = getImgPath(fileUri);
                    new AlertDialog.Builder(this).setMessage(fileUri.toString()+"\n"+imgpath).create().show();
                }else{
                    Toast.makeText(getApplicationContext(), "사진을 선택하지 않았습니다!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //uri를 절대경로로 바꿔서 알려주는 메소드
    private String getImgPath(Uri fileUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, fileUri, proj, null, null,null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String realpath = cursor.getString(column_index);
        cursor.close();
        return realpath;
    }


    private void RegisterContent(String title, String content, String writer) {
        String url = "http://58.236.108.52/board.php";
        SimpleMultiPartRequest smpr= new SimpleMultiPartRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                new AlertDialog.Builder(Write_Activity.this).setMessage("응답:"+response).create().show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Write_Activity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        //요청 객체에 보낼 데이터를 추가
        smpr.addStringParam("writer", writer);
        smpr.addStringParam("title", title);
        smpr.addStringParam("content", content);
        //이미지 파일 추가
        smpr.addFile("img_path", imgpath);

        //요청객체를 서버로 보낼 우체통 같은 객체 생성
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(smpr);

    }
}
