package com.example.kartik.testpaging.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.kartik.testpaging.R;

public class SettingsFragment extends PreferenceFragmentCompat{

    private static final String TAG = "SettingsFragment";

    public static final String KEY_LIST_PREFERENCE = "listPref";

    private ListPreference listPreference;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        addPreferencesFromResource(R.xml.pref_visualizer);


    }



}
