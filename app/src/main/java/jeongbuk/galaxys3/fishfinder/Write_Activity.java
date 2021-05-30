package jeongbuk.galaxys3.fishfinder;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Write_Activity extends AppCompatActivity {
    private Button content_regsiter;
    private ImageButton back;
    private ImageButton gallery;
    private EditText content;
    private EditText title;
    private ImageView imgview;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        content_regsiter = (Button)findViewById(R.id.btn_content_regsiter);
        back = (ImageButton)findViewById(R.id.img_btn_back);
        gallery = (ImageButton)findViewById(R.id.img_btn_gallery);
        content = (EditText)findViewById(R.id.edit_content);
        title = (EditText)findViewById(R.id.edit_title);
        imgview = (ImageView)findViewById(R.id.board_view);


        content_regsiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title = title.getText().toString();
                String Content = content.getText().toString();
                RegisterContent(Title, Content);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 101);
            }
        });
    }

    private void RegisterContent(final String title, final String content) {
        final ProgressDialog progressDialog = new ProgressDialog(Write_Activity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("등록중 입니다.");
        progressDialog.show();

        String url = "http://58.236.108.52/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        public void onResponse(String response) {
            if (response.equals("Successfully Registered")) {
                startActivity(new Intent(Write_Activity.this, Board_Activity.class));
                finish();
            } else {
                Toast.makeText(Write_Activity.this, response, Toast.LENGTH_SHORT).show();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(Write_Activity.this, error.toString(), Toast.LENGTH_SHORT).show();
        }
    }) {
        protected Map<String, String> getParams() throws AuthFailureError {
            HashMap<String, String> param = new HashMap<>();
            param.put("title", title);
            param.put("id", content);

            return param;
        }
    };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                                      DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Write_Activity.this).addToRequestQueue(request);    // // Add a request
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri);
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
                    imgview.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                    instream.close();   // 스트림 닫아주기
                    Toast.makeText(getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
