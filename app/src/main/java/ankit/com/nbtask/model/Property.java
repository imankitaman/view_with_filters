package ankit.com.nbtask.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ankit.com.nbtask.Utils.MyLog;

/**
 * Created by ankit
 */
public class Property implements Serializable {

    @SerializedName("id")
    private String id;
    @SerializedName("propertySize")
    private int propertySize;
    @SerializedName("photoAvailable")
    private boolean photoAvailable;
    @SerializedName("shortlistedByLoggedInUser")
    private boolean shortlistedByLoggedInUser;
    @SerializedName("photos")
    private
    List<Photo> photosList;
    @SerializedName("bathroom")
    private int bathroom;
    @SerializedName("type")
    private ApartmentType apartmentType;

    public enum ApartmentType {
        BHK3, BHK4, BHK2;

        /**
         * @param apartmentType check if presentnr in stream value
         * @param values
         * @return
         */
        public static boolean in(ApartmentType apartmentType, @NonNull String... values) {
            final long emptyValues = Stream.of(values).filter(TextUtils::isEmpty).count();
            if(emptyValues == values.length) return true; // none filters are selected, hence no filter to be applied
            if (apartmentType == null) {
                MyLog.w("ApartmentType", "Passed Type is null");
                return false;
            }
            final Optional<String> hasValue = Stream.of(values).filter(value -> apartmentType.name().equals(value)).findFirst();
            return hasValue.isPresent();
        }
    }

    @SerializedName("buildingType")
    private BuildingType buildingType;

    public enum BuildingType {
        AP, IH, IF;

        public static boolean in(BuildingType type, @NonNull String... values) {

            final long emptyValues = Stream.of(values).filter(TextUtils::isEmpty).count();
            if(emptyValues == values.length) return true; // none filters are selected, hence no filter to be applied
            if (type == null) {
                MyLog.w("BuildingType", "Passed Type is null");
                return false;
            }
            final Optional<String> hasValue = Stream.of(values).filter(value -> type.name().equals(value)).findFirst();
            return hasValue.isPresent();
        }
    }

    @SerializedName("furnishing")
    private Furnishing furnishing;

    public enum Furnishing {
        FULLY_FURNISHED, SEMI_FURNISHED;

        public static boolean in(Furnishing type, @NonNull String... values) {
            final long emptyValues = Stream.of(values).filter(TextUtils::isEmpty).count();
            if(emptyValues == values.length) return true; // none filters are selected, hence no filter to be applied
            if (type == null) {
                MyLog.w("Furnishing", "Passed Type is null");
                return false;
            }
            final Optional<String> hasValue = Stream.of(values).filter(value -> type.name().equals(value)).findFirst();
            return hasValue.isPresent();
        }
    }

    @SerializedName("rent")
    private int rent;
    @SerializedName("propertyTitle")
    private String propertyTitle;

    @SerializedName("secondaryTitle")
    private String subTitle;

    private String furnishingDesc;

    public String getId() {
        return id;
    }

    public int getPropertySize() {
        return propertySize;
    }

    public boolean isPhotoAvailable() {
        return photoAvailable;
    }

    public boolean isShortlistedByLoggedInUser() {
        return shortlistedByLoggedInUser;
    }

    public List<Photo> getPhotosList() {
        return photosList;
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Furnishing getFurnishing() {
        return furnishing;
    }

    public int getRent() {
        return rent;
    }

    public String getPropertyTitle() {
        return propertyTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public String getFurnishingDesc() {
        return furnishingDesc;
    }

    public int getBathroom() {
        return bathroom;
    }

    public void setShortlistedByLoggedInUser(boolean shortlistedByLoggedInUser) {
        this.shortlistedByLoggedInUser = shortlistedByLoggedInUser;
    }

//    @Override
//    public String toString() {
//        return "BuildingType- " + getBuildingType() + " ApartmentType" + getApartmentType() + " Furnished Type" + getFurnishing();
//    }
}
