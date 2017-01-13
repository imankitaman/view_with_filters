package ankit.com.nbtask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
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
        List<Photo> photoList = property.getPhotosList();

        final Optional<String> imageUrlOpt = Stream.of(photoList).filter(Photo::isDisplayPic).map(Photo::getImagesMap).map(Photo.ImagesMap::getMedium).findFirst();
        imageUrlOpt.ifPresent( url -> UiUtil.setRectangleImageUsingGlide(mContext, holder.imgProperty, url));

        final int bathroomType = bathroomCount == 1 ? R.string.properties_adapter_bathroom : R.string.properties_adapter_bathrooms;
        final String fTypeWithBathrooms= mContext.getString(bathroomType, getFurnishingType(property), bathroomCount);//for 1 Bathroom
        holder.txtFTypeWithBathrooms.setText(fTypeWithBathrooms);
    }

    /**
     * @param property object with {@link Property.Furnishing}
     * @return String value of enum
     */
    private String getFurnishingType(Property property) {
        if (property.getFurnishing() == null) return Constants.FurnishedType.NOT_FURNISHED.value;
        switch (property.getFurnishing()) {
            case FULLY_FURNISHED:
                return Constants.FurnishedType.FULLY_FURNISHED.value;
            case SEMI_FURNISHED:
                return Constants.FurnishedType.SEMI_FURNISHED.value;
            default:
                return Constants.FurnishedType.NOT_FURNISHED.value;
        }
    }

    @Override
    public int getItemCount() {
        return propertyData == null ? 0 : propertyData.size();
    }

}
