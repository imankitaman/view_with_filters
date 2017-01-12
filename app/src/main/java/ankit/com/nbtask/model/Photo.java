package ankit.com.nbtask.model;

/**
 * Created by ankit
 */
public class Photo {

    private String title;
    private boolean displayPic;
    private imagesMap imagesMap;

    public String getTitle() {
        return title;
    }

    public boolean isDisplayPic() {
        return displayPic;
    }

    public Photo.imagesMap getImagesMap() {
        return imagesMap;
    }

    public class imagesMap{
        String thumbnail;
        String original;
        String large;
        String medium;

        public String getThumbnail() {
            return thumbnail;
        }

        public String getOriginal() {
            return original;
        }

        public String getLarge() {
            return large;
        }

        public String getMedium() {
            return medium;
        }
    }
}
