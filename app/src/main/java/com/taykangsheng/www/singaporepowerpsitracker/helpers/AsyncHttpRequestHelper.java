package com.taykangsheng.www.singaporepowerpsitracker.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class AsyncHttpRequestHelper extends AsyncTask<Bundle, Integer, JSONObject> {

    private Exception exception;
    private HttpRequestHelper httpRequestHelper;
    private JSONObject results;
    public AsyncResponseListener delegate;
    public String AsyncTaskErrorMessage;
    public String tag;

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        this.exception = null;
        this.results = null;
        this.httpRequestHelper = new HttpRequestHelper();
    }

    @Override
    protected JSONObject doInBackground(Bundle... bundles) {
        Log.d("PSITracker", "Async doInBackground");

        String address = bundles[0].getString("address");
        String[] headers = bundles[0].getStringArray("headers");
        String[] queries = bundles[0].getStringArray("queries");
        AsyncTaskErrorMessage = bundles[0].getString("errorMessage");
        tag = bundles[0].getString("tag");

        try {
            httpRequestHelper = new HttpRequestHelper();
            if (headers != null) {
                if (queries != null) {
                    this.results = httpRequestHelper.HTTPGet(address, headers, queries);
                } else {
                    this.results = httpRequestHelper.HTTPGet(address, headers);
                }
            } else {
                this.results = httpRequestHelper.HTTPGet(address);
            }
            return results;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject results) {
        // TODO: check this.exception
        // TODO: do something with the feed
        Log.d("PSITracker", "Async PostExecute");
        if (exception == null){
            delegate.AsyncResponse(results, AsyncTaskErrorMessage, tag);
        } else {
            Log.d("PSITracker", "Async PostExecute", exception);
        }
    }

    public interface AsyncResponseListener{
        void AsyncResponse(JSONObject output, String error_message, String tag);
    }
}
