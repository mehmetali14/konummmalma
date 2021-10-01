package com.info.konummalma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private TextView textViewenlem,textViewboylam;
    private Button button;
    private  int izinkontrol;
    private LocationManager locationManager;

    private  String konumsaglayici="gps";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewboylam=findViewById(R.id.textViewboylam);
        textViewenlem=findViewById(R.id.textViewenlem);
        button=findViewById(R.id.button);
        locationManager= (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                izinkontrol= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (izinkontrol != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);

                }
                else {
                   Location location=locationManager.getLastKnownLocation(konumsaglayici);
                    if (location!=null){
                        onLocationChanged(location);
                    }
                    else {
                        textViewboylam.setText("KONUM YOK");
                        textViewenlem.setText("KONUM YOK");
                    }
                }
            }
        });

    }


    @Override
    public void onLocationChanged(Location location) {
        double enlem=location.getLatitude();
        double boylam=location.getLongitude();

        textViewenlem.setText("ENLEM:"+enlem);
        textViewboylam.setText("BOYLAM:"+boylam);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            izinkontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (grantResults.length>0 &&grantResults[0] ==PackageManager.PERMISSION_GRANTED ){
                Toast.makeText(MainActivity.this,"İZİN VERİLDİ",Toast.LENGTH_SHORT);
                Location location=locationManager.getLastKnownLocation(konumsaglayici);
                if (location!=null){
                    onLocationChanged(location);
                }
                else {
                    textViewboylam.setText("KONUM YOK");
                    textViewenlem.setText("KONUM YOK");
                }
            }


        }
    }
}