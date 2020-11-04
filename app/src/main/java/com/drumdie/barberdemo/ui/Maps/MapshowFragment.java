package com.drumdie.barberdemo.ui.Maps;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.drumdie.barberdemo.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapshowFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {

    private final static int ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE = 0;
    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Location userLocation;
    private static final int RANGE_TO_DISPLAY_BARBERSHOP_MARKER_IN_METERS = 1000;

    ////////////////////////////////////////////////////////////////////
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mapshow, container, false);

        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(callback);
                }

                // textView.setText(s);
            }
        });
        return root;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleApiClient= new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getContext(),ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                Location userLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                getUserLastLocation(userLocation);
            } else {
                final String[] permissions = new String[]{ACCESS_FINE_LOCATION};
                requestPermissions(permissions, ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            Location userLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            getUserLastLocation(userLocation);

        }
    }
    private void getUserLastLocation(Location userLocation) {
        if (userLocation != null) {

           this.userLocation = userLocation;
            /* this.userLocation.setLatitude(-34.603722);
            this.userLocation.setLongitude(-58.381592);
            /*
             */
            mapFragment.getMapAsync(this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // compara requestCode dado por el user con el dado por nosotros
        if(requestCode == ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED){
                googleApiClient.reconnect();
            }
            else if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)){
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                builder.setTitle("Acceder a la ubicación del teléfono");
                builder.setMessage("Debes aceptar el permiso para poder utilizar la app ");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String[] permissions = new String[]{ACCESS_FINE_LOCATION};
                        requestPermissions(permissions, ACCESS_FINE_LOCATION_PERMISSION_REQUEST_CODE);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {

        }
    };

    public static String convertMeterToKilometer(float totalDistance) {
        double ff = totalDistance / 1000;
        BigDecimal bd = BigDecimal.valueOf(ff);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return String.valueOf(bd.intValue());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        BitmapDescriptor BarberiaMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.iconshop4);
        final LatLng BarberiaLatLng = new LatLng(-34.615825, -58.429200);
        final LatLng userLatLng = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        final FloatingActionButton locButton = getActivity().findViewById(R.id.miLocationButton);
        final FloatingActionButton barberLocationButton = getActivity().findViewById(R.id.barberLocationButton);
        Location barberShopLoc = new Location("") ;
        barberShopLoc.setLatitude(BarberiaLatLng.latitude);
        barberShopLoc.setLongitude(BarberiaLatLng.longitude);

        final float distanceToBarbershop = Math.round(barberShopLoc.distanceTo(userLocation));  // compilador convertirlo a int redondeando
        mMap.addMarker(new MarkerOptions().position(userLatLng).title("Mi ubicación"));

        mMap.addMarker(new MarkerOptions().position(BarberiaLatLng).title("Barbería 65 ")
                .icon(BarberiaMarkerIcon));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(BarberiaLatLng,14));

        locButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng,14));
                Toast.makeText(getActivity(), "Mi Ubicación ", Toast.LENGTH_SHORT).show();

            }
        });
        barberLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(BarberiaLatLng,16));
                if(distanceToBarbershop < RANGE_TO_DISPLAY_BARBERSHOP_MARKER_IN_METERS ) { // mts
                    Toast.makeText(getActivity(), "BARBERIA 65 " + "\nDistancia:  "+ distanceToBarbershop + " mts", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "BARBERIA 65 " + "\nDistancia:  " + convertMeterToKilometer(distanceToBarbershop) + " Kms", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}