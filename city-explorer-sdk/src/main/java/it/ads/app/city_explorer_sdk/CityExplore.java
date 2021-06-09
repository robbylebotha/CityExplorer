package it.ads.app.city_explorer_sdk;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.ads.app.city_explorer_sdk.interfaces.ApiResponseCallback;
import it.ads.app.city_explorer_sdk.interfaces.CityExploreCallBack;
import it.ads.app.city_explorer_sdk.interfaces.CityInterface;
import it.ads.app.city_explorer_sdk.interfaces.ErrorHandling;
import it.ads.app.city_explorer_sdk.interfaces.MallInterface;
import it.ads.app.city_explorer_sdk.interfaces.ShopInterface;
import it.ads.app.city_explorer_sdk.models.Cities;
import it.ads.app.city_explorer_sdk.models.City;
import it.ads.app.city_explorer_sdk.models.Mall;
import it.ads.app.city_explorer_sdk.models.Shop;
import retrofit2.Response;

final public class CityExplore implements ErrorHandling, MallInterface, ShopInterface,
        CityInterface {

    String TAG = "city_explorer_sdk";
    private ArrayList cities;
    private ArrayList<String> malls;
    private ArrayList<String> shops;
    private ArrayList<String> shopsInCity;
    private Map<Integer, String> searchResult;
    Context context;
    Data data;


    public CityExplore(Context context) {
        this.context = context;
        data = new Data(context);
    }

    @Override
    public final void getCities(final CityExploreCallBack response) {
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                cities = new ArrayList<String>();
                for (City city : cityData.body().getCities()) {
                    Log.i(TAG, "got "+city.getName());
                    cities.add(city.getName());
                }
                if(response != null){
                    response.onSuccess(cities);
                }
            }

            @Override
            public void onFail(String message) {
                onError("Failed to get cities: " + message);
                response.onFail("Failed to get cities: " + message);
            }
        });
    }

    @Override
    public final Map<Integer, String> getCity(final String city) {
        searchResult = new HashMap<Integer, String>();
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                for (City citys : cityData.body().getCities()) {
                    if (citys.getName().contains(city)) {
                        Log.i(TAG, "found city: " + citys.getName());
                        searchResult.put(citys.getId(), citys.getName());
                    }
                }
                if(searchResult == null || searchResult.isEmpty()){
                    onError(city+" not found in list");
                }
            }

            @Override
            public void onFail(String message) {
                onError(city + " not found. "+message);
            }
        });

        return searchResult;
    }

    @Override
    public final HashMap<Integer, String> getMall(final String mall, final String city) {
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                searchResult = new HashMap<Integer, String>();
                for (City citys : cityData.body().getCities()) {
                    if (citys.getName().contains(city)) {
                        for (Mall malls : citys.getMalls()) {
                            if (malls.getName().contains(mall)) {
                                Log.i(TAG, malls.getName());
                                searchResult.put(malls.getId(), malls.getName());
                            }
                        }
                    }
                }
                if (searchResult.isEmpty()) {
                    onError("No malls or city not in list :"+mall+"\t"+city);
                }
            }

            @Override
            public void onFail(String message) {
                onError("No malls or city not in list. "+message);
            }
        });


        return (HashMap<Integer, String>) searchResult;
    }

    @Override
    public final Map<Integer, String> getShop(final String mall, final String shop) {
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                searchResult = new HashMap<Integer, String>();
                for (City city : cityData.body().getCities()) {
                    for (Mall malls : city.getMalls()) {
                        if (malls.getName().contains(mall)) { //search mall
                            for (Shop shopss : malls.getShops()) {
                                if (shopss.getName().contains(shop)) { //search shops
                                    Log.i(TAG, shopss.getId() + shopss.getName());
                                    searchResult.put(shopss.getId(), shopss.getName());
                                }
                            }
                        }
                    }
                }
                if (searchResult.isEmpty()) {
                    onError("No malls or city not in list : "+mall+"\t"+shop);
                }
            }

            @Override
            public void onFail(String message) {
                onError("No malls or city not in list. "+message);
            }
        });

        return searchResult;
    }

    @Override
    public final void getShopsInCity(final String city, final CityExploreCallBack response) {
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                shopsInCity = new ArrayList();
                for (City citys : cityData.body().getCities()) {
                    if (citys.getName().contains(city)) {
                        for (Mall mall : citys.getMalls()) {
                            for (Shop shops : mall.getShops()) {
                                Log.i(TAG, shops.getName());
                                shopsInCity.add(shops.getName());
                            }
                        }
                    }
                }
                if (shopsInCity.isEmpty()) {
                    onError("No shops or city not in list: "+city);
                    response.onFail("No shops or city not in list: "+city);
                }else{
                    response.onSuccess(shopsInCity);
                }
            }

            @Override
            public void onFail(String message) {
                onError("No shops or city not in list. "+message);
                response.onFail(message);
            }
        });
    }

    @Override
    public final void getMalls(final String city, final CityExploreCallBack response) {
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                malls = new ArrayList();
                for (City citys : cityData.body().getCities()) {
                    if (citys.getName().equalsIgnoreCase(city)) {
                        for (Mall mall : citys.getMalls()) {
                            Log.i(TAG, mall.getName());
                            malls.add(mall.getName());
                        }
                    }
                }
                if(malls.isEmpty()){
                    onError("malls not found in "+city);
                    response.onFail("malls not found in "+city);
                }else{
                    response.onSuccess(malls);
                }
            }

            @Override
            public void onFail(String message) {
                onError("No malls or city not in list. "+message);
                response.onFail("malls not found in "+city);
            }
        });
    }

    @Override
    public final void getShops(final String mall, final CityExploreCallBack response) {
        data.getAllData(new ApiResponseCallback() {
            @Override
            public void onSuccess(Response<Cities> cityData) {
                shops = new ArrayList();
                for (City city : cityData.body().getCities()) {
                    for (Mall malls : city.getMalls()) {
                        if (malls.getName().equalsIgnoreCase(mall)) {
                            for (Shop shop : malls.getShops()) {
                                Log.i(TAG, shop.getName());
                                shops.add(shop.getName());
                            }
                        }
                    }
                }
                if (shops == null || shops.size() == 0) {
                    onError("No shops found in "+mall);
                    response.onFail("No shops found in "+mall);
                }else{
                    response.onSuccess(shops);
                }
            }

            @Override
            public void onFail(String message) {
                onError("No shops or mall not in list. "+message);
                response.onFail("No shops found in "+mall);
            }
        });
    }

    @Override
    public void onError(String error) {
        Log.i(TAG, error);
    }
}
