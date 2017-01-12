package ankit.com.nbtask.network;

/**
 * ServerAPIRoutes manages all API routes required by the application in one place.
 *
 * @author Ankit Aman
 */
public class ServerAPIRoutes {

    public static final String GET_PROPERTIES_LIST = "/api/v1/property/filter/region/ChIJLfyY2E4UrjsRVq4AjI7zgRY/?lat_lng=12.9279232,77.6271078&rent=0,500000&travelTime=30&pageNo=%s";

    /**
     * @param url  pass api
     * @param args to append with url
     * @return finalUrl
     */
    public static String getApiUrl(String url, Object... args) {

        String baseUrl = "http://www.nobroker.in";
        if (args.length > 0)
            url = String.format(url, args);

        return baseUrl + url;
    }
}
