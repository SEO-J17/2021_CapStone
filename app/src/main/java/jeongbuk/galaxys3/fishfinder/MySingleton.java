package jeongbuk.galaxys3.fishfinder;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton mInstance;
    private RequestQueue mRequestQueue;
    private Context mCtx;

    public MySingleton(Context mCtx) {
        this.mCtx = mCtx;
        mRequestQueue = getmRequestQueue();
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 1024*1024);     //1MB CAP
            Network network = new BasicNetwork(new HurlStack());    //Set up the network to use HttpURLConnection as the HTTP client.
            mRequestQueue = new RequestQueue(cache, network);   //Instantiate the RequestQueue with the cache and network. Start the queue.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized MySingleton getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MySingleton(context);
        }
        return mInstance;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        mRequestQueue.add(request);
    }
}
