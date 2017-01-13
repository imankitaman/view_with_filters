package ankit.com.nbtask;

import android.app.Application;
import android.content.Context;

import ankit.com.nbtask.Utils.AndroidPreference;
import ankit.com.nbtask.network.VolleyRequestQueue;

/**
 * Created by ankit
 */
public class NBTaskApplication extends Application {

    public static AndroidPreference androidPreference;
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyRequestQueue.initRequestQueue(this);
        androidPreference = AndroidPreference.getInstance(this);
    }

}
