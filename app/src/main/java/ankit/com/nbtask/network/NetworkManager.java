package ankit.com.nbtask.network;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ankit.com.nbtask.Utils.Utility;


/**
 * Created by ankit
 */
public enum NetworkManager {
    nManager;

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public boolean checkAndShowNetworkAlert(Context context) {
        boolean isConnected = NetworkManager.nManager.isNetworkAvailable(context);
        if (!isConnected) {
            Utility.showAlertDialog(context, "", "Internet Connection not found.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        return isConnected;
    }
}
