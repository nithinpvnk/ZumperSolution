package com.nithinkumar.zumpersolution.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.nithinkumar.zumpersolution.Adapters.ReviewAdapter;
import com.nithinkumar.zumpersolution.Model.Photo;
import com.nithinkumar.zumpersolution.Model.Place;
import com.nithinkumar.zumpersolution.Model.PlacePool;
import com.nithinkumar.zumpersolution.Model.Review;
import com.nithinkumar.zumpersolution.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailScreenFragment extends Fragment {


    private TextView mName;
    private TextView mAddress;
    private RatingBar mRating;
    private TextView mContact;
    private LinearLayout contactInfo;
    private ImageView img;

    private RecyclerView reviewsView;
    private ReviewAdapter adapter;

    int pos;
    private PlacePool details;

    public DetailScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_place_details, container, false);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            pos = (int) bundle.getInt("SelectedItem");
            Log.e("POSTIOB", String.valueOf(pos));
        }

        mName = (TextView) view.findViewById(R.id.place_name);
        mAddress = (TextView) view.findViewById(R.id.place_address);
        mRating = (RatingBar) view.findViewById(R.id.place_rating);
        mContact = (TextView) view.findViewById(R.id.place_contact);
        reviewsView = (RecyclerView) view.findViewById(R.id.place_reviews);
        img = (ImageView) view.findViewById(R.id.place_image);

        contactInfo = (LinearLayout) view.findViewById(R.id.contact_info);
        detailDataDownload(pos);

        adapter = new ReviewAdapter(getContext(), details.getInstance().get(pos).getReviews());
        reviewsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        reviewsView.setAdapter(adapter);
        dataSet();

        return view;
    }

    private void dataSet() {
        mName.setText(details.getInstance().get(pos).getName());
        mAddress.setText(details.getInstance().get(pos).getAddress());
        mRating.setRating((float) details.getInstance().get(pos).getRating());
        mContact.setText(details.getInstance().get(pos).getMobileNUmber());

        if(details.getInstance().get(pos).getPhotos() != null)
        {
            String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=150&"
                    + "photoreference="
                    +details.getInstance().get(pos).getPhotos().get(0).getReference()
                    + "&key="
                    + getResources().getString(R.string.google_api_key);
            Log.e("URL IMG", url);

            Glide.with(getContext())
                    .load(url)
                    .into(img);
        }
    }

    private void detailDataDownload(final int pos) {
        final RequestQueue mRequestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);

        // Start the queue
        mRequestQueue.start();
        String reference = details.getInstance().get(pos).getReference();
        String url = "https://maps.googleapis.com/maps/api/place/details/json?reference=" + reference + "&sensor=true&key=AIzaSyATz8fYmjox95GZpoox6KpkN5xPaAiTRQI";
        Log.e("url", url);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response = response.getJSONObject("result");
                    String address = response.getString("formatted_address");
                    details.getInstance().get(pos).setAddress(address);
                    Log.e("address", address);
                    String phoneNumber = response.getString("formatted_phone_number");
                    details.getInstance().get(pos).setMobileNUmber(phoneNumber);

                    JSONArray reviews = response.getJSONArray("reviews");
                    List<Review> reviewsList = new ArrayList<>(reviews.length());
                    for (int count = 0; count < reviews.length(); count++) {
                        JSONObject review = reviews.optJSONObject(count);
                        String author = review.getString("author_name");
                        double rating = review.getDouble("rating");
                        long time = review.getLong("time");
                        Log.e("time",time+"");
                        String revText = review.getString("text");
                        Review review1 = new Review(author, time, rating, revText);
                        reviewsList.add(review1);
                    }
                    details.getInstance().get(pos).setReviews(reviewsList);

                    JSONArray photos = response.getJSONArray("photos");
                    List<Photo> photoList = new ArrayList<>(photos.length());
                    for (int count = 0; count < photos.length(); count++) {
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
                    details.getInstance().get(pos).setPhotos(photoList);
                    Log.e("review",details.getInstance().get(pos).getReviews().get(0).getReviewText());
                    adapter.setData(details.getInstance().get(pos).getReviews());
                    dataSet();
                    callingPlace();
                } catch (Exception ex) {
                    Log.e("Json", ex.getMessage());
                    ex.printStackTrace();
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

    private void callingPlace() {
        contactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = details.getInstance().get(pos).getMobileNUmber().replaceAll("-", "");
                Log.e("NUM", num);
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + num));
                startActivity(callIntent);
            }
        });
    }
}
