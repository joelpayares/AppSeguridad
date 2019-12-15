package com.appseguridad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Home extends AppCompatActivity {

    TextView coreUsu;
    TextView nomUsu;
    TextView celUsu;
    TextView latitud;
    TextView longitud;

    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;

    UsuariosSQLiteHelper usdbh;
    SQLiteDatabase db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        coreUsu = findViewById(R.id.txtCorreo);
        nomUsu = findViewById(R.id.txtNombres);
        celUsu = findViewById(R.id.txtCelular);
        latitud = findViewById(R.id.latitudeValueGPS);
        longitud = findViewById(R.id.longitudeValueGPS);

        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        db = usdbh.getReadableDatabase();

        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Home.this.methodToRun();
            }
        });*/

        consultar();
    }

    private void consultar() {
        Cursor c = db.rawQuery("SELECT * FROM Usuarios WHERE codigo = '" + Objects.requireNonNull(mAuth.getCurrentUser()).getUid() + "' ",null);

            if (c.moveToFirst()) {

                do {
                    nomUsu.setText(c.getString(1));
                    coreUsu.setText(c.getString(3));
                    celUsu.setText(c.getString(2));
                } while (c.moveToNext());
            }

        db.close();
        c.close();
    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    public void toggleGPSUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText(R.string.resume);
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

            }
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 20 * 1000, 10, locationListenerGPS);
            Toast.makeText(this, "GPS provider started running", Toast.LENGTH_LONG).show();
            button.setText(R.string.pause);
        }
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitud.setText((int) longitudeGPS);
                    latitud.setText((int) latitudeGPS);
                    Toast.makeText(Home.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };
}