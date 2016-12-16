package com.taykangsheng.www.singaporepowerpsitracker.helpers;


import com.google.android.gms.maps.model.LatLng;

import org.hamcrest.core.*;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;


public class JSONHelperTest {

    @Test
    public void testGetLocationNames() throws Exception {
        /*
        *   getLocationNames() must always return 6 locations in String array
        * */
        String correctFomat = "{ 'region_metadata':[{'name': 'national', 'label_location':{ 'longitude':0, 'latitude':0 } }, {'name': 'central', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':0, 'latitude':0 } } ], 'items':[{}] }";

        /*
        * Test getLocationNames() correct string array.
        * */
        JSONObject get_location_names_test = new JSONObject(correctFomat);
        JSONHelper get_location_names_helper = new JSONHelper(get_location_names_test);
        assertThat(get_location_names_helper.getLocationNames().length, is(6));
        assertThat(Arrays.asList(get_location_names_helper.getLocationNames()).contains("national"), is(true));
        assertThat(Arrays.asList(get_location_names_helper.getLocationNames()).contains("north"), is(true));
        assertThat(Arrays.asList(get_location_names_helper.getLocationNames()).contains("south"), is(true));
        assertThat(Arrays.asList(get_location_names_helper.getLocationNames()).contains("east"), is(true));
        assertThat(Arrays.asList(get_location_names_helper.getLocationNames()).contains("west"), is(true));
        assertThat(Arrays.asList(get_location_names_helper.getLocationNames()).contains("central"), is(true));
    }

