package com.example.blood_bank.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood_bank.Adaptar.Check_Box_Adapter;
import com.example.blood_bank.Adaptar.Sitingnavigation_Checkbox_governments;
import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.bloodtyps.BloodTyps;
import com.example.blood_bank.data.model.donations.DonationData;
import com.example.blood_bank.data.model.getnotificationssettings.getNotificationssettings;
import com.example.blood_bank.data.model.governorates.Governorates;
import com.example.blood_bank.data.model.login.Client;
import com.example.blood_bank.data.model.notificationssettings.Notificationssettings;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.blood_bank.data.api.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class Siting_NavigationFragment extends Fragment {


    @BindView(R.id.Editnotfication_Tv)
    TextView EditnotficationTv;
    @BindView(R.id.bloodbank_Edit_notifcation_fragment_tv_bloodtaype)
    TextView bloodbankEditNotifcationFragmentTvBloodtaype;
    @BindView(R.id.bloodbank_Edit_notifcation_fragment_recvgoverment)
    RecyclerView bloodbankEditNotifcationFragmentRecvgoverment;
    @BindView(R.id.bloodbank_Edit_notifcation_fragment_rec_bloodType)
    RecyclerView bloodbankEditNotifcationFragmentRecBloodType;
    @BindView(R.id.bloodbank_Edit_notifcation_fragment_tvgoverment)
    TextView bloodbankEditNotifcationFragmentTvgoverment;
    @BindView(R.id.bloodbank_siting_notfcation_button_save)
    Button bloodbankSitingNotfcationButtonSave;
    private List<DonationData> donationData = new ArrayList<>();
    private ApiService apiService;
    private Check_Box_Adapter Check_Box_Adapter;
    private Sitingnavigation_Checkbox_governments sitingnavigation_checkbox_governments;
    private int max;
    private int category_id = 0;
    private Client client;
    Unbinder unbinder;
    private List<Integer> governmentsIds = new ArrayList<>();
    private List<String> governmentsNames = new ArrayList<>();
    private List<String> bloodtypsNames = new ArrayList<>();
    private List<Integer> bloodtypsId = new ArrayList<>();


    public Siting_NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_siting__navigation, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());

        Check_Box_Adapter = new Check_Box_Adapter(getActivity(), getActivity(), bloodtypsNames, bloodtypsId);
        bloodbankEditNotifcationFragmentRecBloodType.setAdapter(Check_Box_Adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        bloodbankEditNotifcationFragmentRecBloodType.setLayoutManager(gridLayoutManager);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        bloodbankEditNotifcationFragmentRecvgoverment.setLayoutManager(linearLayoutManager);

        sitingnavigation_checkbox_governments = new Sitingnavigation_Checkbox_governments(getActivity(), getActivity(), governmentsNames, governmentsIds);
        bloodbankEditNotifcationFragmentRecvgoverment.setAdapter(sitingnavigation_checkbox_governments);
       apiService.notification(client.getApiToken()).enqueue(new Callback<getNotificationssettings>() {
           @Override
           public void onResponse(Call<getNotificationssettings> call, Response<getNotificationssettings> response) {
               if (response.body().getStatus()==1){

               }
           }

           @Override
           public void onFailure(Call<getNotificationssettings> call, Throwable t) {

           }
       });
        getListBloods();
        getListcategoris();
        return view;
    }


    private void getListcategoris() {
        apiService.getListGovern().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        governmentsNames.add(response.body().getData().get(i).getName());
                        governmentsIds.add(response.body().getData().get(i).getId());
                    }
                    sitingnavigation_checkbox_governments.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });
    }

    private void getListBloods() {
        apiService.getListBloods().enqueue(new Callback<BloodTyps>() {
            @Override
            public void onResponse(Call<BloodTyps> call, Response<BloodTyps> response) {

                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        bloodtypsNames.add(response.body().getData().get(i).getName());
                        bloodtypsId.add(response.body().getData().get(i).getId());
                    }
                    Check_Box_Adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<BloodTyps> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    public void notfication() {
        apiService.notificationSettings(client.getApiToken(), "governorates[]", "governorates").enqueue(new Callback<Notificationssettings>() {
            @Override
            public void onResponse(Call<Notificationssettings> call, Response<Notificationssettings> response) {
                if (response.body().getStatus()==1){
                    Toast.makeText(getActivity(), "تم التسجيل"+response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Notificationssettings> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bloodbank_siting_notfcation_button_save)
    public void onViewClicked() {
        notfication();
    }
    }
