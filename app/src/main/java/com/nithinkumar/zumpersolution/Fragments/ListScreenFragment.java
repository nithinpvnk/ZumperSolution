package com.nithinkumar.zumpersolution.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private SwipeRefreshLayout mSwipeRefreshLayout;



    public ListScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_screen, container, false);
        pool =  PlacePool.getInstance();
        mSwipeRefreshLayout =  view.findViewById(R.id.list_swipe_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ListViewAdapter((ListScreenActivity) getActivity(), pool);
        mRecyclerView.setAdapter(adapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
        return view;
    }

    private void refreshItems() {

        if(pool.getDataCount() > adapter.getItemCount())
        {
            adapter.refreshPull(pool);
        }
        else
        {
            Toast.makeText(getActivity(),"All Items are Downloaded", Toast.LENGTH_LONG).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }
}