    @Test
    public void testGetLatLong() throws Exception {
        /*
        * testGetLatLong(String) returns a LatLng object if string provided is one of the 6
        * testGetLatLong(String) returns null if string provided is not one of the 6
        * testGetLatLong(String) returns LatLng object with correct longitude and latitude values
        * */

        String correctFomat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':1, 'latitude':2 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':3, 'latitude':4 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':5, 'latitude':6 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':7, 'latitude':8 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':9, 'latitude':10 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':11, 'latitude':12 } } ], 'items':[{}] }";

        JSONObject get_latlng_test = new JSONObject(correctFomat);
        JSONHelper get_latlng_helper = new JSONHelper(get_latlng_test);

        /*
        * testGetLatLong(String) returns LatLng object or null
        * */
        assertThat(get_latlng_helper.getLatLong("national"), isA(LatLng.class));
        assertThat(get_latlng_helper.getLatLong("north"), isA(LatLng.class));
        assertThat(get_latlng_helper.getLatLong("south"), isA(LatLng.class));
        assertThat(get_latlng_helper.getLatLong("east"), isA(LatLng.class));
        assertThat(get_latlng_helper.getLatLong("west"), isA(LatLng.class));
        assertThat(get_latlng_helper.getLatLong("central"), isA(LatLng.class));
        assertThat(get_latlng_helper.getLatLong("wrongLocation"), is(nullValue()));

        /*
        * testGetLatLong(String) returns correct values
        * */
        assertThat(get_latlng_helper.getLatLong("national").longitude, is((double) 1));
        assertThat(get_latlng_helper.getLatLong("national").latitude, is((double) 2));
        assertThat(get_latlng_helper.getLatLong("central").longitude, is((double) 3));
        assertThat(get_latlng_helper.getLatLong("central").latitude, is((double) 4));
        assertThat(get_latlng_helper.getLatLong("north").longitude, is((double) 5));
        assertThat(get_latlng_helper.getLatLong("north").latitude, is((double) 6));
        assertThat(get_latlng_helper.getLatLong("south").longitude, is((double) 7));
        assertThat(get_latlng_helper.getLatLong("south").latitude, is((double) 8));
        assertThat(get_latlng_helper.getLatLong("east").longitude, is((double) 9));
        assertThat(get_latlng_helper.getLatLong("east").latitude, is((double) 10));
        assertThat(get_latlng_helper.getLatLong("west").longitude, is((double) 11));
        assertThat(get_latlng_helper.getLatLong("west").latitude, is((double) 12));
    }

    @Test
    public void testGetNumberOfReadings() throws Exception {
        /*
        * getNumberOfReadings() always return an Integer;
        * It always return the number of readings provided.
        * */
        String correctFomat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':0, 'latitude':0 } } ], 'items':[{}] }";

        /*
        * Test numberOfReadings() returns integer.
        * */
        JSONObject integer_test = new JSONObject(correctFomat);
        JSONHelper integer_test_helper = new JSONHelper(integer_test);
        assertThat(integer_test_helper.getNumberOfReadings(), instanceOf(Integer.class));

        /*
        * Test numberOfReadings() returns correct number of readings
        * */
        String readings_1 = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':0, 'latitude':0 } } ], 'items':[{}] }";
        JSONObject count_test = new JSONObject(readings_1);
        JSONHelper count_test_helper = new JSONHelper(count_test);
        assertThat(count_test_helper.getNumberOfReadings(), is(1));

        String readings_5 = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':0, 'latitude':0 } } ], 'items':[{}, {}, {}, {}, {}] }";
        JSONObject count_test5 = new JSONObject(readings_5);
        JSONHelper count_test5_helper = new JSONHelper(count_test5);
        assertThat(count_test5_helper.getNumberOfReadings(), is(5));

        String readings_24 = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':0, 'latitude':0 } } ], 'items':[{}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}] }";
        JSONObject count_test24 = new JSONObject(readings_24);
        JSONHelper count_test24_helper = new JSONHelper(count_test24);
        assertThat(count_test24_helper.getNumberOfReadings(), is(24));
    }

    @Test
    public void testGetTimeStamp() throws Exception {
        /*
        * getTimeStamp() returns a data if there is a timestamp
        * getTimeStamp() returns null if there is no timestamp
        * getTimeStamp() returns the correct timestamp by index
        * getTimeStamp() returns null if the index does not exist
        *
        * Note:
        *   android SimpleDateFormat for parsing +08:00 uses the letter Z.
        *   JUnit SimpleDateFormat for parsing +8:00 uses the letter X
        *   http://stackoverflow.com/questions/34686477/junit4-test-causes-java-text-parseexception-unparseable-date
        */

        String withTimeStampFormat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':1, 'latitude':2 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':3, 'latitude':4 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':5, 'latitude':6 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':7, 'latitude':8 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':9, 'latitude':10 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':11, 'latitude':12 } } ], " +
                "'items':[" +
                "{'timestamp':'2016-12-15T13:00:00+0800'}, " +
                "{'timestamp':'2016-12-15T12:00:00+0800'}, " +
                "{'timestamp':'2016-12-15T11:00:00+0800'}, " +
                "{'timestamp':'2016-12-15T10:00:00+0800'}, " +
                "{'timestamp':'2016-12-15T09:00:00+0800'} ] }";

        String withoutTimeStampFormat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':1, 'latitude':2 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':3, 'latitude':4 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':5, 'latitude':6 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':7, 'latitude':8 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':9, 'latitude':10 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':11, 'latitude':12 } } ], " +
                "'items':[{}, {}, {}, {}, {}] }";

        JSONObject with_timestamp_test = new JSONObject(withTimeStampFormat);
        JSONHelper with_timestamp_helper = new JSONHelper(with_timestamp_test);
        JSONObject without_timestamp_test = new JSONObject(withoutTimeStampFormat);
        JSONHelper without_timestamp_helper = new JSONHelper(without_timestamp_test);

        /*
        * returns date object if there is a timestamp
        * returns null if there is no timestamp
        * */
        assertThat(with_timestamp_helper.getTimeStamp(0), isA(Date.class));
        assertThat(without_timestamp_helper.getTimeStamp(0), is(nullValue()));
        assertThat(with_timestamp_helper.getTimeStamp(1), isA(Date.class));
        assertThat(without_timestamp_helper.getTimeStamp(1), is(nullValue()));
        assertThat(with_timestamp_helper.getTimeStamp(2), isA(Date.class));
        assertThat(without_timestamp_helper.getTimeStamp(2), is(nullValue()));
        assertThat(with_timestamp_helper.getTimeStamp(3), isA(Date.class));
        assertThat(without_timestamp_helper.getTimeStamp(3), is(nullValue()));
        assertThat(with_timestamp_helper.getTimeStamp(4), isA(Date.class));
        assertThat(without_timestamp_helper.getTimeStamp(4), is(nullValue()));

        /*
        * returns correct timestamp if index exist
        * returns null if index does not exist
        * */
        String timestamp1 = "2016-12-15T13:00:00+0800";
        String timestamp2 = "2016-12-15T12:00:00+0800";
        String timestamp3 = "2016-12-15T11:00:00+0800";
        String timestamp4 = "2016-12-15T10:00:00+0800";
        String timestamp5 = "2016-12-15T9:00:00+0800";
        SimpleDateFormat given_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        assertThat(with_timestamp_helper.getTimeStamp(0), is(given_format.parse(timestamp1)));
        assertThat(with_timestamp_helper.getTimeStamp(1), is(given_format.parse(timestamp2)));
        assertThat(with_timestamp_helper.getTimeStamp(2), is(given_format.parse(timestamp3)));
        assertThat(with_timestamp_helper.getTimeStamp(3), is(given_format.parse(timestamp4)));
        assertThat(with_timestamp_helper.getTimeStamp(4), is(given_format.parse(timestamp5)));
        assertThat(with_timestamp_helper.getTimeStamp(5), is(nullValue()));
    }

    @Test
    public void testGetUpdateTimeStamp() throws Exception {
        /*
        * getUpdateTimeStamp(index) returns a data if there is a update_timestamp
        * getUpdateTimeStamp(index) returns null if there is no update_timestamp
        * getUpdateTimeStamp(index) returns the correct update_timestamp by index
        * getUpdateTimeStamp(index) returns null if the index does not exist
        *
        * Note:
        *   android SimpleDateFormat for parsing +08:00 uses the letter Z.
        *   JUnit SimpleDateFormat for parsing +8:00 uses the letter X
        *   http://stackoverflow.com/questions/34686477/junit4-test-causes-java-text-parseexception-unparseable-date
        */

        String withUpdateTimeStampFormat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':1, 'latitude':2 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':3, 'latitude':4 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':5, 'latitude':6 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':7, 'latitude':8 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':9, 'latitude':10 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':11, 'latitude':12 } } ], " +
                "'items':[" +
                "{'update_timestamp':'2016-12-15T13:00:00+0800'}, " +
                "{'update_timestamp':'2016-12-15T12:00:00+0800'}, " +
                "{'update_timestamp':'2016-12-15T11:00:00+0800'}, " +
                "{'update_timestamp':'2016-12-15T10:00:00+0800'}, " +
                "{'update_timestamp':'2016-12-15T09:00:00+0800'} ] }";

        String withoutUpdateTimeStampFormat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':1, 'latitude':2 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':3, 'latitude':4 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':5, 'latitude':6 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':7, 'latitude':8 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':9, 'latitude':10 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':11, 'latitude':12 } } ], " +
                "'items':[{}, {}, {}, {}, {}] }";

        JSONObject with_update_timestamp_test = new JSONObject(withUpdateTimeStampFormat);
        JSONHelper with_update_timestamp_helper = new JSONHelper(with_update_timestamp_test);
        JSONObject without_update_timestamp_test = new JSONObject(withoutUpdateTimeStampFormat);
        JSONHelper without_update_timestamp_helper = new JSONHelper(without_update_timestamp_test);

        /*
        * returns date object if there is a update_timestamp
        * returns null if there is no update_timestamp
        * */
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(0), isA(Date.class));
        assertThat(without_update_timestamp_helper.getUpdateTimeStamp(0), is(nullValue()));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(1), isA(Date.class));
        assertThat(without_update_timestamp_helper.getUpdateTimeStamp(1), is(nullValue()));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(2), isA(Date.class));
        assertThat(without_update_timestamp_helper.getUpdateTimeStamp(2), is(nullValue()));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(3), isA(Date.class));
        assertThat(without_update_timestamp_helper.getUpdateTimeStamp(3), is(nullValue()));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(4), isA(Date.class));
        assertThat(without_update_timestamp_helper.getUpdateTimeStamp(4), is(nullValue()));

        /*
        * returns correct timestamp if index exist
        * returns null if index does not exist
        * */
        String timestamp1 = "2016-12-15T13:00:00+0800";
        String timestamp2 = "2016-12-15T12:00:00+0800";
        String timestamp3 = "2016-12-15T11:00:00+0800";
        String timestamp4 = "2016-12-15T10:00:00+0800";
        String timestamp5 = "2016-12-15T9:00:00+0800";
        SimpleDateFormat given_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(0), is(given_format.parse(timestamp1)));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(1), is(given_format.parse(timestamp2)));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(2), is(given_format.parse(timestamp3)));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(3), is(given_format.parse(timestamp4)));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(4), is(given_format.parse(timestamp5)));
        assertThat(with_update_timestamp_helper.getUpdateTimeStamp(5), is(nullValue()));

    }

    @Test
    public void testGetReading() throws Exception {
        /*
        * getReadings(index, key, location) returns float if index, key and location exist
        * getReadings(index, key, location) returns 0 if index, key and location does not exist
        * getReadings(index, key, location) returns the correct float if index, key and location exist
        * */

        String correct_format = "{ " +
                "'region_metadata':[" +
                    "{'name': 'national', 'label_location':{ 'longitude':1, 'latitude':2 } }, " +
                    "{'name': 'central', 'label_location':{ 'longitude':3, 'latitude':4 } }, " +
                    "{'name': 'north', 'label_location':{ 'longitude':5, 'latitude':6 } }, " +
                    "{'name': 'south', 'label_location':{ 'longitude':7, 'latitude':8 } }, " +
                    "{'name': 'east', 'label_location':{ 'longitude':9, 'latitude':10 } }, " +
                    "{'name': 'west', 'label_location':{ 'longitude':11, 'latitude':12 } }" +
                "], " +
                "'items': [ {'update_timestamp': '2016-12-15T13:06:18+08:00','timestamp': '2016-12-15T13:00:00+08:00'," +
                    "'readings': {"+
                        "'psi_twenty_four_hourly': {'national': 60,'south': 53,'north': 60,'east': 56,'central': 53,'west': 52},"+
                        "'pm10_twenty_four_hourly': {'national': 33,'south': 30,'north': 33,'east': 28,'central': 22,'west': 26},"+
                        "'pm10_sub_index': {'national': 33,'south': 30,'north': 33,'east': 28,'central': 22,'west': 26},"+
                        "'pm25_twenty_four_hourly': {'national': 20,'south': 14,'north': 20,'east': 17,'central': 14,'west': 13}"+
                    "}" +
                "}, {'update_timestamp': '2016-12-15T13:06:18+08:00','timestamp': '2016-12-15T13:00:00+08:00'," +
                "'readings': {"+
                "'psi_twenty_four_hourly': {'national': 60,'south': 53,'north': 60,'east': 56,'central': 53,'west': 52},"+
                "'pm10_twenty_four_hourly': {'national': 33,'south': 30,'north': 33,'east': 28,'central': 22,'west': 26},"+
                "'pm10_sub_index': {'national': 33,'south': 30,'north': 33,'east': 28,'central': 22,'west': 26},"+
                "'pm25_twenty_four_hourly': {'national': 20,'south': 14,'north': 20,'east': 17,'central': 14,'west': 13}"+
                "}}]}";


        JSONObject get_readings_test = new JSONObject(correct_format);
        JSONHelper get_readings_helper = new JSONHelper(get_readings_test);
        /*
        * getReadings(index, key, location) returns float if index, key and location exist
        * */
        assertThat(get_readings_helper.getReading(0, "psi_twenty_four_hourly", "national"), isA(float.class));
        assertThat(get_readings_helper.getReading(1, "pm10_twenty_four_hourly", "south"), isA(float.class));
        assertThat(get_readings_helper.getReading(0, "pm10_sub_index", "north"), isA(float.class));
        assertThat(get_readings_helper.getReading(1, "pm25_twenty_four_hourly", "west"), isA(float.class));
        assertThat(get_readings_helper.getReading(0, "psi_twenty_four_hourly", "central"), isA(float.class));
        assertThat(get_readings_helper.getReading(1, "pm10_twenty_four_hourly", "east"), isA(float.class));

        /*
        * getReadings(index, key, location) returns 0 if index, key and location does not exist
        * */
        assertThat(get_readings_helper.getReading(0, "psi_twenty_four_hourly", "testing"), is((float)0));
        assertThat(get_readings_helper.getReading(1, "pm10_twenty_four_hourly", "testing2"), is((float)0));
        assertThat(get_readings_helper.getReading(0, "testing", "north"), is((float)0));
        assertThat(get_readings_helper.getReading(1, "testing2", "west"), is((float)0));
        assertThat(get_readings_helper.getReading(2, "psi_twenty_four_hourly", "central"), is((float)0));
        assertThat(get_readings_helper.getReading(3, "pm10_twenty_four_hourly", "east"), is((float)0));

        /*
        * getReadings(index, key, location) returns correct value if index, key and location exist
        * */
        assertThat(get_readings_helper.getReading(0, "psi_twenty_four_hourly", "national"), is( (float) 60 ));
        assertThat(get_readings_helper.getReading(1, "pm10_twenty_four_hourly", "south"), is( (float) 30 ));
        assertThat(get_readings_helper.getReading(0, "pm10_sub_index", "north"), is( (float) 33));
        assertThat(get_readings_helper.getReading(1, "pm25_twenty_four_hourly", "west"), is( (float) 13 ));
        assertThat(get_readings_helper.getReading(0, "psi_twenty_four_hourly", "central"), is( (float) 53 ));
        assertThat(get_readings_helper.getReading(1, "pm10_twenty_four_hourly", "east"), is( (float) 28));
    }

    @Test
    public void testIsDataAvailable() throws Exception {
        /*
        * isDataAvailable() must always return a boolean.
        * isDataAvailable() return true if json is formatted properly
        * isDataAvailable() return false if json is NOT formatted properly
        */

        String wrongFormat = "{}";
        String correctFomat = "{ 'region_metadata':[" +
                "{'name': 'national', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'central', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'north', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'south', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'east', 'label_location':{ 'longitude':0, 'latitude':0 } }, " +
                "{'name': 'west', 'label_location':{ 'longitude':0, 'latitude':0 } } ], 'items':[{}, {},{}] }";

        /*
        * Test to return boolean
        * case 1: empty json provided.
        * case 2: json with correct format provided
        * case 3: json with wrong format provided
        * */
        JSONObject booleanTest_obj = new JSONObject();
        JSONHelper booleanTest_helper = new JSONHelper(booleanTest_obj);
        assertThat(booleanTest_helper.isDataAvailable(), instanceOf(boolean.class) );

        JSONObject booleanTest_obj2 = new JSONObject();
        JSONHelper booleanTest_helper2 = new JSONHelper(booleanTest_obj2);
        assertThat(booleanTest_helper2.isDataAvailable(), instanceOf(boolean.class) );

        JSONObject booleanTest_obj3 = new JSONObject();
        JSONHelper booleanTest_helper3 = new JSONHelper(booleanTest_obj3);
        assertThat(booleanTest_helper3.isDataAvailable(), instanceOf(boolean.class) );

        /*
        * Test to return true if json is formatted properly
        * */
        JSONObject true_test = new JSONObject(correctFomat);
        JSONHelper true_test_helper = new JSONHelper(true_test);
        assertThat(true_test_helper.isDataAvailable(), Is.is(true));

        /*
        * Test to return false if json is NOT formatted properly
        * */
        JSONObject false_test = new JSONObject(wrongFormat);
        JSONHelper false_test_helper = new JSONHelper(false_test);
        assertThat(false_test_helper.isDataAvailable(), Is.is(false));
    }
}