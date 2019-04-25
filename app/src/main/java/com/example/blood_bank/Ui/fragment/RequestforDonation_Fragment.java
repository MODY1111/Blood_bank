package com.example.blood_bank.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.bloodtyps.BloodTyps;
import com.example.blood_bank.data.model.citys.Cities;
import com.example.blood_bank.data.model.governorates.Governorates;
import com.example.blood_bank.data.model.login.Client;

import java.util.ArrayList;
import java.util.List;

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
public class RequestforDonation_Fragment extends Fragment {


    ApiService apiService;
    @BindView(R.id.Fragment_Request_Dontaion_ed_name)
    EditText FragmentRequestDontaionEdName;
    @BindView(R.id.Fragment_Request_Dontaion_ed_ege)
    EditText FragmentRequestDontaionEdEge;

    @BindView(R.id.Fragment_Request_Dontaion_Spinner)
    EditText FragmentRequestDontaionSpinner;
    @BindView(R.id.Fragment_Request_Dontaion_ed_Elmansuora)
    Spinner FragmentRequestDontaionEdElmansuora;
    @BindView(R.id.Fragment_Request_Dontaion_sp_goverment)
    Spinner FragmentRequestDontaionSpGoverment;
    @BindView(R.id.Fragment_Request_Dontaion_ed_Hospital)
    EditText FragmentRequestDontaionEdHospital;
    @BindView(R.id.Fragment_Request_Dontaion_Tv_titel)
    EditText FragmentRequestDontaionTvTitel;
    @BindView(R.id.Fragment_Request_Dontaion_place)
    ImageView FragmentRequestDontaionPlace;
    @BindView(R.id.Fragment_Request_Dontaion_ed_phone)
    EditText FragmentRequestDontaionEdPhone;
    @BindView(R.id.Fragment_Request_Dontaion_ed_notes)
    EditText FragmentRequestDontaionEdNotes;
    @BindView(R.id.Fragment_Request_Dontaion_Button_sendrequest)
    Button FragmentRequestDontaionButtonSendrequest;
    Unbinder unbinder;
    @BindView(R.id.Fragment_Request_Dontaion_Sp_blood)
    Spinner FragmentRequestDontaionSpBlood;
    private Client client;
    private List<String> GovernoratesTxt = new ArrayList<>();
    private List<Integer> GovernoratesId = new ArrayList<>();
    private List<String> citiesText = new ArrayList<>();
    private List<Integer> citiesId = new ArrayList<>();
    private int city_id = 0;
    private List<String> bloodText = new ArrayList<>();
    private List<Integer> bloodId = new ArrayList<>();
    private int blood_type_id = 0;

    public RequestforDonation_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requestfor_donation_, container, false);

        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        unbinder = ButterKnife.bind(this, view);
        getgoverment();
        getblood();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_Request_Dontaion_Button_sendrequest)
    public void onViewClicked() {
       // sendrequest();
    }

//    public void sendrequest() {
//        String patient_name = FragmentRequestDontaionEdName.getText().toString();
//        String patient_age = FragmentRequestDontaionEdEge.getText().toString();
//        String phone = FragmentRequestDontaionEdPhone.getText().toString();
//        String bags_num = FragmentRequestDontaionSpinner.getText().toString();
//        String notes = FragmentRequestDontaionEdNotes.getText().toString();
//        String hospital_name = FragmentRequestDontaionEdHospital.getText().toString();
//        String hospital_address = FragmentRequestDontaionTvTitel.getText().toString();
//        if (city_id == 0) {
//            return;
//        }
//        String latitude = "31.7655";
//        String longitude = "30.7541";
//        apiService.creatrequest(client.getApiToken(), patient_name, patient_age, bloodId.get(FragmentRequestDontaionEdElmansuora.getSelectedItemPosition())
//                , bags_num, hospital_name, hospital_address, city_id, phone, notes, latitude, longitude
//        ).enqueue(new Callback<Create>() {
//            @Override
//            public void onResponse(Call<Create> call, Response<Create> response) {
//                if (response.body().getStatus() == 1) {
//                    Toast.makeText(getActivity(), "تم إرسال الطلب", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Create> call, Throwable t) {
//                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    public void getgoverment() {
        apiService.getListGovern().enqueue(new Callback<Governorates>() {
            @Override
            public void onResponse(Call<Governorates> call, Response<Governorates> response) {
                if (response.body().getStatus() == 1) {
                    GovernoratesTxt.add("اختر المحافظه");
                    GovernoratesId.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        GovernoratesTxt.add(response.body().getData().get(i).getName());
                        GovernoratesId.add(response.body().getData().get(i).getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spineritem, GovernoratesTxt);
                    FragmentRequestDontaionSpGoverment.setAdapter(adapter);
                    FragmentRequestDontaionSpGoverment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            if (i != 0) {
                                getcities(GovernoratesId.get(i));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Governorates> call, Throwable t) {

            }
        });
    }

    private void getcities(Integer id) {
        apiService.getcity(id).enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {
                if (response.body().getStatus() == 1) {
                    citiesText = new ArrayList<>();
                    citiesId = new ArrayList<>();
                    citiesText.add("اختر المدينه");
                    citiesId.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        citiesText.add(response.body().getData().get(i).getName());
                        citiesId.add(response.body().getData().get(i).getId());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spineritem, citiesText);
                    FragmentRequestDontaionEdElmansuora.setAdapter(adapter);
                    FragmentRequestDontaionEdElmansuora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position != 0) {
                                city_id = (citiesId.get(position));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });

    }

    public void getblood() {
        apiService.getListBloods().enqueue(new Callback<BloodTyps>() {
            @Override
            public void onResponse(Call<BloodTyps> call, Response<BloodTyps> response) {
                if (response.body().getStatus() == 1) {
                    bloodText = new ArrayList<>();
                    bloodId = new ArrayList<>();
                    bloodText.add("اختر فصيله الدم");
                    bloodId.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        bloodText.add(response.body().getData().get(i).getName());
                        bloodId.add(response.body().getData().get(i).getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spineritem, bloodText);
                    FragmentRequestDontaionSpBlood.setAdapter(adapter);
                    FragmentRequestDontaionSpBlood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            if (i != 0) {
                                blood_type_id = (bloodId.get(i));
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BloodTyps> call, Throwable t) {

            }
        });
    }
}
