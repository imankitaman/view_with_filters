package ankit.com.nbtask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ankit.com.nbtask.BuildConfig;
import ankit.com.nbtask.R;
import ankit.com.nbtask.Utils.Constants;
import ankit.com.nbtask.Utils.MyLog;
import ankit.com.nbtask.Utils.UiUtil;
import ankit.com.nbtask.model.Photo;
import ankit.com.nbtask.model.Property;
import ankit.com.nbtask.viewholder.PropertiesViewHolder;

/**
 * Created by ankit
 */
public class PropertiesAdapter extends RecyclerView.Adapter<PropertiesViewHolder> {
    private static final String TAG = PropertiesAdapter.class.getCanonicalName();
    private Context mContext;
    private List<Property> propertyData;

    public PropertiesAdapter() {
        propertyData = new ArrayList<>();
    }

    /**
     * @param list of properties
     *             it will check for list is empty first.
     *             {contains list of {@link Property} objects}
     */
    public void addProperties(List<Property> list) {
        if (propertyData.isEmpty()) {
            propertyData = list;
            notifyDataSetChanged();
        } else {
            propertyData.addAll(list);
            notifyItemRangeInserted(propertyData.size(), list.size());
        }
    }

    /**
     * clear all properties from exiting list
     */
    public void clearAllProperties() {
        propertyData.clear();
        notifyDataSetChanged();
    }

    @Override
    public PropertiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_properties_list, parent, false);
        mContext = parent.getContext();
        return new PropertiesViewHolder(view);
    }


    @Override
    public void onBindViewHolder(PropertiesViewHolder holder, int position) {
        final Property property = propertyData.get(position);
        holder.txtPropertyTitle.setText(property.getPropertyTitle());
        holder.txtSubtitle.setText(mContext.getString(R.string.properties_adapter_secondary_title, property.getSubTitle()));
        holder.txtPropertyRent.setText(mContext.getString(R.string.properties_adapter_rent, property.getRent()));
        holder.txtPropertySize.setText(mContext.getString(R.string.properties_adapter_property_size, property.getPropertySize()));

        int bathroomCount = property.getBathroom();// Count of bathrooms
        //TODO fetch displayPic
        List<Photo> photoList = property.getPhotosList();

        final Map<String, Photo> imageMap = Stream.of(photoList).filter(p -> p.isDisplayPic()).collect(Collectors.toMap(photo -> photo.getImagesMap().getMedium()));
        MyLog.e("key of map ", imageMap.keySet().toString());
        if (property.isPhotoAvailable()) {
            String imageUrl = String.format(BuildConfig.D3_URL, property.getId(), photoList.get(0).getImagesMap().getMedium());
            UiUtil.setRectangleImageUsingGlide(mContext, holder.imgProperty, imageUrl);
        }
        final String fTypeWithBathrooms;
        if (bathroomCount == 1) {
            fTypeWithBathrooms = mContext.getString(R.string.properties_adapter_bathroom, getFurnishingType(property), bathroomCount);//for 1 Bathroom
        } else
            fTypeWithBathrooms = mContext.getString(R.string.properties_adapter_bathrooms, getFurnishingType(property), bathroomCount);//for more than 1 Bathrooms
        holder.txtFTypeWithBathrooms.setText(fTypeWithBathrooms);
    }

    /**
     * @param property object with {@link Property.Furnishing}
     * @return String value of enum
     */
    private String getFurnishingType(Property property) {
        if (property.getFurnishing() == null) return Constants.FurnishedType.NOT_FURNISHED;
        switch (property.getFurnishing()) {
            case FULLY_FURNISHED:
                return Constants.FurnishedType.FULLY_FURNISHED;
            case SEMI_FURNISHED:
                return Constants.FurnishedType.SEMI_FURNISHED;
            default:
                return Constants.FurnishedType.NOT_FURNISHED;
        }
    }

    @Override
    public int getItemCount() {
        return propertyData == null ? 0 : propertyData.size();
    }

}
