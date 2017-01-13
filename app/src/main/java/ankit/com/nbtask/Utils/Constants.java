package ankit.com.nbtask.Utils;

/**
 * Created by ankit on 12/01/17.
 */

public class Constants {

    public enum FurnishedType {
        FULLY_FURNISHED("Fully Furnished"),
        SEMI_FURNISHED("Semi Furnished"),
        NOT_FURNISHED("Not Furnished");
        public String value;

        FurnishedType(String value) {
            this.value = value;
        }
    }

    public enum IntentString {PROPERTIES_LIST}
}
