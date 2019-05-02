package com.example.blood_bank_mo.Ui.fragment;


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

import com.example.blood_bank_mo.Adaptar.NotificationSettingsAdapter;

import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.data.local.SharedPreferencesManger;
import com.example.blood_bank_mo.data.model.bloodtyps.BloodTyps;
import com.example.blood_bank_mo.data.model.donations.DonationData;
import com.example.blood_bank_mo.data.model.getnotificationssettings.getNotificationssettings;
import com.example.blood_bank_mo.data.model.governorates.Governorates;
import com.example.blood_bank_mo.data.model.login.Client;
import com.example.blood_bank_mo.data.model.notificationssettings.Notificationssettings;

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
import static com.example.blood_bank_mo.data.api.RetrofitClient.getClient;

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
    private NotificationSettingsAdapter notificationSettingsAdapter1, notificationSettingsAdapter2;
    private int max;
    private int category_id = 0;
    private Client client;
    Unbinder unbinder;
    private List<Integer> governmentsIds = new ArrayList<>();
    public static List<String> governmentsSelectedIds = new ArrayList<>();
    private List<String> governmentsNames = new ArrayList<>();
    private List<String> bloodtypsNames = new ArrayList<>();

    private List<Integer> bloodtypsId = new ArrayList<>();
    public static List<String> bloodtypsSelectedId = new ArrayList<>();
    private List<String> governorates = new ArrayList<>();
    private List<String> bloodTypes = new ArrayList<>();

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

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        bloodbankEditNotifcationFragmentRecBloodType.setLayoutManager(gridLayoutManager);

        notificationSettingsAdapter1 = new NotificationSettingsAdapter(getActivity(), getActivity(), bloodtypsNames, bloodtypsId
                , bloodtypsSelectedId ,0);
        bloodbankEditNotifcationFragmentRecBloodType.setAdapter(notificationSettingsAdapter1);

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 3);
        bloodbankEditNotifcationFragmentRecvgoverment.setLayoutManager(linearLayoutManager);

        notificationSettingsAdapter2 = new NotificationSettingsAdapter(getActivity(), getActivity(), governmentsNames
                , governmentsIds, governmentsSelectedIds,1);
        bloodbankEditNotifcationFragmentRecvgoverment.setAdapter(notificationSettingsAdapter2);

        apiService.notification(client.getApiToken()).enqueue(new Callback<getNotificationssettings>() {
            @Override
            public void onResponse(Call<getNotificationssettings> call, Response<getNotificationssettings> response) {
                if (response.body().getStatus() == 1) {

                    bloodtypsSelectedId = response.body().getData().getBloodTypes();
                    governmentsSelectedIds = response.body().getData().getGovernorates();
                    notificationSettingsAdapter1.notifyDataSetChanged();
                    getListBloods();
                    getListGovernorates();
                }
            }

            @Override
            public void onFailure(Call<getNotificationssettings> call, Throwable t) {

            }
        });

        return view;
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
                    notificationSettingsAdapter1.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<BloodTyps> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    private void getListGovernorates() {
        apiService.getListGovernorates().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        governmentsNames.add(response.body().getData().get(i).getName());
                        governmentsIds.add(response.body().getData().get(i).getId());
                    }
                    notificationSettingsAdapter2.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });
    }

    public void notfication() {
        apiService.notificationSettings(client.getApiToken(), "governorates[]", "governorates").enqueue(new Callback<Notificationssettings>() {
            @Override
            public void onResponse(Call<Notificationssettings> call, Response<Notificationssettings> response) {
                if (response.body().getStatus() == 1) {
                    Toast.makeText(getActivity(), "تم التسجيل" + response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Notificationssettings> call, Throwable t) {

            }
        });


    }

    public void getnotificatio() {
        apiService.getnotification(client.getApiToken()).enqueue(new Callback<getNotificationssettings>() {
            @Override
            public void onResponse(Call<getNotificationssettings> call, Response<getNotificationssettings> response) {
                if (response.body().getStatus() == 1) {

                    Toast.makeText(getActivity(), "تم الحفظ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<getNotificationssettings> call, Throwable t) {

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
        getnotificatio();
    }
}
