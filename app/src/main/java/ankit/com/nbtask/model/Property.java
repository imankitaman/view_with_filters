package ankit.com.nbtask.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ankit
 */
public class Property {

    @SerializedName("id")
    private String id;
    @SerializedName("propertySize")
    private int propertySize;
    @SerializedName("photoAvailable")
    private boolean photoAvailable;
    @SerializedName("shortlistedByLoggedInUser")
    private boolean shortlistedByLoggedInUser;
    @SerializedName("photos")
    List<Photo> photosList;

    private int bathroom;

    //TODO ENUM filter
//    String type = "BHK3";
//    String buildingType = "AP";
//    private String furnishing = "SEMI_FURNISHED";

    @SerializedName("type")
    private Type type;

    public enum Type {BHK3, BHK4, BHK2}

    @SerializedName("buildingType")
    private BuildingType buildingType;

    public enum BuildingType {AP, IH, IF}

    @SerializedName("furnishing")
    private Furnishing furnishing;

    public enum Furnishing {FULLY_FURNISHED, SEMI_FURNISHED}

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

    public Type getType() {
        return type;
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
}
