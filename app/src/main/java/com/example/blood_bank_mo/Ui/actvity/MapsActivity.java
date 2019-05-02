package com.example.blood_bank_mo.Ui.actvity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.blood_bank_mo.Gpstracker;
import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.Ui.fragment.RequestforDonation_Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.blood_bank_mo.Ui.fragment.RequestforDonation_Fragment.hospital_address;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    @BindView(R.id.My_Location)
    Button MyLocation;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Gpstracker gpstracker = new Gpstracker(MapsActivity.this, MapsActivity.this);

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(gpstracker.getLongitude(), gpstracker.getLatitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        setMyLocation(gpstracker.getLongitude(), gpstracker.getLatitude()
                , gpstracker.getGeocoderAddress(MapsActivity.this).get(0).getAddressLine(0));

        MyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gpstracker gpstracker = new Gpstracker(MapsActivity.this, MapsActivity.this);

                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(gpstracker.getLongitude(), gpstracker.getLatitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                setMyLocation(gpstracker.getLongitude(), gpstracker.getLatitude()
                        , gpstracker.getGeocoderAddress(MapsActivity.this).get(0).getAddressLine(0));
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                try {
                    // Creating a marker
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting the position for the marker
                    markerOptions.position(latLng);

                    // Setting the title for the marker.
                    // This will be displayed on taping the marker
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.ENGLISH);


                    /**
                     * Geocoder.getFromLocation - Returns an array of Addresses
                     * that are known to describe the area immediately surrounding the given latitude and longitude.
                     */
                    List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    Address address = addresses.get(0);
                    hospital_address = address.getAddressLine(0);

                    setMyLocation(latLng.longitude, latLng.latitude, addresses.get(0).getAddressLine(0));
                    // Clears the previously touched position
                    mMap.clear();

                    // Animating to the touched position
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    // Placing a marker on the touched position
                    mMap.addMarker(markerOptions);
                    mMap.animateCamera(CameraUpdateFactory.zoomIn());
                    // Zoom out to zoom level 10, animating with a duration of 2 seconds.
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                } catch (Exception e) {

                }
            }
        });
    }

    private void setMyLocation(double longitude2, double latitude2, String Address) {
        RequestforDonation_Fragment.longitude = longitude2;
        RequestforDonation_Fragment.latitude = latitude2;
        hospital_address = Address;

        Toast.makeText(this, String.valueOf(RequestforDonation_Fragment.longitude), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(RequestforDonation_Fragment.latitude), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, hospital_address, Toast.LENGTH_SHORT).show();
    }
}
