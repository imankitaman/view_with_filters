package ankit.com.nbtask.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ankit
 */
public class AndroidPreference  {

    private Context context;
    public static final String PREFS_NAME = "AndroidPreferences";

    public static AndroidPreference androidPreference;


    private AndroidPreference(Context context) {
        this.context = context;
    }
    public static synchronized AndroidPreference getInstance(Context context){
        if(androidPreference ==null){
            androidPreference = new AndroidPreference(context);
        }
        return androidPreference;
    }

    public void put(String key, String value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    public boolean getBoolean(String key, boolean defValue) {
        try {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
            boolean prefVal = sharedPreferences.getBoolean(key, defValue);
            return prefVal;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return defValue;
    }

    public void putInt(String key, int value) {
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void addIfAlreadyExists(String key, String value, String delimiter) {
        String currentValue = getValue(key, null);
        if(currentValue == null){
            //add new entry
            put(key, value);
        }else{
            //append with old value
            put(key, currentValue+delimiter+value);
        }
    }

    public String getValue(String key, String defValue) {
        try {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
            String prefVal = sharedPreferences.getString(key, defValue);
            return prefVal;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return defValue;
    }


    public int getIntValue(String key, int defValue) {
        try {
            SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
            int prefVal = sharedPreferences.getInt(key, defValue);
            return prefVal;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return defValue;
    }

    public boolean clearAllPref(){
        SharedPreferences sharedPreferences = this.context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }

}
