package ankit.com.nbtask.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ankit
 */
public class MyLog {

    private static boolean debugEnabled = true;

    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static void setDebugEnabled(boolean debugEnabled) {
        MyLog.debugEnabled = debugEnabled;
    }

    public static void i(String tag, String message)
    {
        if(debugEnabled)
            Log.i(tag, message);
    }

    public static void d(String tag, String message)
    {
        if(debugEnabled)
            Log.d(tag, message);
    }

    public static void w(String tag, String message)
    {
        if(debugEnabled)
            Log.w(tag, message);
    }

    public static void v(String tag, String message)
    {
        if(debugEnabled)
            Log.v(tag, message);
    }

    public static void e(String tag, String message)
    {
        if(debugEnabled)
            Log.e(tag, message);
    }

    public static void showToast(Context context, String message)
    {
        if(isDebugEnabled())
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void  showToastAlways(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void  showShortToastAlways(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
