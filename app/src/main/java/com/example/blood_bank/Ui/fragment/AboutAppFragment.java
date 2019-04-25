package com.example.blood_bank.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.Setting.Setting;
import com.example.blood_bank.data.model.login.Client;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank.data.api.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutAppFragment extends Fragment {

    @BindView(R.id.bloodbank_aboutapp_fragment_tv)
    TextView bloodbankAboutappFragmentTv;
    Unbinder unbinder;
    private ApiService apiService;
    private Client client;

    public AboutAppFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        aboutapp();
        return view;
    }

    public void aboutapp() {


        apiService.getSetting(client.getApiToken()).enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                if (response.body().getStatus() == 1) {
                    bloodbankAboutappFragmentTv.setText(response.body().getData().getAboutApp());
                }
            }


            @Override
            public void onFailure(Call<Setting> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
