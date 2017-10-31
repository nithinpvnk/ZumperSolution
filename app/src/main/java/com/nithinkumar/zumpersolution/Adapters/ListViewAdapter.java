package com.nithinkumar.zumpersolution.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nithinkumar.zumpersolution.Activities.ListScreenActivity;
import com.nithinkumar.zumpersolution.Fragments.DetailScreenFragment;
import com.nithinkumar.zumpersolution.Model.Photo;
import com.nithinkumar.zumpersolution.Model.Place;
import com.nithinkumar.zumpersolution.Model.PlacePool;
import com.nithinkumar.zumpersolution.R;

import java.io.Serializable;

/**
 * Created by Nithin on 10/31/2017.
 */

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder> {

    private ListScreenActivity context;
    private PlacePool pool;


    public ListViewAdapter(ListScreenActivity context, PlacePool pool) {
        this.context = context;
        this.pool = pool;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.places_item_view, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        Place place = pool.getInstance().get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("POS", String.valueOf(position));
                fragDetails(position);
            }
        });
        holder.bindingData(place);
    }

    private void fragDetails(int placeDetails) {
        DetailScreenFragment mFragment = new DetailScreenFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt("SelectedItem", placeDetails);
        mFragment.setArguments(mBundle);
        switchContent(mFragment);

    }

    private void switchContent(DetailScreenFragment mFragment) {
        Log.e("Stack", String.valueOf(context.getSupportFragmentManager().getBackStackEntryCount()));
        context.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_main_container, mFragment)
                .commit();

    }

    @Override
    public int getItemCount() {
        return pool.getInstance().Count();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView namePlate;
        RatingBar rating;
        Place pool;
        PlacePool pp;

        public ListViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.place_icon);
            namePlate = (TextView) itemView.findViewById(R.id.place_item_name);
            rating = (RatingBar) itemView.findViewById(R.id.place_item_rating);
        }

        public void bindingData(Place place) {
            pool = place;


            if(pool.getPhotos() != null)
            {
                String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=150&"
                        + "photoreference="
                        +pool.getPhotos().get(0).getReference()
                        + "&key="
                        + context.getResources().getString(R.string.google_api_key);
                Log.e("URL IMG", url);

                Glide.with(context)
                        .load(url)
                        .into(icon);
            }

            namePlate.setText(pool.getName());
            rating.setRating((float) pool.getRating());
        }
    }
}
