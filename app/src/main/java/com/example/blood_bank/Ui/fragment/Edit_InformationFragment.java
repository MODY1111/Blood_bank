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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.helper.DateModel;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.bloodtyps.BloodTyps;
import com.example.blood_bank.data.model.citys.Cities;
import com.example.blood_bank.data.model.governorates.Governorates;
import com.example.blood_bank.data.model.login.Client;
import com.example.blood_bank.data.model.login.Login;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank.data.api.RetrofitClient.getClient;
import static com.example.blood_bank.helper.HelperMethod.showCalender;
import static com.example.blood_bank.data.local.SharedPreferencesManger.saveUserData;

/**
 * A simple {@link Fragment} subclass.
 */
public class Edit_InformationFragment extends Fragment {


    @BindView(R.id.editprofile_ed_name)
    EditText editprofileEdName;
    @BindView(R.id.editprofile_ed_email)
    EditText editprofileEdEmail;


    @BindView(R.id.editprofile_Sp_city)
    Spinner editprofileSpCity;
    @BindView(R.id.editprofile_Sp_govrnment)
    Spinner editprofileSpGovrnment;
    @BindView(R.id.editprofile_ed_phone)
    EditText editprofileEdPhone;
    @BindView(R.id.editprofile_ed_passwoard)
    EditText editprofileEdPasswoard;
    @BindView(R.id.editprofile_ed_passwoard_confirm)
    EditText editprofileEdPasswoardConfirm;
    @BindView(R.id.editprofile_ed_btn_edit)
    Button editprofileEdBtnEdit;
    Unbinder unbinder;
    ApiService apiService;
    @BindView(R.id.editprofile_sp_bloodtype)
    Spinner editprofileSpBloodtype;
    @BindView(R.id.editprofile_Tv_date)
    TextView editprofileTvDate;
    @BindView(R.id.editprofile_Tv_datebirth)
    TextView editprofileTvDatebirth;
    private Integer city_id = 0;
    private List<String> GovernoratesTxt = new ArrayList<>();
    private List<Integer> GovernoratesId = new ArrayList<>();
    private List<String> citiesText = new ArrayList<>();
    private List<Integer> citiesId = new ArrayList<>();
    private int blood_type_id =0;


    private List<String> bloodsText = new ArrayList<>();
    private List<Integer> bloodsId = new ArrayList<>();
    private DateModel LastDate, BOD;
    private Client client;

    public Edit_InformationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit__information, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        BOD = new DateModel("01", "01", "1970", "01-01-1970");
        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));
        LastDate = new DateModel(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        client = SharedPreferencesManger.loadUserData(getActivity());

        editprofileEdName.setHint(client.getName());
        editprofileEdEmail.setHint(client.getEmail());
        editprofileEdPhone.setHint(client.getPhone());
        editprofileTvDatebirth.setHint(client.getDonationLastDate());


        getBloodTyp();
        getgoverment();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

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
                    editprofileSpGovrnment.setAdapter(adapter);
                    editprofileSpGovrnment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                    editprofileSpCity.setAdapter(adapter);
                    editprofileSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void getBloodTyp() {
        apiService.getListBloods().enqueue(new Callback<BloodTyps>() {
            @Override
            public void onResponse(Call<BloodTyps> call, Response<BloodTyps> response) {
                if (response.body().getStatus() == 1) {
                    bloodsText.add("اختر فصيلة الدم");
                    bloodsId.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        bloodsText.add(response.body().getData().get(i).getName());
                        bloodsId.add(response.body().getData().get(i).getId());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spineritem, bloodsText);
                    editprofileSpBloodtype.setAdapter(adapter);
                    editprofileSpBloodtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            if (i != 0) {
                                blood_type_id = (bloodsId.get(i));

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


    public void editinformation() {
        String name = editprofileEdName.getText().toString().trim();
        String email = editprofileEdEmail.getText().toString().trim();
        String donation_last_date = editprofileTvDate.getText().toString().trim();
        String birth_date = editprofileTvDatebirth.getText().toString().trim();
        String phone = editprofileEdPhone.getText().toString().trim();
        String pass = editprofileEdPasswoard.getText().toString().trim();
        String confirmpass = editprofileEdPasswoardConfirm.getText().toString().trim();
        if (name.isEmpty()) {
            editprofileEdName.setError("name is required");
            editprofileEdName.requestFocus();
        }
        if (email.isEmpty()) {
            editprofileEdEmail.setError("name is required");
            editprofileEdEmail.requestFocus();
        }

        if (birth_date.isEmpty()) {
            editprofileTvDate.setError("name is required");
            editprofileTvDate.requestFocus();
        }
        if (donation_last_date.isEmpty()) {
            editprofileTvDatebirth.setError("name is required");
            editprofileTvDatebirth.requestFocus();
        }
        if (phone.isEmpty()) {
            editprofileEdPhone.setError("name is required");
            editprofileEdPhone.requestFocus();
        }
        if (pass.isEmpty()) {
            editprofileEdPasswoard.setError("name is required");
            editprofileEdPasswoard.requestFocus();
        }
        if (confirmpass.isEmpty()) {
            editprofileEdPasswoardConfirm.setError("name is required");
            editprofileEdPasswoardConfirm.requestFocus();
        }
        if (city_id == 0) {
            Toast.makeText(getActivity(), "chek your data pleas", Toast.LENGTH_SHORT).show();
            return;
        }
        if (blood_type_id == 0) {
            return;
        }
        apiService.editprofile(name, email, birth_date, city_id, phone, donation_last_date, pass, confirmpass, blood_type_id).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getStatus() == 1) {
                    Client client = response.body().getData().getClient();
                    client.setApiToken(response.body().getData().getApiToken());
                    saveUserData(getActivity(), client);
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });


    }

    @OnClick(R.id.editprofile_ed_btn_edit)
    public void onViewClicked() {
        editinformation();
    }

    @OnClick({R.id.editprofile_Tv_date, R.id.editprofile_Tv_datebirth})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.editprofile_Tv_date:
                showCalender(getActivity(), "اختر اخر تاريخ للتبرع", editprofileTvDate,LastDate );
                break;
            case R.id.editprofile_Tv_datebirth:
                showCalender(getActivity(), "اختر تاريخ ميلادك\n", editprofileTvDatebirth,BOD );

                break;
        }
    }
}