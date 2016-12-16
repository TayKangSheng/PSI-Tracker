package com.taykangsheng.www.singaporepowerpsitracker.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.taykangsheng.www.singaporepowerpsitracker.R;


/****************************************************************
 *
 *      This Class is for managing Shared Preference Data
 *      Through this class, we can build a central platform
 *      to access and save data.
 *
 ****************************************************************/
public class DataHelper {
    Context context;
    String preferenceFileKey;
    SharedPreferences sharedPref;

    public DataHelper(Context activity){
        this.context = activity;
        this.preferenceFileKey = context.getString(R.string.preference_file_key);
        sharedPref = context.getSharedPreferences( this.preferenceFileKey, Context.MODE_PRIVATE);
    }

    public String getPSIData(){
        String key = context.getString(R.string.data_key);
        return getData(key);
    }

    public void setPSIData(String toSet){
        String key = context.getString(R.string.data_key);
        setData(key, toSet);
    }

    public String getMapData(){
        String key = context.getString(R.string.map_data_key);
        return getData(key);
    }

    public void setMapData(String toSet){
        String key = context.getString(R.string.map_data_key);
        setData(key, toSet);
    }

    private String getData(String key){
        return sharedPref.getString(key, null);
    }

    private void setData(String key, String value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
