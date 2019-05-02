package com.example.blood_bank_mo.data.local;

import android.app.Activity;
import android.content.SharedPreferences;

import com.example.blood_bank_mo.data.model.bloodtyps.BloodTypsData;
import com.example.blood_bank_mo.data.model.citys.CityData;
import com.example.blood_bank_mo.data.model.governorates.GovernoratesData;
import com.example.blood_bank_mo.data.model.login.Client;


public class SharedPreferencesManger {

    private static SharedPreferences sharedPreferences = null;
    private static String USER_ID = "USER_ID";
    private static String USER_EMAIL = "USER_EMAIL";
    private static String USER_NAME = "USER_NAME";
    private static String USER_BID = "USER_BID";
    private static String USER_CITY_ID = "USER_CITY_ID";
    private static String USER_PHONE = "USER_PHONE";
    private static String USER_DLD = "USER_DLD";
    private static String USER_BLOOD_TYPE_ID = "USER_BLOOD_TYPE";
    private static String USER_BLOOD_TYPE_NAME = "USER_BLOOD_TYPE";
    private static String USER_PIN_CODE = "USER_PIN_CODE";
    public static String USER_API_TOKEN = "USER_API_TOKEN";
    private static String USER_CITY_NAME = "USER_CITY_NAME ";

    public static String REMEMBER = "REMEMBER";
    private static String USER_GOVERMENT_ID = "USER_GOVERMENT_ID ";
    private static String USER_GOVERMENT_NAME = "USER_GOVERMENT_NAME ";

    public static void setSharedPreferences(Activity activity) {
        if (sharedPreferences == null) {
            sharedPreferences = activity.getSharedPreferences(
                    "Blood", activity.MODE_PRIVATE);

        }
    }

    public static void SaveData(Activity activity, String data_Key, String data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }
    public static void SaveData(Activity activity, String data_Key, boolean data_Value) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(data_Key, data_Value);
            editor.commit();
        } else {
            setSharedPreferences(activity);
        }
    }

    public static boolean LoadBoolean (Activity activity, String data_Key){
            if (sharedPreferences != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
            } else {
                setSharedPreferences(activity);
            }

            return sharedPreferences.getBoolean(data_Key, false);
        }




    public static String LoadData(Activity activity, String data_Key) {
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
        } else {
            setSharedPreferences(activity);
        }

        return sharedPreferences.getString(data_Key, null);
    }

    public static void saveUserData(Activity activity, Client userData) {
        //  setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(USER_ID, userData.getId());
            editor.putString(USER_EMAIL, userData.getEmail());
            editor.putString(USER_NAME, userData.getName());
            editor.putString(USER_BID, userData.getBirthDate());
            editor.putString(USER_CITY_ID, userData.getCityId());
            editor.putString(USER_CITY_NAME, userData.getCity().getName());
            editor.putString(USER_GOVERMENT_ID, userData.getCity().getGovernorateId());
            editor.putString(USER_GOVERMENT_NAME, userData.getCity().getGovernorate().getName());
            editor.putString(USER_PHONE, userData.getPhone());
            editor.putString(USER_DLD, userData.getDonationLastDate());
            editor.putString(USER_BLOOD_TYPE_ID, String.valueOf(userData.getBloodType().getId()));
            editor.putString(USER_BLOOD_TYPE_NAME, userData.getBloodType().getName());
            editor.putString(USER_PIN_CODE, userData.getPinCode());
            editor.putString(USER_API_TOKEN, userData.getApiToken());
            editor.commit();
        } else {

        }
    }

    public static Client loadUserData(Activity activity) {
        Client userData = new Client();
        setSharedPreferences(activity);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            int id = sharedPreferences.getInt(USER_ID, 0);
            String name = sharedPreferences.getString(USER_NAME, null);
            String email = sharedPreferences.getString(USER_EMAIL, null);
            String birthDate = sharedPreferences.getString(USER_BID, null);
            String phone = sharedPreferences.getString(USER_PHONE, null);
            String donationLastDate = sharedPreferences.getString(USER_DLD, null);

            String bloodTypeId = sharedPreferences.getString(USER_BLOOD_TYPE_ID, null);
            String bloodTypename = sharedPreferences.getString(USER_BLOOD_TYPE_NAME, null);
            String pinCode = sharedPreferences.getString(USER_PIN_CODE, null);
            String apiToken = sharedPreferences.getString(USER_API_TOKEN, null);

            BloodTypsData bloodType = new BloodTypsData();

            String cityId = sharedPreferences.getString(USER_CITY_ID, null);
            String cityName = sharedPreferences.getString(USER_CITY_NAME, null);
            String ganermentId = sharedPreferences.getString(USER_GOVERMENT_NAME, null);
            String ganermentName = sharedPreferences.getString(USER_GOVERMENT_ID, null);
            GovernoratesData governorate = new GovernoratesData();
            CityData city = new CityData();
            userData = new Client(id, name, email, birthDate, cityId, phone, donationLastDate, bloodTypeId, city, bloodType, apiToken);

            editor.commit();
        } else {

        }
        return userData;
    }
}
