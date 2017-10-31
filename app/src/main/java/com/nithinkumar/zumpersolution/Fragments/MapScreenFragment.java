package com.nithinkumar.zumpersolution.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nithinkumar.zumpersolution.Model.PlacePool;
import com.nithinkumar.zumpersolution.R;
import com.nithinkumar.zumpersolution.Utils.ZSPref;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class MapScreenFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private MapView mMapView;
    private ZSPref mZsPref;


    int clickedPlace;

    public MapScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_screen, container, false);
        mZsPref = new ZSPref(getContext());
        mMapView = (MapView) view.findViewById(R.id.fragment_map_view);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();    // This is required to get the map to be displayed immediately when the screen is launched

        try {
            MapsInitializer.initialize((getActivity().getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setMyLocationEnabled(true);


        Log.v("LAT", String.valueOf(mZsPref.getLat_val()));
        Log.v("LONG", String.valueOf(mZsPref.getLong_val()));
        LatLng myLocation = new LatLng(mZsPref.getLat_val(), mZsPref.getLong_val());
        mMap.addMarker(new MarkerOptions().position(myLocation).title("It's Me").snippet("Currently"));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(myLocation).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        nearByPlaces();
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(true)
                {
                    frag();
                }
                return false;

            }
        });
    }

    private void frag()
    {

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.map_main_container);
        if(fragment == null)
        {
            fragment = new DetailScreenFragment();
            fragmentManager.beginTransaction().replace(R.id.map_main_container, fragment).commit();
        }
    }

    private void nearByPlaces() {
        for (int i = 0; i < PlacePool.getInstance().Count(); i++) {
            double lat = PlacePool.getInstance().get(i).getLat();
            double lon = PlacePool.getInstance().get(i).getLon();
            Log.e("VALUESATMAPFRAG", String.valueOf(lat) + " \n" + String.valueOf(lon));
            LatLng place = new LatLng(lat, lon);
            mMap.addMarker((new MarkerOptions()
                    .position(place)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    .title(PlacePool.getInstance().get(i).getName())
                    .snippet(PlacePool.getInstance().get(i).getAddress())));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        }
    }
}
