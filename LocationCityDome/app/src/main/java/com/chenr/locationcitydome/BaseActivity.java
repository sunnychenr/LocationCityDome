package com.chenr.locationcitydome;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseActivity extends AppCompatActivity {

    private EditText edt_1;
    private EditText edt_2;
    private TextView tv_city;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initView();

        registerReceiver();


    }

    private void initView() {
        edt_1 = (EditText) findViewById(R.id.edt_1);
        edt_2 = (EditText) findViewById(R.id.edt_2);
        tv_city = (TextView) findViewById(R.id.tv_city);
        btn = (Button) findViewById(R.id.btn);
    }

    public void myClick(View view) {
        String str_lat = edt_1.getText().toString();
        String str_lng = edt_2.getText().toString();

        if ("".equals(str_lat)) {
            edt_1.setError("请输入纬度");
            return;
        }

        if ("".equals(str_lng)) {
            edt_2.setError("请输入经度");
            return;
        }

        double lat = Double.parseDouble(str_lat);
        double lng = Double.parseDouble(str_lng);

        Intent intent = new Intent(BaseActivity.this, LocationCityIntentService.class);
        intent.putExtra("location", new LatLng(lat, lng));
        startService(intent);
    }

    private void registerReceiver() {
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("result");
        registerReceiver(mBroadcastReceiver, mIntentFilter);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("result".equals(intent.getAction())) {
                tv_city.setText(intent.getStringExtra("cityName"));
            }
        }
    };

}
