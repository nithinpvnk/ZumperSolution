package com.nithinkumar.zumpersolution.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.nithinkumar.zumpersolution.Fragments.MapScreenFragment;
import com.nithinkumar.zumpersolution.R;

public class MapScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_screen);
        fragmentLoader();
    }

    private  void fragmentLoader()
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.map_main_container);
        if(fragment == null)
        {
            fragment = new MapScreenFragment();
            fragmentManager.beginTransaction().add(R.id.map_main_container, fragment).commit();
        }
    }
}
