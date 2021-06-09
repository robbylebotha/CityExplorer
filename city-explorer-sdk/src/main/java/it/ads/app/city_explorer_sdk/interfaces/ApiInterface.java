package it.ads.app.city_explorer_sdk.interfaces;

import it.ads.app.city_explorer_sdk.models.Cities;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 *
 */
public interface ApiInterface {

    //http://www.mocky.io/v2/5b7e8bc03000005c0084c210
    @GET
    Call<Cities> fetchAllData(@Url String url);
}
