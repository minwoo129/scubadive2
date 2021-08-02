package com.example.scubadive2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class MapActivity extends AppCompatActivity {
    GoogleMap map;
    TextView latitudeTv, longitudeTv;
    double lati1, longi1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        latitudeTv = findViewById(R.id.latitudeTv);
        longitudeTv = findViewById(R.id.longitudeTv);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.mapfrag);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        double lati = Math.abs(latLng.latitude);
                        double longi = Math.abs(latLng.longitude);
                        lati1 = latLng.latitude;
                        longi1 = latLng.longitude;
                        MarkerOptions mo = new MarkerOptions();
                        mo.position(latLng);
                        mo.title("선택위치");
                        map.addMarker(mo);
                        Log.d("kkk", lati1 + " : " + longi1);

                        String latitude = "", longitude = "";
                        latitude += (String.valueOf((int)lati)+"°");
                        lati = (lati-(int)lati)*60;
                        latitude += (String.valueOf((int)lati)+"\'");
                        lati = (lati-(int)lati)*60;
                        latitude += (String.valueOf(lati)+"\"");
                        if(lati1 > 0) latitude += "N";
                        else latitude += "S";

                        longitude += (String.valueOf((int)longi)+"°");
                        longi = (longi - (int)longi)*60;
                        longitude += (String.valueOf((int)longi)+"\'");
                        longi = (longi - (int)longi)*60;
                        longitude += (String.valueOf(longi)+"\"");
                        if(longi1 > 0) longitude += "E";
                        else longitude += "W";

                        latitudeTv.setText(latitude);
                        longitudeTv.setText(longitude);
                    }
                });
            }
        });

        findViewById(R.id.returnBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lat", lati1);
                intent.putExtra("long", longi1);
                setResult(200, intent);
                initDB();
                saveDB(lati1, longi1);
                finish();
            }
        });
    }

    private void initDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("location", Context.MODE_PRIVATE, null);
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS location("
                +"lattitude DOUBLE,"
                +"longitude DOUBLE)");

        db.close();
    }

    private void saveDB(double lattitude, double longitude) {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("location", Context.MODE_PRIVATE, null);
        }
        db.execSQL("INSERT INTO location (lattitude, longitude) VALUES ("+lattitude+", "+longitude+")");
        db.close();
    }
}
