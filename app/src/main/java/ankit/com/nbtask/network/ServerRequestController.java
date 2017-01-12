package ankit.com.nbtask.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ankit.com.nbtask.Utils.Utility;

/**
 * ServerRequestController is a place where all the API requests are made to the Server.
 *
 * @author Ankit Aman
 */
public final class ServerRequestController {

    public final static int REQUEST_TIMEOUT_LIMIT = 3000; // Maximum time limit for request to wait for the response in seconds
    public final static int REQUEST_RETRY_LIMIT = 1; // Maximum retries the system has to request for an API in-case of failure

    /**
     * Makes a server api request with http-method : GET
     * @param url Server API url
     * @param responseHandler A callback which handles both Success and Failure of the API request
     * @param tag  identifier for the request queue maintained by Volley
     */
    public static void get(String url, ServerResponseHandler responseHandler, String tag, boolean... showDialog) {
        boolean bshowDialog = true;
        if (showDialog.length > 0)
            bshowDialog = showDialog[0];

        Context currentActivity = responseHandler.getContext();
        if (!NetworkManager.nManager.checkAndShowNetworkAlert(currentActivity)) {
            return;
        }

        if (bshowDialog)
            Utility.showProgressDialog(currentActivity, "", "", false);

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, responseHandler, responseHandler) {
            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                REQUEST_TIMEOUT_LIMIT,
                REQUEST_RETRY_LIMIT,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue

        responseHandler.setCurrentRequest(jsonObjReq);

        VolleyRequestQueue.instance().addToRequestQueue(jsonObjReq, tag);
    }

    /**
     * Makes a server api request with http-method : DELETE
     * @param url Server API url
     * @param responseHandler A callback which handles both Success and Failure of the API request
     * @param tag  identifier for the request queue maintained by Volley
     */
    public static void post(@NonNull String url, @NonNull JSONObject params, ServerResponseHandler responseHandler, @NonNull String tag, boolean... showDialog)  {
        Context currentActivity = responseHandler.getContext();
        if(!NetworkManager.nManager.checkAndShowNetworkAlert(currentActivity)){
            return ;
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST ,url, params, responseHandler, responseHandler) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
//                headers.put("token", TownCakesApplication.androidPreference.getValue(PreferenceConfig.USER_TOKEN,""));
                return headers;
            }
        };
        responseHandler.setCurrentRequest(jsonObjReq);
        VolleyRequestQueue.instance().addToRequestQueue(jsonObjReq, tag);
    }


    /**
     * Cancel the API request(if already made) through the Request ID tag
     * @param tag identifier for the request queue maintained by Volley
     */
    public static void cancelApiCallsByTag(String tag) {
        VolleyRequestQueue.instance().cancelPendingRequests(tag);
    }

}