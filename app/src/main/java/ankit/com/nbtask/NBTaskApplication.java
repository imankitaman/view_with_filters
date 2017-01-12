package ankit.com.nbtask;

import android.app.Application;
import android.content.Context;

import ankit.com.nbtask.network.VolleyRequestQueue;

/**
 * Created by ankit
 */
public class NBTaskApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VolleyRequestQueue.initRequestQueue(this);
    }

}
