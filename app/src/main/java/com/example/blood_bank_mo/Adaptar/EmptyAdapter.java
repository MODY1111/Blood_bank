package com.example.blood_bank_mo.Adaptar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.Ui.fragment.Information_DontaionFragment;
import com.example.blood_bank_mo.data.model.donations.DonationData;
import com.example.blood_bank_mo.helper.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.ViewHolder> {
    @BindView(R.id.BB)
    TextView BB;
    @BindView(R.id.dontaion_text_name_item)
    TextView dontaionTextNameItem;
    @BindView(R.id.dontaion_hospital_item)
    TextView dontaionHospitalItem;
    @BindView(R.id.donation_city_item)
    TextView donationCityItem;
    @BindView(R.id._donation_detals_item)
    Button DonationDetalsItem;
    @BindView(R.id.donation_button_call_item)
    Button donationButtonCallItem;
    @BindView(R.id.Buttons)
    LinearLayout Buttons;
    @BindView(R.id.Item_Request)
    RelativeLayout ItemRequest;
    private Context context;
    private Activity activity;
    private List<DonationData> donationData = new ArrayList<>();

    AppCompatActivity appCompatActivity;

    public EmptyAdapter(Context context, Activity activity, List<DonationData> donationData) {
        this.context = context;
        this.activity = activity;
        appCompatActivity = (AppCompatActivity) activity;
        this.donationData = donationData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_request,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.BB.setText(donationData.get(position).getBloodType().getName());
        holder.dontaionTextNameItem.setText("اسم المريص : " + donationData.get(position).getPatientName());
        holder.dontaionHospitalItem.setText(donationData.get(position).getHospitalName());
        holder.donationCityItem.setText(donationData.get(position).getCity().getName());


    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.DonationDetalsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Information_DontaionFragment information_dontaion = new Information_DontaionFragment();
                information_dontaion.id = donationData.get(position).getId();
                HelperMethod.replaceFragment(appCompatActivity.getSupportFragmentManager(), R.id.bloodbank_navigation_content, information_dontaion);

            }
        });
        holder.donationButtonCallItem.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String number = "tel:" + donationData.get(position).getPhone();
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number));
                context.startActivity(callIntent);

            }
        });

    }





    @Override
    public int getItemCount() {
        return donationData.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.BB)
        TextView BB;
        @BindView(R.id.dontaion_text_name_item)
        TextView dontaionTextNameItem;
        @BindView(R.id.dontaion_hospital_item)
        TextView dontaionHospitalItem;
        @BindView(R.id.donation_city_item)
        TextView donationCityItem;
        @BindView(R.id._donation_detals_item)
        Button DonationDetalsItem;
        @BindView(R.id.donation_button_call_item)
        Button donationButtonCallItem;
        @BindView(R.id.Buttons)
        LinearLayout Buttons;
        @BindView(R.id.Item_Request)
        RelativeLayout ItemRequest;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
