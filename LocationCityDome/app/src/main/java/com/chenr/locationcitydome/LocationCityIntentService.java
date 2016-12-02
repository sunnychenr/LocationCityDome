package com.chenr.locationcitydome;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by ChenR on 2016/11/29.
 */

public class LocationCityIntentService extends IntentService {

    public LocationCityIntentService() {
        super("LocationCityIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LatLng ll = (LatLng) intent.getSerializableExtra("location");
        Geocoder mGeocoder = new Geocoder(this);
        List<Address> addressList = null;
        Log.d("ChenR", ll.toString());
        try {
            addressList = mGeocoder.getFromLocation(ll.getLat(), ll.getLng(), 1);
            Log.d("ChenR", addressList.toString());
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                Log.d("ChenR", "国家代码 ----> " + address.getCountryCode());
                Log.d("ChenR", "AdminArea : " + address.getAdminArea());
                String locality = address.getLocality();
                Intent result = new Intent("result");
                result.putExtra("cityName", locality);
                sendBroadcast(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
