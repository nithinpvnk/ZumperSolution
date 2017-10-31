package com.nithinkumar.zumpersolution.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Nithin on 10/30/2017.
 */

public class ZSPref {

    private SharedPreferences mPrefs;

    private final String lat_val = "Lat_Val";
    private final String long_val = "Long_Val";

    public ZSPref(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void setLat_val(float lat) {
        mPrefs.edit().putFloat(lat_val, lat).apply();
    }

    public float getLat_val() {
        return mPrefs.getFloat(lat_val, 37.7749f);
    }

    public void setLong_val(float lon) {
        mPrefs.edit().putFloat(long_val, lon).apply();
    }

    public float getLong_val() {
        return mPrefs.getFloat(long_val, -122.4194f);
    }
}
