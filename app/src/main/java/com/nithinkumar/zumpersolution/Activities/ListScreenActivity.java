package com.nithinkumar.zumpersolution.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nithinkumar.zumpersolution.Fragments.DetailScreenFragment;
import com.nithinkumar.zumpersolution.Fragments.ListScreenFragment;
import com.nithinkumar.zumpersolution.R;

public class ListScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_screen);

        fragmentLoader();
    }

    private void fragmentLoader() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_main_container);
        if (fragment == null) {
            fragment = new ListScreenFragment();
            Log.e("Stack123", String.valueOf(fragmentManager.getBackStackEntryCount()));
            fragmentManager.beginTransaction().add(R.id.list_main_container, fragment).commit();
        }
    }
}
