package it.ads.app.city_explorer_sdk.interfaces;

import it.ads.app.city_explorer_sdk.models.Cities;
import retrofit2.Response;

/**
 * Interface to deal with all data transactions
 * @author Robby Lebotha
 */
public interface DataInterface {

    /**
     * Call API and fetch all json data
     */
    void getAllData(ApiResponseCallback apiResponse);

    /**
     * Save data for offline storage
     * @param data response from API call
     */
    void saveData(Response<Cities> data, String timpestamp);

    /**
     * Retrieve stored data in offline mode
     */
    void getStoredData();
}
