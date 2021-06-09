package it.ads.app.city_explorer_sdk.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import it.ads.app.city_explorer_sdk.interfaces.NetworkInterface;

/**
 * Class to deal with checking network condition
 * @author Robby Lebotha
 */
public final class CheckNetwork implements NetworkInterface {
    Context context;
    private String TAG = "city_explorer_sdk";


    public CheckNetwork(Context context){
        this.context = context;
    }

    @Override
    public final Boolean isAvailable() {

        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean available = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        if(!available){
            networkError("Network Status: " + false);
        }else {
            available = true;
        }

        Log.i(TAG, "Network Status: " + available);
        return available;
    }

    @Override
    public final String networkType() {
        return null;
    }

    @Override
    public void networkError(String error) {
        Log.i(TAG, error);
    }
}
