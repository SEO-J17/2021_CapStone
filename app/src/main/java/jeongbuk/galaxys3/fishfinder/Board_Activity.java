package jeongbuk.galaxys3.fishfinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Board_Activity extends AppCompatActivity {
    FloatingActionButton floatingActionButton ;
    MyRecyclerAdapter adapter;
    ArrayList<ItemData> items = new ArrayList<>();
    ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.btn_floating);
        btn_back = (ImageButton) findViewById(R.id.board_img_btn_back);

        RecyclerView recyclerView = findViewById(R.id.recylerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new MyRecyclerAdapter(this, items);
        recyclerView.setAdapter(adapter);

        loadDB();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Board_Activity.this, Write_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void loadDB() {
        String url ="http://211.232.201.35/board_load.php";
        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(Request.Method.POST, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                items.clear();
                adapter.notifyDataSetChanged();
                try {
                    for(int i=0;i<response.length();i++){
                        JSONObject jsonObject= response.getJSONObject(i);

                        String name = jsonObject.getString("writer");
                        String date = jsonObject.getString("post_time");
                        String title = jsonObject.getString("title");
                        String content = jsonObject.getString("content");
                        String imgpath = jsonObject.getString("img_path");
                        imgpath = "http://211.232.201.35/"+imgpath;

                        items.add(0, new ItemData(name, imgpath, date, title , content));
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

}