package ankit.com.nbtask.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import java.util.Map;

import ankit.com.nbtask.R;

/**
 *  @author Ankit Aman
 */
public class Utility {
    private static final String TAG = Utility.class.getSimpleName();

    private static Map<String, String> map;


    /**
     * The progress dialog.
     */
    public static ProgressDialog progressDialog;



    public static AlertDialog showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        if (context != null && context instanceof Activity && !((Activity) context).isFinishing()) {
            AlertDialog alertDialog;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert).create();
            } else {
                alertDialog = new AlertDialog.Builder(context).create();
            }

            title = TextUtils.isEmpty(title) ? "" : title;

            // Setting Dialog Title
            alertDialog.setTitle(title);

            // Setting Dialog Message
            alertDialog.setMessage(message);

            // Setting alert dialog icon
            // alertDialog.setIcon((status) ? R.drawable.success :
            // R.drawable.fail);

            // Setting Cancel Button
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", listener);

            // Setting OKAY Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", listener);

            // Showing Alert Message
            alertDialog.show();

            return alertDialog;
        }
        return null;
    }


    /**
     * Function to display simple Progress Dialog.
     *
     * @param context      - activity context
     * @param title        - progress dialog title
     * @param message      - progress message
     * @param isCancelable the is cancelable
     * @author ankitaman
     */
    public static void showProgressDialog(Context context, String title, String message, boolean... isCancelable) {
        hideProgressDialog();
        if (context != null && context instanceof Activity) {
            title = TextUtils.isEmpty(title) ? context.getResources().getString(R.string.dialog_title) : title;
            message = TextUtils.isEmpty(message) ? "Loading. Please wait.." : message;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                progressDialog = new ProgressDialog(context);
            } else {
                progressDialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
            }
            progressDialog.setTitle(title);
            progressDialog.setMessage(message);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            if (isCancelable.length > 0) {
                progressDialog.setCancelable(isCancelable[0]);
            }

            progressDialog.show();
        }
    }

    /**
     * Function to hide progress Dialog.
     *
     * @author ankitaman
     */
    public static void hideProgressDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                }
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
