package com.taykangsheng.www.singaporepowerpsitracker.helpers;

import android.os.AsyncTask;
import android.util.Log;

import com.taykangsheng.www.singaporepowerpsitracker.R;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**************************************************
 *
 *      This is a blocking HTTPRequest class
 *
 **************************************************/
public class HttpRequestHelper {

    /**************************************************
     *
     *      Unused Constructor.
     *
     **************************************************/
    public HttpRequestHelper(){

    }

    /**************************************************
     *
     *      Get Request.
     *      String address: the basic url
     *      String[] params: all the keys in pairs
     *          {"key", "value", "key", "value"...}
     *
     *      Returns JSONObject
     *          or null
     *
     **************************************************/
    public JSONObject HTTPGet(String address){
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();

            InputStream inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            return (JSONObject) new JSONTokener(response).nextValue();
        } catch (Exception e){
            Log.d("PSITracker", "HttpRequestHelper", e);
            return null;
        }
    }

    public JSONObject HTTPGet(String address, String[] headers){
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream inStream = null;

        try {
            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            for (int i=0 ; i<headers.length ; i+=2){
                urlConnection.setRequestProperty(headers[i], headers[i+1]);
            }
            urlConnection.connect();

            inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            return (JSONObject) new JSONTokener(response).nextValue();

        } catch (Exception e){
            Log.d("PSITracker", "HttpRequestHelper", e);
            return null;
        }
    }

    public JSONObject HTTPGet(String address, String[] headers, String[] queries){
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            if (queries.length > 0){
                address += "?";
                for (int i=0 ; i<queries.length ; i+=2){
                    address += queries[i];
                    address += "=";
                    address += queries[i+1];
                }
            }
            url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            for (int i=0 ; i<headers.length ; i+=2){
                urlConnection.setRequestProperty(headers[i], headers[i+1]);
            }
            urlConnection.connect();

            InputStream inStream = urlConnection.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inStream));
            String temp, response = "";
            while ((temp = bReader.readLine()) != null) {
                response += temp;
            }
            return (JSONObject) new JSONTokener(response).nextValue();
        } catch (Exception e){
//            throw new RuntimeException(e);
            return null;

        }
    }
}
