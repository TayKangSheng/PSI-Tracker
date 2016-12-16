package com.taykangsheng.www.singaporepowerpsitracker.fragments;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.taykangsheng.www.singaporepowerpsitracker.R;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.DataHelper;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.JSONHelper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Map extends Fragment implements
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        CompoundButton.OnCheckedChangeListener,
        OnMapReadyCallback{
    private GoogleMap mGoogleMap;
    private DataHelper dataHelper;
    private JSONHelper jsonHelper;
    private String currentRegion;
    private Marker currentMarker;

    public static Map newInstance() {
        return new Map();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Map");
        dataHelper = new DataHelper(getContext());
        String mapData = dataHelper.getMapData();
        if ( mapData == null ){
            try {
                jsonHelper = new JSONHelper(null);
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        } else {
            try {
                jsonHelper = new JSONHelper(new JSONObject(dataHelper.getMapData()));
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        // Without this, onMapReady will not be called when its done.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        UiSettings uisettings = mGoogleMap.getUiSettings();
        uisettings.setMapToolbarEnabled(false);
        uisettings.setScrollGesturesEnabled(false);

        if (jsonHelper.isDataAvailable()) {
            String[] locations = jsonHelper.getLocationNames();
            for (int i = 1; i < locations.length; i++) {
                String locationName = locations[i].substring(0, 1).toUpperCase() + locations[i].substring(1);
                LatLng lat_lng = jsonHelper.getLatLong(locations[i]);
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(lat_lng)
                        .title(locationName));
                marker.setTag(locations[i]);

                mGoogleMap.setOnMarkerClickListener(this);
                mGoogleMap.setOnMapClickListener(this);
            }

            LatLng camera_position = jsonHelper.getLatLong("central");
            camera_position = new LatLng(camera_position.latitude + 0.015, camera_position.longitude);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(camera_position));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera_position, 10));

            View header = getView().findViewById(R.id.map_readings_header);
            header.setVisibility(View.VISIBLE);

            addReadingsPanel("national");

        } else {
            View noData = getView().findViewById(R.id.map_readings_none);
            noData.setVisibility(View.VISIBLE);

            LatLng camera_position = new LatLng((double) 1.35735, (double) 103.82);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(camera_position));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera_position, 10));
        }
    }

    public void addReadingsPanel(String region){
        currentRegion = region;

        int latestReadingsIndex = jsonHelper.getNumberOfReadings()-1;
        Date timestamp = jsonHelper.getUpdateTimeStamp(latestReadingsIndex);
        DateFormat print_format = new SimpleDateFormat("MMM dd',' yyyy HH:mma");
        TextView date = (TextView) getView().findViewById(R.id.map_readings_header_date);
        date.setText(print_format.format(timestamp));

        // Update Title
        TextView title = (TextView) getView().findViewById(R.id.map_readings_header_title);
        title.setText(region.substring(0, 1).toUpperCase() + region.substring(1));
        title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Get all data
        float psi_twenty_four_hourly = jsonHelper.getReading(latestReadingsIndex, "psi_twenty_four_hourly", currentRegion);
        float pm10_twenty_four_hourly = jsonHelper.getReading(latestReadingsIndex, "pm10_twenty_four_hourly", currentRegion);
        float pm10_sub_index = jsonHelper.getReading(latestReadingsIndex, "pm10_sub_index", currentRegion);
        float pm25_twenty_four_hourly = jsonHelper.getReading(latestReadingsIndex, "pm25_twenty_four_hourly", currentRegion);
        float psi_three_hourly = jsonHelper.getReading(latestReadingsIndex, "psi_three_hourly", currentRegion);
        float so2_twenty_four_hourly = jsonHelper.getReading(latestReadingsIndex, "so2_twenty_four_hourly", currentRegion);
        float no2_one_hour_max = jsonHelper.getReading(latestReadingsIndex, "no2_one_hour_max", currentRegion);
        float so2_sub_index = jsonHelper.getReading(latestReadingsIndex, "so2_sub_index", currentRegion);
        float o3_sub_index = jsonHelper.getReading(latestReadingsIndex, "o3_sub_index", currentRegion);
        float pm25_sub_index = jsonHelper.getReading(latestReadingsIndex, "pm25_sub_index", currentRegion);
        float co_eight_hour_max = jsonHelper.getReading(latestReadingsIndex, "co_eight_hour_max", currentRegion);
        float o3_eight_hour_max = jsonHelper.getReading(latestReadingsIndex, "o3_eight_hour_max", currentRegion);
        float co_sub_index = jsonHelper.getReading(latestReadingsIndex, "co_sub_index", currentRegion);

        // Update all data.
        TextView temp;

        // Non-detailed view
        temp = (TextView) getView().findViewById(R.id.map_readings_not_detailed_psi);
        temp.setText( String.valueOf(psi_twenty_four_hourly) );

        // detailed view
        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_psi_twenty_four_hourly);
        temp.setText( String.valueOf(psi_twenty_four_hourly) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_pm10_twenty_four_hourly);
        temp.setText( String.valueOf(pm10_twenty_four_hourly) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_pm10_sub_index);
        temp.setText( String.valueOf(pm10_sub_index) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_pm25_twenty_four_hourly);
        temp.setText( String.valueOf(pm25_twenty_four_hourly) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_psi_three_hourly);
        temp.setText( String.valueOf(psi_three_hourly) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_so2_twenty_four_hourly);
        temp.setText( String.valueOf(so2_twenty_four_hourly) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_no2_one_hour_max);
        temp.setText( String.valueOf(no2_one_hour_max) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_so2_sub_index);
        temp.setText( String.valueOf(so2_sub_index) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_o3_sub_index);
        temp.setText( String.valueOf(o3_sub_index) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_pm25_sub_index);
        temp.setText( String.valueOf(pm25_sub_index) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_co_eight_hour_max);
        temp.setText( String.valueOf(co_eight_hour_max) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_o3_eight_hour_max);
        temp.setText( String.valueOf(o3_eight_hour_max) );

        temp = (TextView) getView().findViewById(R.id.map_readings_detailed_co_sub_index);
        temp.setText( String.valueOf(co_sub_index) );

        // Check Switch and show correct set of values
        Switch s = (Switch) getView().findViewById(R.id.map_readings_header_switch);
        onCheckedChanged(s, s.isChecked());
        s.setOnCheckedChangeListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (currentMarker != null) {
            currentMarker.setIcon(BitmapDescriptorFactory.defaultMarker());
        }
        currentMarker = marker;
        marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        addReadingsPanel((String) marker.getTag());
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (!currentRegion.equals("national")){
            if (currentMarker != null) {
                currentMarker.setIcon(BitmapDescriptorFactory.defaultMarker());
            }
            addReadingsPanel("national");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            View notDetailed = getView().findViewById(R.id.map_readings_not_detailed);
            notDetailed.setVisibility(View.GONE);

            View detailed = getView().findViewById(R.id.map_readings_detailed);
            detailed.setVisibility(View.VISIBLE);

        } else{
            View detailed = getView().findViewById(R.id.map_readings_detailed);
            detailed.setVisibility(View.GONE);

            View notDetailed = getView().findViewById(R.id.map_readings_not_detailed);
            notDetailed.setVisibility(View.VISIBLE);
        }
    }
}
