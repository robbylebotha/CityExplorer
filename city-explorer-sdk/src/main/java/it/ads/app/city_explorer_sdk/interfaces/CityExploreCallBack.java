package it.ads.app.city_explorer_sdk.interfaces;

import java.util.ArrayList;

/**
 * @author Robby Lebotha
 * Callback
 */
public interface CityExploreCallBack {

    /**
     * When result returns succesfully
     * @param list String list of results
     */
    void onSuccess(ArrayList<String> list);

    /**
     * When result fails
     * @param message string message
     */
    void onFail(String message);
}
