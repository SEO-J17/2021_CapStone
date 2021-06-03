package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;
import java.util.ArrayList;

public class Board_Activity extends AppCompatActivity {
    FloatingActionButton floatingActionButton ;
    ArrayList<ItemData> dataList = new ArrayList<>();
    MyRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.btn_floating);

        RecyclerView recyclerView = findViewById(R.id.recylerview);
        adapter = new MyRecyclerAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        loadDB();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board_Activity.this, Write_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void loadDB() {
        String url ="http://58.236.108.52/board_load.php";
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(Board_Activity.this, response.toString(), Toast.LENGTH_SHORT).show();
                dataList.clear();
                adapter.notifyDataSetChanged();
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject= response.getJSONObject(i);

                        int number = Integer.parseInt(jsonObject.getString("content_serial"));
                        String name = jsonObject.getString("writer");
                        String date = jsonObject.getString("post_time");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("conetent");
                        String imgpath = jsonObject.getString("img_path");
                        //이미지 경로의 경우 서버 IP가 제외된 주소이므로(uploads/xxxx.jpg) 바로 사용 불가.
                        imgpath = "http://58.236.108.52/"+imgpath;

                        dataList.add(0, new ItemData(number, name, imgpath, date, title , content));
                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Board_Activity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    //리스트 클릭 이벤트


//    @Override
//    public void onItemLongClicked(int position) {
//        adapter.remove(position);
//        Toast.makeText(getApplicationContext(),"리스트 삭제", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onTitleClicked(int position) {
//        Toast.makeText(getApplicationContext(),dataList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onContentClicked(int position) {
//        Toast.makeText(getApplicationContext(),dataList.get(position).getContent(), Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onImageViewClicked(int position) {
//        Toast.makeText(getApplicationContext(), "Image", Toast.LENGTH_SHORT).show();
//    }

//    // 다이얼로그를 사용해 list_insert.xml과 연결
//    public void onMenuInsert(View view) {
//
//        final View innerView = getLayoutInflater().inflate(R.layout.list_item, null);
//        final Dialog mDialog = new Dialog(this);
//        mDialog.setTitle("Title");
//        mDialog.setContentView(innerView);
//        mDialog.setCancelable(true);
//
//        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.MATCH_PARENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        mDialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
//
//        final EditText editTitle = mDialog.findViewById(R.id.addTitle);
//        final EditText editCont = mDialog.findViewById(R.id.addContent);
//        Button btn_go = mDialog.findViewById(R.id.btn_go);
//        Button btn_cancel = mDialog.findViewById(R.id.btn_cancel);
//
//        //입력버튼을 누르면 실행되는 이벤트
//        btn_go.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String myTitle = editTitle.getText().toString();
//                String myCont = editCont.getText().toString();
//
//                dataList.add(new ItemData(cat[i], myTitle, myCont));
//                Toast.makeText(getApplicationContext(), myTitle, Toast.LENGTH_SHORT).show();
//                mDialog.dismiss();
//            }
//        });
//
//        //취소를 누르면 다이얼로그 종료
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDialog.dismiss();
//            }
//        });
//
//        mDialog.show();
//        adapter.notifyDataSetChanged();
//    }
//
//    //자동추가 버튼 클릭시 리스트가 자동으로 추가되도록 하는 이벤트
//    public void onAutoInsert(View view) {
//        for (int i=0; i<10; i++) {
//            dataList.add(new ItemData(cat[i], "TITLE : "+(i+1),
//                    String.format("리사이클러뷰 %02d", (i+1))));
//        }
//        adapter.notifyDataSetChanged();
//    }
}