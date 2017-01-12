package ankit.com.nbtask.model;

/**
 * Created by ankit
 */
public class Response {

    private int status;
    private int statusCode;
    private String message;
    private String[] data;
    private otherParams orOtherParams;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public otherParams getOrOtherParams() {
        return orOtherParams;
    }

    public void setOrOtherParams(otherParams orOtherParams) {
        this.orOtherParams = orOtherParams;
    }

    class otherParams {
        int count;
        String region_id;
        int total_count;
        String city;
        String searchToken;
        String topPropertyId;
    }

}
