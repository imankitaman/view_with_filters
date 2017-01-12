package ankit.com.nbtask.network;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import ankit.com.nbtask.Utils.MyLog;
import ankit.com.nbtask.Utils.Utility;


/**
 * ServerResponseHandler handles the response for the API request that is made by the system.
 *
 * @author Ankit Aman
 */
public abstract class ServerResponseHandler implements Response.Listener<JSONObject>, Response.ErrorListener {

    private final String TAG = "ServerNotifier";
    private Context context;

    /**
     * Pass ApplicationContext for AlertDialog
     *
     * @param context
     */
    public ServerResponseHandler(Context context) {
        super();
        this.context = context;
    }

    public static int count = 0;
    public Request currentRequest;

    public void setCurrentRequest(Request currentRequest) {
        this.currentRequest = currentRequest;
    }

    public Request getCurrentRequest() {
        return currentRequest;
    }

    public abstract void onSuccess(JSONObject response);

    public void handleError(JSONObject response) {
    }


    @Override
    public void onResponse(JSONObject response) {
        MyLog.d(TAG, response.toString());
        try {
            int statusCode = response.getInt("statusCode");
            int status = response.getInt("status");
            if (statusCode == 200 || status == 200) {
                MyLog.i(TAG, response.toString());
                count = 0;
                onSuccess(response);

                Utility.hideProgressDialog();
            }
        } catch (JSONException e) {
            //TODO add server event
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        NetworkResponse response = volleyError.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 401:
                    if (context != null) {
                        Intent intent = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        if (context instanceof Activity) {
                            ((Activity) context).finish();
                        }
                    }
                    break;
            }
        }

        MyLog.d(TAG, "Error: " + VolleyErrorHelper.getMessage(volleyError, context));

        Utility.hideProgressDialog();

        if (context != null && context instanceof Activity) {
            MyLog.showToast(context, "Error: " + VolleyErrorHelper.getMessage(volleyError, context));
            // hide the progress dialog


            AlertDialog alertDialog = Utility.showAlertDialog(context, "", VolleyErrorHelper.getMessage(volleyError, context), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // System.exit(0);
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            count++;

                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            });
            if (alertDialog != null) {
                if (count < 4) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText("Retry");
                } else {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText("Exit");
                }
            }
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
