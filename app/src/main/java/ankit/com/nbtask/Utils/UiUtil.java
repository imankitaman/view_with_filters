package ankit.com.nbtask.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import ankit.com.nbtask.BuildConfig;
import ankit.com.nbtask.R;

/**
 * Created by ankit
 */

public class UiUtil {

    public static void setRectangleImageUsingGlide(Context context, @NonNull ImageView imageView, @NonNull String imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);
    }

}
