package com.example.blood_bank_mo.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class HelperMethod {
    public static void replaceFragment(FragmentManager supportFragmentManager, int id, Fragment fragment) {
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public static void showCalender(Context context, String title, final TextView text_view_data, final DateModel data1) {
        Calendar mCurrentDate = Calendar.getInstance();
        DatePickerDialog mDatePicker = new DatePickerDialog(context, AlertDialog.THEME_HOLO_DARK, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedYear, int selectedMonth, int selectedDay) {
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat mFormat = new DecimalFormat("00", symbols);
                String data = selectedYear + "-" + mFormat.format(Double.valueOf((selectedMonth + 1))) + "-" + mFormat.format(Double.valueOf(selectedDay));
                data1.setDate_txt(data);
                data1.setDay(mFormat.format(Double.valueOf(selectedDay)));
                data1.setMonth(mFormat.format(Double.valueOf(selectedMonth + 1)));
                data1.setYear(String.valueOf(selectedYear));
                text_view_data.setText(data);

            }
        }, Integer.parseInt(data1.getYear()), Integer.parseInt(data1.getMonth()) - 1, Integer.parseInt(data1.getDay()));
        mDatePicker.setTitle(title);
        mDatePicker.show();
    }


    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }

    public static void disappearKeypad(Activity activity, View v) {
        try {
            if (v != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {

        }
    }
}
