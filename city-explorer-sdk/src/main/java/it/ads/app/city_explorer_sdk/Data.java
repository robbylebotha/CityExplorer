package it.ads.app.city_explorer_sdk;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import it.ads.app.city_explorer_sdk.interfaces.ApiInterface;
import it.ads.app.city_explorer_sdk.interfaces.ApiResponseCallback;
import it.ads.app.city_explorer_sdk.interfaces.DataInterface;
import it.ads.app.city_explorer_sdk.models.Cities;
import it.ads.app.city_explorer_sdk.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Data implements DataInterface {
    Context context;
    private Response<Cities> citiesData;
    String TAG = "city_explorer_sdk";

    public Data(Context context){
        this.context = context;
    }


    @Override
    public void getAllData(final ApiResponseCallback apiResponse) {
        Retrofit retrofit = ApiClient.getInstance();
        ApiInterface client = retrofit.create(ApiInterface.class);
        Call<Cities> call = client.fetchAllData(context.getString(R.string.api_key));
        call.enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                assert response.body() != null;
                Log.i(TAG, response.toString());
                if (response.body() != null) {
                    citiesData = response;
                    apiResponse.onSuccess(response);

                } else {
                    apiResponse.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {
                if (t instanceof IOException) {
                    apiResponse.onFail(t.toString());
                }
                apiResponse.onFail("failed");
            }
        });
    }

    @Override
    public void saveData(Response<Cities> data, String timpestamp) {

    }

    @Override
    public void getStoredData() {

    }
}
