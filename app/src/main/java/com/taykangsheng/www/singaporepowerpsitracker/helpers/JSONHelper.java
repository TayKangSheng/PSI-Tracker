package com.taykangsheng.www.singaporepowerpsitracker.helpers;



import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JSONHelper {

    private LatLng mNationalLatLng;
    private LatLng mSouthLatLng;
    private LatLng mNorthLatLng;
    private LatLng mEastLatLng;
    private LatLng mCentralLatLng;
    private LatLng mWestLatLng;
    private JSONArray listOfReadings;
    private boolean dataAvailable = false;

    public JSONHelper(JSONObject json){
        if (json != null) {
            try {
                // Read Location values
                JSONArray region_metadata = json.getJSONArray("region_metadata");
                for (int i = 0; i < region_metadata.length(); i++) {
                    JSONObject obj = region_metadata.getJSONObject(i);
                    String name = obj.getString("name");
                    JSONObject label_location = obj.getJSONObject("label_location");
                    double longitude = label_location.getDouble("longitude");
                    double latitude = label_location.getDouble("latitude");
                    LatLng lat_long = new LatLng(latitude, longitude);
                    switch (name) {
                        case "national":
                            mNationalLatLng = lat_long;
                            break;
                        case "south":
                            mSouthLatLng = lat_long;
                            break;
                        case "north":
                            mNorthLatLng = lat_long;
                            break;
                        case "east":
                            mEastLatLng = lat_long;
                            break;
                        case "central":
                            mCentralLatLng = lat_long;
                            break;
                        case "west":
                            mWestLatLng = lat_long;
                            break;
                    }
                }

                listOfReadings = json.getJSONArray("items");

                dataAvailable = true;

            } catch (Exception e) {
                /*
                *   Log Exception message somewhere
                */
                // e.printStackTrace();
            }
        }
    }

    public String[] getLocationNames(){
        String[] location_names = {"national", "south", "north", "east", "central", "west"};
        return location_names;
    }

    public LatLng getLatLong(String location){
        switch (location){
            case "national":
                return mNationalLatLng;
            case "south":
                return mSouthLatLng;
            case "north":
                return mNorthLatLng;
            case "east":
                return mEastLatLng;
            case "central":
                return mCentralLatLng;
            case "west":
                return mWestLatLng;
            default:
                return null;
        }
    }

    public int getNumberOfReadings(){
        return listOfReadings.length();
    }

    public Date getTimeStamp(int index){
        try {
            String timestamp = listOfReadings.getJSONObject(index).getString("timestamp");
            SimpleDateFormat given_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = given_format.parse(timestamp);
            return date;
        } catch (Exception e){
            // throw new RuntimeException(e);
            return null;
        }
    }

    public Date getUpdateTimeStamp(int index){
        try {
            String timestamp = listOfReadings.getJSONObject(index).getString("update_timestamp");
            SimpleDateFormat given_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = given_format.parse(timestamp);
            return date;
        } catch (Exception e){
             // throw new RuntimeException(e);
            return null;
        }
    }

    public float getReading(int index, String key, String location){
        try {
            JSONObject readings = listOfReadings.getJSONObject(index).getJSONObject("readings");
            return (float) readings.getJSONObject(key).getDouble(location);
        } catch (Exception e){
            //throw new RuntimeException(e);
            return 0;
        }
    }

    public boolean isDataAvailable(){
        return dataAvailable;
    }

}
