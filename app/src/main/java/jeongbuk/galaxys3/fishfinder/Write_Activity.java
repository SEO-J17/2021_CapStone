package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

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


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                BringPhoto();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 1);
            }
        });


    }

    @Override

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK)
            return;
        switch(requestCode){
            case PICK_FROM_ALBUM:
            {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
                Log.d("SmartWheel",mImageCaptureUri.getPath().toString());
            }


            case PICK_FROM_CAMERA:
            {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 200); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 1); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;

            }
            case CROP_FROM_iMAGE:
            {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();
                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+
                        "/SmartWheel/"+System.currentTimeMillis()+".jpg";


                if(extras != null){
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    iv_UserPhoto.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;
                }

                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                {
                    f.delete();
                }
            }
        }

    }



}
