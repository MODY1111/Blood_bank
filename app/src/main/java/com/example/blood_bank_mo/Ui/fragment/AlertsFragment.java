package com.example.blood_bank_mo.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blood_bank_mo.Adaptar.Alirts_Adapter;

import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.data.local.OnEndless;
import com.example.blood_bank_mo.data.local.SharedPreferencesManger;
import com.example.blood_bank_mo.data.model.donations.Donations;
import com.example.blood_bank_mo.data.model.login.Client;
import com.example.blood_bank_mo.data.model.notifications.NotificationData;
import com.example.blood_bank_mo.data.model.notifications.Notifications;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank_mo.data.api.RetrofitClient.getClient;
import static com.example.blood_bank_mo.data.local.OnEndless.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertsFragment extends Fragment {


    @BindView(R.id.Alerts_fragment_recyclerview)
    RecyclerView AlertsFragmentRecyclerview;
    Unbinder unbinder;
    private ApiService apiService;
    private Client client;
    private Alirts_Adapter alirts_adapter;
    private List<NotificationData> notificationDataList = new ArrayList<>();
    private int max = 0;

    public AlertsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alerts, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        AlertsFragmentRecyclerview.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    getDonations(current_page);
                }
            }
        };

        alirts_adapter = new Alirts_Adapter(getActivity(),getActivity(),notificationDataList);
        AlertsFragmentRecyclerview.setAdapter(alirts_adapter);


        return view;
    }

    private void getDonations(int page) {
        apiService.NotificationsList(client.getApiToken(), page).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {
                if (response.body().getStatus() == 1) {
                    max = response.body().getData().getLastPage();
                    notificationDataList.addAll(response.body().getData().getData());
                    alirts_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
