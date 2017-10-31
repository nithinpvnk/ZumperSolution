package com.nithinkumar.zumpersolution.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nithinkumar.zumpersolution.Activities.ListScreenActivity;
import com.nithinkumar.zumpersolution.Adapters.ListViewAdapter;
import com.nithinkumar.zumpersolution.Model.PlacePool;
import com.nithinkumar.zumpersolution.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class ListScreenFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ListViewAdapter adapter;
    private PlacePool pool;



    public ListScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_screen, container, false);
        pool =  PlacePool.getInstance();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ListViewAdapter((ListScreenActivity) getActivity(), pool);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
}
