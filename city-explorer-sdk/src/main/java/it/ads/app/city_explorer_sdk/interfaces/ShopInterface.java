package it.ads.app.city_explorer_sdk.interfaces;

import java.util.Map;

/**
 * @author Robby Lebotha
 */
public interface ShopInterface {
    /**
     * Get a particular shop
     * @param mall String of full or partial shop name
     * @return Hash<Iteger id><String name>
     */
    Map<Integer, String> getShop(String mall , String shop);

    /**
     * Get a list of all shops in a given city
     * @param city <b>String</b> city name
     * @param response Callback
     *
     */
    void getShopsInCity(String city, CityExploreCallBack response);

    /**
     * Get a list of shops in a mall
     * @param mall String name of mall
     *  @param response Callback
     */
    void getShops(String mall, CityExploreCallBack response);
}
