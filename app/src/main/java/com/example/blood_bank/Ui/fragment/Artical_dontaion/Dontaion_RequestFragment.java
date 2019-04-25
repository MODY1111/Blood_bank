package com.example.blood_bank.Ui.fragment.Artical_dontaion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blood_bank.Adaptar.EmptyAdapter;
import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.OnEndless;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.donations.DonationData;
import com.example.blood_bank.data.model.donations.Donations;
import com.example.blood_bank.data.model.login.Client;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank.data.api.RetrofitClient.getClient;
import static com.example.blood_bank.data.local.OnEndless.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dontaion_RequestFragment extends Fragment {


    @BindView(R.id.Dontaion_request_rev)
    RecyclerView DontaionRequestRev;
    Unbinder unbinder;
    private int max = 0;
    private ApiService apiService;
    private Client client;
    private EmptyAdapter emptyAdapter;
    private List<DonationData> donationData = new ArrayList<>();

    public Dontaion_RequestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dontaion__request, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        DontaionRequestRev.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    getDonations(current_page);
                }
            }
        };
        DontaionRequestRev.addOnScrollListener(onEndless);
        emptyAdapter = new EmptyAdapter(getActivity(), getActivity(), donationData);
        DontaionRequestRev.setAdapter(emptyAdapter);
        getDonations(1);
        return view;

    }

    private void getDonations(int page) {
        apiService.getDonation(client.getApiToken(), page).enqueue(new Callback<Donations>() {
            @Override
            public void onResponse(Call<Donations> call, Response<Donations> response) {
                if (response.body().getStatus() == 1) {
                    max = response.body().getData().getLastPage();
                    donationData.addAll(response.body().getData().getData());
                    emptyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Donations> call, Throwable t) {
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
