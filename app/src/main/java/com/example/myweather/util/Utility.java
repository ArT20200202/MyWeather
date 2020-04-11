package com.example.myweather.util;

import android.text.TextUtils;

import com.example.myweather.db.City;
import com.example.myweather.db.County;
import com.example.myweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response) {
        if(!TextUtils.isEmpty(response)) {
            try {
                JSONArray AllProvinces = new JSONArray(response);
                for(int i = 0; i < AllProvinces.length(); i++) {
                    JSONObject provinceObject = AllProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;//为什么要返回boolean？
    }

    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray AllCities = new JSONArray(response);
                for (int i = 0; i < AllCities.length(); i++) {
                    JSONObject cityObject = AllCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {
        if(!TextUtils.isEmpty(response)) {
            try {
                JSONArray AllCounties = new JSONArray(response);
                int counties = AllCounties.length();
                for(int i = 0; i < AllCounties.length(); i++) {
                    JSONObject countyObject = AllCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
