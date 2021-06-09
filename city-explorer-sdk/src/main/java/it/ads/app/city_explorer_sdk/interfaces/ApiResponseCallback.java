package it.ads.app.city_explorer_sdk.interfaces;

import it.ads.app.city_explorer_sdk.models.Cities;
import retrofit2.Response;

/**
 * Use this interface as a callback when calling the getAllData() method
 * so that your method will only start parsing data when the retrofit API call
 * has responded. Otherwise you''s be trying to process data which is currently
 * null as the request may take time.
 * @author Robby Lebotha
 */
public interface ApiResponseCallback {

    /**
     * Call this method when retrofit returns successful response
     * @param cityData City Data model
     */
    void onSuccess(Response<Cities> cityData);

    /**
     * Call this method when retrofit returns a failed response
     * @param message String error message
     */
    void onFail(String message);
}
