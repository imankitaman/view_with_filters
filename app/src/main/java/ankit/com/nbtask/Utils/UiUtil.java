package ankit.com.nbtask.Utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.annimon.stream.function.BiConsumer;
import com.bumptech.glide.Glide;

import ankit.com.nbtask.BuildConfig;
import ankit.com.nbtask.R;

/**
 * Created by ankit
 */
public class UiUtil {

    public static void setRectangleImageUsingGlide(Context context, @NonNull ImageView imageView,@NonNull String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);
    }

    //Bi Consumer for setting checkBox State
    public static BiConsumer<CheckBox, String> setChecked = (checkBox, selectedStr) -> {
        if (!TextUtils.isEmpty(selectedStr)) checkBox.setChecked(true);
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Animator animateRevealColorFromCoordinates(Context context, View viewRoot, @ColorRes  int color) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, getDeviceMaxXCoordinates(context), getDeviceMaxYCoordinates(context), 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(context, color));
        anim.setDuration(context.getResources().getInteger(R.integer.anim_duration_medium));
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.start();
        return anim;
    }

  public static int getDeviceMaxXCoordinates(Context context){
        Display mdisp = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        return mdispSize.x;
    }

  public static int getDeviceMaxYCoordinates(Context context){
        Display mdisp = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        return mdispSize.y;
    }

}
