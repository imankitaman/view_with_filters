package ankit.com.nbtask.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ankit.com.nbtask.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ankit
 */

public class PropertiesViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.txtTitle)
    public TextView txtPropertyTitle;
    @Bind(R.id.txtSubtitle)
    public TextView txtSubtitle;
    @Bind(R.id.imgFav)
    public ImageView imgFav;
    @Bind(R.id.imgCall)
    public ImageView imgCall;
    @Bind(R.id.imgProperty)
    public ImageView imgProperty;
    @Bind(R.id.txtPropertyRent)
    public TextView txtPropertyRent;
    @Bind(R.id.txtFTypeWithBathrooms)
    public TextView txtFTypeWithBathrooms;
    @Bind(R.id.txtPropertySize)
    public TextView txtPropertySize;

    public PropertiesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
