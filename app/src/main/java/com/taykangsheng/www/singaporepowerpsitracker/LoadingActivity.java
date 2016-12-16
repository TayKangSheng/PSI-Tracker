package com.taykangsheng.www.singaporepowerpsitracker;


import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.taykangsheng.www.singaporepowerpsitracker.fragments.AlertDialogFragment;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.AsyncHttpRequestHelper;
import com.taykangsheng.www.singaporepowerpsitracker.helpers.DataHelper;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class LoadingActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogListener, AsyncHttpRequestHelper.AsyncResponseListener {
    private View mContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        mContentView = findViewById(R.id.fullscreen_content);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        RetrieveCurrentData();
    }

    private void RetrieveCurrentData(){
        AsyncHttpRequestHelper asyncTask = new AsyncHttpRequestHelper();
        asyncTask.delegate = this;

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Singapore"), Locale.US);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("PSITracker", df.format(calendar.getTime()));

        String URL = "https://api.data.gov.sg/v1/environment/psi";
        String[] headers = {
                "api-key", "5oE2yNgO6mrIfFXG3Y4KNf7KGD4thuO7",
                "Accept", "*/*",
                "User-Agent", "Mozilla/5.0"
        };

        Bundle bundle = new Bundle();
        bundle.putString("address",URL);
        bundle.putStringArray("headers", headers);
        bundle.putString("errorMessage","Fail to receive latest updates.");
        bundle.putString("tag", "map");

        Log.d("PSITracker", "Execute Async");
        asyncTask.execute(bundle);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // goes to the Main Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        dialog.dismiss();
        startActivity(intent);
        finish();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // quit the application.
        dialog.dismiss();
        finish();
    }

    @Override
    public void AsyncResponse(JSONObject currentData, String error_message, String tag) {
        if (currentData == null){
            Log.d("PSITracker", "Fail to receive latest updates.");
            AlertDialogFragment alertFragment = AlertDialogFragment.newInstance(error_message,"Continue","Quit");
            alertFragment.show(getSupportFragmentManager(), "AlertFragment");
        } else {
            Log.d("PSITracker", "Successfully receive Data");
            DataHelper dataHelper = new DataHelper(this);
            dataHelper.setMapData(currentData.toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

}
