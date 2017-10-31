package com.nithinkumar.zumpersolution.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Network;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.solver.Cache;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nithinkumar.zumpersolution.BuildConfig;
import com.nithinkumar.zumpersolution.Model.Photo;
import com.nithinkumar.zumpersolution.Model.Place;
import com.nithinkumar.zumpersolution.Model.PlacePool;
import com.nithinkumar.zumpersolution.Model.Review;
import com.nithinkumar.zumpersolution.R;
import com.nithinkumar.zumpersolution.Utils.ZSPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenActivity extends AppCompatActivity {

    CardView mapScreen;
    CardView listScreen;

    private static final String TAG = HomeScreenActivity.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    //Entry point for fused location provider API
    private FusedLocationProviderClient mFusedLocationClient;

    //Geographical Location
    protected Location mLastLocation;

    ZSPref mZSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mZSP = new ZSPref(this);
        mapScreen = (CardView) findViewById(R.id.map_screen);
        listScreen = (CardView) findViewById(R.id.list_screen);

        mapScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreenActivity.this, MapScreenActivity.class);
                startActivity(intent);

            }
        });

        listScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreenActivity.this, ListScreenActivity.class);
                startActivity(intent);
            }
        });
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        downloadData();
    }

    private void downloadData() {
        final RequestQueue mRequestQueue;
        final AppCompatActivity activity = this;
        // Instantiate the cache
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        BasicNetwork network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        String base_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        String key = "key=" + getResources().getString(R.string.google_api_key);
        String loc = "location=" + String.valueOf(mZSP.getLat_val()) + "," + String.valueOf(mZSP.getLong_val());
        String extra = "&radius=2000&type=restaurant&";
        Log.e("LOCATION", loc);
        Log.e("URL", base_URL + loc + extra + key);
        String url = base_URL + loc + extra + key;
        //String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=37.541294,-121.975749&radius=2000&type=restaurant&key=AIzaSyATz8fYmjox95GZpoox6KpkN5xPaAiTRQI";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Json", "Success");
                try {
                    /* process Json. */
                    JSONArray results = response.getJSONArray("results");
                    PlacePool pool = PlacePool.getInstance();
                    for (int count = 0; count < results.length(); count++) {
                        JSONObject place = results.getJSONObject(count);
                        double lat = place.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                        double lon = place.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                        String reference = place.getString("reference");
                        String name = place.getString("name");
                        String address = place.getString("vicinity");
                        double ratings = place.getDouble("rating");
                        Log.e("VALUES", String.valueOf(lat) + " \n" + String.valueOf(lon) + " \n" + reference + " \n" + name);
                        pool.add(new Place(lat, lon, address, name, ratings, reference));
                        imgDownload(reference, pool, count);
                    }
                    //  Log.d("no error", pool.get(4).getReference());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json", error.getMessage());
            }
        });
        mRequestQueue.add(jsObjRequest);
    }

    private void imgDownload(String ref, final PlacePool pool, final int pos) {
        final RequestQueue mRequestQueue;
        final AppCompatActivity activity = this;
        // Instantiate the cache
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        BasicNetwork network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();

        String reference = ref;
        String url = "https://maps.googleapis.com/maps/api/place/details/json?reference=" + reference + "&sensor=true&key=AIzaSyATz8fYmjox95GZpoox6KpkN5xPaAiTRQI";
        Log.e("url1234", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("result");

                    Log.e("TESTIJNG", "HTRFG");
                    JSONArray photos = response.getJSONArray("photos");
                    List<Photo> photoList = new ArrayList<>(photos.length());
                    for (int count = 0; count < 1; count++) {
                        JSONObject photo = photos.optJSONObject(count);
                        String height = photo.getString("height");
                        String width = photo.getString("width");
                        String reference = photo.getString("photo_reference");
                        JSONArray htmlAttributes = photo.getJSONArray("html_attributions");
                        List<String> htmlAttrs = new ArrayList<>(htmlAttributes.length());
                        for (int c1 = 0; c1 < htmlAttributes.length(); c1++) {
                            String htmlobj = (String) htmlAttributes.get(c1);
                            htmlAttrs.add(htmlobj);
                        }
                        Photo ph = new Photo(height, width, reference, htmlAttrs);
                        photoList.add(ph);
                    }
                    pool.getInstance().get(pos).setPhotos((List<Photo>) photoList);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json", error.getMessage());
            }
        });
        mRequestQueue.add(jsObjRequest);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

                            mZSP.setLat_val((float) mLastLocation.getLatitude());
                            mZSP.setLong_val((float) mLastLocation.getLongitude());
                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());
                            showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    private void showSnackbar(final String text) {
        View container = findViewById(R.id.main_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId, View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int permissionState3 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE);
        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED && permissionState3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);
        boolean shouldprocidecall = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale && shouldprocidecall) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            startLocationPermissionRequest();
                        }
                    });

        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(HomeScreenActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);

        ActivityCompat.requestPermissions(HomeScreenActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                REQUEST_PERMISSIONS_REQUEST_CODE);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }
}
