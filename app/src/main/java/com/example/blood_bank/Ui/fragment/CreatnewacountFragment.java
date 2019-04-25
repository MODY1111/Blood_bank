package com.example.blood_bank.Ui.fragment;

import android.content.Intent;
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
import com.example.blood_bank.Ui.actvity.blood_navigationActivity;

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

public class CreatnewacountFragment extends Fragment {
    @BindView(R.id.Creat_new_acount_EditText_name_Fragment)
    EditText CreatNewAcountEditTextNameFragment;
    @BindView(R.id.Creat_new_acount_EditText_email_Fragment)
    EditText CreatNewAcountEditTextEmailFragment;
    @BindView(R.id.Creat_new_acount_sp_kindblood_Fragment)
    Spinner CreatNewAcountSpKindbloodFragment;
    @BindView(R.id.Creat_new_acount_Text_Date_birth_Fragment)
    TextView CreatNewAcountTextDateBirthFragment;
    @BindView(R.id.Creat_new_acount_Spinner_Governorate_Fragment)
    Spinner CreatNewAcountSpinnerGovernorateFragment;
    @BindView(R.id.Creat_new_acount_Spinner_City_Fragment)
    Spinner CreatNewAcountSpinnerCityFragment;
    @BindView(R.id.Creat_new_acount_EditText_Phone_Fragment)
    EditText CreatNewAcountEditTextPhoneFragment;
    @BindView(R.id.Creat_new_acount_EditText_password_Fragment)
    EditText CreatNewAcountEditTextPasswordFragment;
    @BindView(R.id.Creat_new_acount_EditText_ConfirmPassword_Fragment)
    EditText CreatNewAcountEditTextConfirmPasswordFragment;
    @BindView(R.id.Creat_new_acount_Button_Fragment)
    Button CreatNewAcountButtonFragment;
    Unbinder unbinder;
    @BindView(R.id.Creat_new_acount_Text_Lastdate_Fragment)
    TextView CreatNewAcountTextLastdateFragment;
    private ApiService apiService;
    private Integer city_id = 0;    private List<String> GovernoratesTxt = new ArrayList<>();
    private List<Integer> GovernoratesId = new ArrayList<>();
    private List<String> citiesText = new ArrayList<>();
    private List<Integer> citiesId = new ArrayList<>();
    private List<String> bloodsText = new ArrayList<>();
    private List<Integer> bloodsId = new ArrayList<>();
    private int blood_type_id = 0;
    private DateModel LastDate, BOD;

    public CreatnewacountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_account, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        BOD = new DateModel("01", "01", "1970", "01-01-1970");
        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));
        LastDate = new DateModel(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        getgoverment();
        getBloodTyp();
        return view;
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
                    CreatNewAcountSpinnerGovernorateFragment.setAdapter(adapter);
                    CreatNewAcountSpinnerGovernorateFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                    CreatNewAcountSpinnerCityFragment.setAdapter(adapter);
                    CreatNewAcountSpinnerCityFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        bloodsId.add(Integer.valueOf(response.body().getData().get(i).getId()));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spineritem, bloodsText);
                    CreatNewAcountSpKindbloodFragment.setAdapter(adapter);
                    CreatNewAcountSpKindbloodFragment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @OnClick({R.id.Creat_new_acount_Text_Date_birth_Fragment, R.id.Creat_new_acount_Text_Lastdate_Fragment,R.id.Creat_new_acount_Button_Fragment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Creat_new_acount_Text_Date_birth_Fragment:
                showCalender(getActivity(), "اختر تاريخ ميلادك", CreatNewAcountTextDateBirthFragment, BOD);
                break;
            case R.id.Creat_new_acount_Text_Lastdate_Fragment:
                showCalender(getActivity(), "اختر اخر تاريخ للتبرع", CreatNewAcountTextLastdateFragment, LastDate);

                break;
            case R.id.Creat_new_acount_Button_Fragment:
                newacount();
                break;
        }
    }

    public void newacount() {
        String name = CreatNewAcountEditTextNameFragment.getText().toString().trim();
        String phone = CreatNewAcountEditTextPhoneFragment.getText().toString().trim();
        String email = CreatNewAcountEditTextEmailFragment.getText().toString().trim();
        String password = CreatNewAcountEditTextPasswordFragment.getText().toString().trim();
        String password_confirmation = CreatNewAcountEditTextConfirmPasswordFragment.getText().toString().trim();
        String birth_date = CreatNewAcountTextDateBirthFragment.getText().toString().trim();
        String donation_last_date = CreatNewAcountTextLastdateFragment.getText().toString().trim();
        if (name.isEmpty()) {
            CreatNewAcountEditTextNameFragment.setError("name is required");
            CreatNewAcountEditTextNameFragment.requestFocus();
            return;
        }
        if (phone.isEmpty()){
            CreatNewAcountEditTextPhoneFragment.setError("phone is required");
            CreatNewAcountEditTextPhoneFragment.requestFocus();
            return;
        }
        if (email.isEmpty()){
            CreatNewAcountEditTextPasswordFragment.setError("email is required");
            CreatNewAcountEditTextPasswordFragment.requestFocus();
            return;
        }
        if (password.isEmpty()){
            CreatNewAcountEditTextPasswordFragment.setError("passwoard is required");
            CreatNewAcountEditTextPasswordFragment.requestFocus();
            return;
        }
        if (password_confirmation.isEmpty()){
            CreatNewAcountEditTextConfirmPasswordFragment.setError("confirm pass is required");
            CreatNewAcountEditTextConfirmPasswordFragment.requestFocus();
            return;
        }
        if (birth_date.isEmpty()){
            CreatNewAcountTextDateBirthFragment.setError("is empty");
            CreatNewAcountTextDateBirthFragment.requestFocus();
            return;
        }
        if (donation_last_date.isEmpty()){
            CreatNewAcountTextLastdateFragment.setError("is empty");
            CreatNewAcountTextLastdateFragment.requestFocus();
            return;
        }
        if (city_id == 0) {
            Toast.makeText(getActivity(), "chek your data pleas", Toast.LENGTH_SHORT).show();
            return;
        }
        if (blood_type_id == 0) {
            return;
        }
        apiService.onRegister(name, email, birth_date, city_id, phone, donation_last_date, password, password_confirmation, blood_type_id).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getStatus()==1) {
                    Client client = response.body().getData().getClient();
                    client.setApiToken(response.body().getData().getApiToken());
                    saveUserData(getActivity(), client);
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), blood_navigationActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {

            }
        });


    }
}
