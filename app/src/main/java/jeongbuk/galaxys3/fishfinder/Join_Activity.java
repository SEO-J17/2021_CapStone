package jeongbuk.galaxys3.fishfinder;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

        public class Join_Activity extends AppCompatActivity {
            private static String ip_addr = "192.168.79.77";
            private static String tag = "php test!";

            private EditText nickname;
            private EditText id;
            private EditText password;

            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_join);

                nickname = findViewById(R.id.edit_nickname);
                id = findViewById(R.id.edit_id);
                password = findViewById(R.id.edit_pw);

                Button register = findViewById(R.id.btn_register);
                register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Alias = nickname.getText().toString();
                String ID = id.getText().toString();
                String PW = password.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + ip_addr + "/join.php", Alias,ID,PW);


            }
        });
    }




    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        protected void onPreExecute(){
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Join_Activity.this, "pls wait",null,true,true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            Log.d(tag, "POST response  - " + result);
        }



        @Override
        protected String doInBackground(String... params) {
            String Alias =(String)params[1];
            String ID =(String)params[2];
            String PW =(String)params[3];

            String server_url = (String)params[0];
            String post_parameters = "Nickname="+ Alias + "&ID=" + ID + "&PW" + PW;
            try {

                URL url = new URL(server_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(post_parameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(tag, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString();


            } catch (Exception e) {
                Log.d(tag, "InsertData: Error ", e);
                return new String("Error: " + e.getMessage());
            }
        }
    }
}
