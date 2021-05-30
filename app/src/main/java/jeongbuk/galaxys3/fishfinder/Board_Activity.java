package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.Inet4Address;
import java.util.ArrayList;

public class Board_Activity extends AppCompatActivity implements MyRecyclerAdapter.MyRecyclerViewClickListener {
    FloatingActionButton floatingActionButton ;
    ArrayList<ItemData> dataList = new ArrayList<>();
    MyRecyclerAdapter adapter = new MyRecyclerAdapter(dataList);
    static int i=0;
//    int[] cat = {R.drawable.pic001, R.drawable.pic002, R.drawable.pic003,
//            R.drawable.pic004, R.drawable.pic005, R.drawable.pic006,
//            R.drawable.pic007, R.drawable.pic008, R.drawable.pic009,
//            R.drawable.pic010};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.btn_floating);

        RecyclerView recyclerView = findViewById(R.id.recylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(this);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board_Activity.this, Write_Activity.class);
                startActivity(intent);
            }
        });
    }

    //리스트 클릭 이벤트

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(getApplicationContext(), ""+(position+1), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClicked(int position) {

    }

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