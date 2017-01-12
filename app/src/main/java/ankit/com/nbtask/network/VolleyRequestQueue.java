package ankit.com.nbtask.network;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * VolleyRequestQueue manages a queue for the API requests by maintaining a Tag for
 * each request for identification. This is a singleton.
 *
 * @author Ankit Aman
 */
public final class VolleyRequestQueue{

    private static VolleyRequestQueue instance;
    private RequestQueue mRequestQueue;

    public static final String TAG = VolleyRequestQueue.class.getSimpleName();

    private VolleyRequestQueue(RequestQueue mRequestQueue){
        this.mRequestQueue = mRequestQueue;
    }

    public static void initRequestQueue(Context applicationContext) {
        synchronized (VolleyRequestQueue.class) {
            if (instance == null) {
                synchronized (VolleyRequestQueue.class) {
                    RequestQueue mRequestQueue = Volley.newRequestQueue(applicationContext);
                    instance = new VolleyRequestQueue(mRequestQueue);
                }
            }
        }
    }

    public static VolleyRequestQueue instance() {
        if (instance == null) {
            throw new RuntimeException("RequestQueue is not initialized");
        }
        return instance;
    }

    public RequestQueue requestQ(){
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        instance().requestQ().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        instance().requestQ().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        instance().requestQ().cancelAll(tag);
    }


}
