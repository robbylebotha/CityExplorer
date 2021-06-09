package it.ads.app.cityexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import it.ads.app.city_explorer_sdk.CityExplore;
import it.ads.app.city_explorer_sdk.interfaces.CityExploreCallBack;
import it.ads.app.city_explorer_sdk.network.CheckNetwork;

public class MainActivity extends AppCompatActivity {
    CheckNetwork hasNetwork;
    String TAG = "city_explorer app";
    CityExplore cityExplore;
    Spinner spinnerCity, spinnerMall, spinnerShop;
    int spinnerMallSwitch, spinnerShopsSwitch, spinnerCitySwitch;
    LinearLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerCity = findViewById(R.id.mySpinner);
        spinnerMall =  findViewById(R.id.mySpinner2);
        spinnerShop =  findViewById(R.id.mySpinner3);
        progressBar = findViewById(R.id.llProgressBar);

        //this is to stop the spinners from automatically selecting a value
        //when its initailly created. Common issue with spinners, other solutions are
        //too complicated at this time.
        //can view frustrations here
        // https://stackoverflow.com/questions/45116269/how-to-prevent-the-spinner-from-selecting-the-first-item-when-user-does-not-sele
        //TODO reply to above question with my solution;
        spinnerMallSwitch = 1;
        spinnerShopsSwitch = 1;
        spinnerCitySwitch = 1;


        hasNetwork = new CheckNetwork(getApplicationContext());
        if(hasNetwork.isAvailable()){

            cityExplore = new CityExplore(getApplicationContext());
            //Get list of cities when app starts
            progressBar.setVisibility(View.VISIBLE);
           cityExplore.getCities(new CityExploreCallBack() {
               @Override
               public void onSuccess(ArrayList<String> list) {
                   Log.i(TAG, String.valueOf(list.size()));
                   createCitySpinner(list);
                   progressBar.setVisibility(View.INVISIBLE);
               }

               @Override
               public void onFail(String message) {
                   Log.i(TAG, "Failed to load cities: "+message);
                   Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                   progressBar.setVisibility(View.INVISIBLE);
               }
           });
        }else{
            Toast.makeText(getApplicationContext(),"No Network Available",Toast.LENGTH_SHORT).show();
        }

    }

    private void createCitySpinner(ArrayList<String> arrayList){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext()
                ,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(arrayAdapter);
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view
                    , int position, long id) {
                String cityName = parent.getItemAtPosition(position).toString();
                Log.i(TAG, "Selected: " + cityName);
                if(spinnerCitySwitch == 1){
                    spinnerCitySwitch = 0;
                    return;
                }
                //populate malls
                progressBar.setVisibility(View.VISIBLE);
                 cityExplore.getMalls(cityName, new CityExploreCallBack() {
                     @Override
                     public void onSuccess(ArrayList<String> list) {
                         spinnerMallSwitch = 1;
                         spinnerShopsSwitch = 1;
                         createMallSpinner(list);
                         progressBar.setVisibility(View.INVISIBLE);
                     }

                     @Override
                     public void onFail(String message) {
                         Log.i(TAG, "Failed to load malls: "+message);
                         Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                         progressBar.setVisibility(View.INVISIBLE);
                     }
                 });

                 //BONUS: create a list of all shops in the city.
                //should've probably used RxJava by now
                cityExplore.getShopsInCity(cityName, new CityExploreCallBack() {
                    @Override
                    public void onSuccess(ArrayList<String> list) {
                        createShopSpinner(list);
                    }

                    @Override
                    public void onFail(String message) {

                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

    private void createMallSpinner(ArrayList<String> arrayList){

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext()
                ,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMall.setAdapter(arrayAdapter);
        spinnerMall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view
                    , int position, long id) {
                if(spinnerMallSwitch == 1){
                    spinnerMallSwitch = 0;
                    return;
                }
                String mallName = parent.getItemAtPosition(position).toString();
                Log.i(TAG, "Selected: " + mallName);
                progressBar.setVisibility(View.VISIBLE);
                cityExplore.getShops(mallName, new CityExploreCallBack() {
                    @Override
                    public void onSuccess(ArrayList<String> list) {
                        progressBar.setVisibility(View.INVISIBLE);
                        createShopSpinner(list);
                    }

                    @Override
                    public void onFail(String message) {
                        Log.i(TAG, "Failed to load shops: "+message);
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

    }

    private void createShopSpinner(ArrayList<String> arrayList){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getApplicationContext()
                ,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShop.setAdapter(arrayAdapter);
        spinnerShop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view
                    , int position, long id) {
                if(spinnerShopsSwitch == 1){
                    spinnerShopsSwitch = 0;
                    return;
                }
                String shopName = parent.getItemAtPosition(position).toString();
                Log.i(TAG, "Selected: " + shopName);

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
    }

}