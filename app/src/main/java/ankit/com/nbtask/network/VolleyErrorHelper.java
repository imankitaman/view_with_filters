package ankit.com.nbtask.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;
import ankit.com.nbtask.R;
import ankit.com.nbtask.model.Response;

/**
 * The Class VolleyErrorHelper will help to maintain all kind of connection error
 * and returns String formated proper message .
 *
 * @author ankit
 */
public class VolleyErrorHelper {

    /**
     * Returns appropriate message which is to be displayed to the user
     * against the specified error object.
     *
     * @param error   the error
     * @param context the context
     * @return the message
     */
    public static String getMessage(Object error, Context context) {

        if (error instanceof TimeoutError) {
            if (context != null)
                return context.getResources().getString(R.string.generic_server_down);
            else
                return "Could not connect. Please try later.";
        } else if (isServerProblem(error)) {
            if (context != null)
                return handleServerError(error, context);
            else
                return "Failed to connect to server.";
        } else if (isNetworkProblem(error)) {
            if (context != null)
                return context.getResources().getString(R.string.generic_error);
            else
                return "No internet connection.";
        }

        if (context != null)
            return context.getResources().getString(R.string.generic_error);
        else
            return "Failed to connect to server.";
    }

    /**
     * Determines whether the error is related to network.
     *
     * @param error the error
     * @return true, if is network problem
     */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }

    /**
     * Determines whether the error is related to server.
     *
     * @param error the error
     * @return true, if is server problem
     */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }

    /**
     * Handles the server error, tries to determine whether to show a stock message or to
     * show a message retrieved from the server.
     *
     * @param err     the err
     * @param context the context
     * @return the string
     */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;

        NetworkResponse response = error.networkResponse;

        if (response != null) {
            switch (response.statusCode) {
                case 404:
                case 422:
                case 401:
                case 400:
                    try {

                        Response result = new Gson().fromJson(new String(response.data),
                                new TypeToken<Response>() {
                                }.getType());
                        if (result != null && result.getMessage() != null) {
                            return result.getMessage();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // invalid request
                    return error.getMessage();

                default:
                    if (context != null)
                        return context.getResources().getString(R.string.generic_server_down);
                    else
                        return "Could not connect. Please try later.";
            }
        }
        if (context != null)
            return context.getResources().getString(R.string.generic_error);
        else
            return "Failed to connect to server.";
    }

}
