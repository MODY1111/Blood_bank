package com.example.blood_bank.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.blood_bank.R;
import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.donationDetals.DonationDetails;
import com.example.blood_bank.data.model.login.Client;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank.data.api.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class Information_DontaionFragment extends Fragment {

    @BindView(R.id.Fragment_Requestdontioninformation_name)
    TextView FragmentRequestdontioninformationName;
    @BindView(R.id.Fragment_Requestdontioninformation_ege)
    TextView FragmentRequestdontioninformationEge;
    @BindView(R.id.Fragment_Requestdontioninformation_blood)
    TextView FragmentRequestdontioninformationBlood;
    @BindView(R.id.Fragment_Requestdontioninformation_number)
    TextView FragmentRequestdontioninformationNumber;
    @BindView(R.id.Fragment_Requestdontioninformation_Hospital)
    TextView FragmentRequestdontioninformationHospital;
    @BindView(R.id.Fragment_Requestdontioninformation_Hospital_titel)
    TextView FragmentRequestdontioninformationHospitalTitel;
    @BindView(R.id.Fragment_Requestdontioninformation_Phone)
    TextView FragmentRequestdontioninformationPhone;
    @BindView(R.id.Fragment_Requestdontioninformation_details)
    TextView FragmentRequestdontioninformationDetails;
    @BindView(R.id.Fragment_Requestdontioninformation_Text_details)
    TextView FragmentRequestdontioninformationTextDetails;
    @BindView(R.id.Fragment_Requestdontioninformation_Button_Call)
    Button FragmentRequestdontioninformationButtonCall;
    Unbinder unbinder;


    private ApiService apiService;
    private Client client;
    public int id;
    MapView mapView;
    MapView map;
    private SupportMapFragment mapFragment;

    public Information_DontaionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_information__dontaion, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        FragmentActivity fragmentActivity = getActivity();
        mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.information_dontaion_map);

        dontaion_request();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_Requestdontioninformation_Button_Call)
    public void onViewClicked() {
    }

    public void dontaion_request() {
        apiService.dontaion_request(client.getApiToken(), id).enqueue(new Callback<DonationDetails>() {
            @Override
            public void onResponse(Call<DonationDetails> call, final Response<DonationDetails> response) {
                if (response.body().getStatus() == 1) {
                    FragmentRequestdontioninformationName.setText(response.body().getData().getPatientName());
                    FragmentRequestdontioninformationEge.setText(response.body().getData().getPatientAge());
                    FragmentRequestdontioninformationBlood.setText(response.body().getData().getBagsNum());
                    FragmentRequestdontioninformationNumber.setText(response.body().getData().getHospitalName());
                    FragmentRequestdontioninformationHospital.setText(response.body().getData().getPhone());
                    FragmentRequestdontioninformationHospitalTitel.setText(response.body().getData().getHospitalAddress());

                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng sydney = new LatLng(response.body().getData().getLatitude(), response.body().getData().getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(sydney).title(""));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<DonationDetails> call, Throwable t) {

            }
        });
    }
}
