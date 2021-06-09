package it.ads.app.city_explorer_sdk.interfaces;

import java.util.HashMap;

/**
 * @author Robby Lebotha
 */
public interface MallInterface {
    /**
     * Get a particular mall in a city unsing full or partial string
     * @param mall The mall you are looking for
     * @param city The city you are looking from
     * @return a Hashset<Integer, String> of mallID and mallName
     */
    HashMap<Integer,String> getMall(String mall, String city);

    /**
     * Get a list of malls in a city
     * @param city String name of city
     * @param response Callback
     */
    void getMalls(String city, CityExploreCallBack response);
}
