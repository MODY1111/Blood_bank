package com.example.blood_bank_mo.Ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.Ui.actvity.blood_navigationActivity;
import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.data.local.SharedPreferencesManger;
import com.example.blood_bank_mo.data.model.login.Client;
import com.example.blood_bank_mo.data.model.login.Login;
import com.example.blood_bank_mo.helper.HelperMethod;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank_mo.data.api.RetrofitClient.getClient;
import static com.example.blood_bank_mo.data.local.SharedPreferencesManger.saveUserData;
import static com.example.blood_bank_mo.helper.HelperMethod.disappearKeypad;


public class LoginFragment extends Fragment {


    @BindView(R.id.bloodbank_login_image)
    ImageView bloodbankLoginImage;
    @BindView(R.id.bloodbank_login_edittext_phone)
    EditText bloodbankLoginEdittextPhone;
    @BindView(R.id.bloodbank_login_edittext_password)
    EditText bloodbankLoginEdittextPassword;
    @BindView(R.id.Fragment_Text_Forgetpassword)
    TextView FragmentTextForgetpassword;
    @BindView(R.id.relativ_chek)
    RelativeLayout relativChek;
    @BindView(R.id.Loginactvity_Button_login)
    Button LoginactvityButtonLogin;
    @BindView(R.id.Loginactvity_Button_Creat_new_Acount)
    Button LoginactvityButtonCreatNewAcount;
    Unbinder unbinder;
    @BindView(R.id.login_frame)
    FrameLayout loginFrame;
    @BindView(R.id.loginfram_kaypoard)
    RelativeLayout loginframKaypoard;
    @BindView(R.id.Login_Fragment_Cb)
    AppCompatCheckBox LoginFragmentCb;
    private ApiService apiService;
    private Context context;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        apiService = getClient().create(ApiService.class);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_Text_Forgetpassword, R.id.Loginactvity_Button_login, R.id.Loginactvity_Button_Creat_new_Acount})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_Text_Forgetpassword:
                Forget_passwoardFragment Forgetpassword_fram = new Forget_passwoardFragment();
                HelperMethod.replaceFragment(getFragmentManager(), R.id.bloodbank_User_fram, Forgetpassword_fram);
                break;
            case R.id.Loginactvity_Button_login:
                userLogin();
                break;
            case R.id.Loginactvity_Button_Creat_new_Acount:
                CreatnewacountFragment creatnewacount = new CreatnewacountFragment();
                HelperMethod.replaceFragment(getFragmentManager(), R.id.bloodbank_User_fram, creatnewacount);
                break;
        }
    }

    public void userLogin() {
        String phone = bloodbankLoginEdittextPhone.getText().toString().trim();
        String passwoard = bloodbankLoginEdittextPassword.getText().toString().trim();
        if (phone.equals("")) {

        }
        if (passwoard.equals("")) {

        }

        apiService.userLogin(phone, passwoard).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.body().getStatus() == 1) {
                    Client client = response.body().getData().getClient();
                    client.setApiToken(response.body().getData().getApiToken());
                    saveUserData(getActivity(), client);
                    SharedPreferencesManger.SaveData(getActivity(), SharedPreferencesManger.REMEMBER, LoginFragmentCb.isChecked());
                    Intent intent = new Intent(getActivity(), blood_navigationActivity.class);
                    startActivity(intent);
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(getActivity(), "حدث خطأ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @OnClick(R.id.loginfram_kaypoard)
    public void onViewClicked() {
        disappearKeypad(getActivity(), getView());
    }
}


