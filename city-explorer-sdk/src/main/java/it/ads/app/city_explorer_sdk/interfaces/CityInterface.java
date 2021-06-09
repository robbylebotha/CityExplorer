package it.ads.app.city_explorer_sdk.interfaces;

import java.util.Map;

/**
 * @author Robby Lebotha
 */
public interface CityInterface {

    /**
     * Get a list of cities using partial of full string
     * @param response callback
     */
    void getCities(CityExploreCallBack response);

    /**
     * Get name and id of a particular city
     * @param city String city name, can be partial
     * @return HashSet<K><V> of results
     */
    Map<Integer, String> getCity(String city);
}
