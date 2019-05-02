package com.example.blood_bank_mo.Ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.Ui.actvity.blood_navigationActivity;
import com.example.blood_bank_mo.data.model.NewPassword.Newpassword;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank_mo.data.api.RetrofitClient.getClient;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResitPasswoard_Fragment extends Fragment {


    @BindView(R.id.bloodbank_Resitpasswoard_ed_pin)
    EditText bloodbankResitpasswoardEdPin;
    @BindView(R.id.bloodbank_Resitpasswoard_ed_code)
    EditText bloodbankResitpasswoardEdCode;
    @BindView(R.id.forgetpasswoard_edittext_confirm)
    EditText forgetpasswoardEdittextConfirm;
    @BindView(R.id.bloodbank_resitpasswoard_butn)
    Button bloodbankResitpasswoardButn;
    Unbinder unbinder;
    private ApiService apiService;
    public String phone;

    public ResitPasswoard_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resit_passwoard_, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        String pin_code = preferences.getString("pin_code", null);
        bloodbankResitpasswoardEdCode.setText(pin_code);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bloodbank_resitpasswoard_butn)
    public void onViewClicked() {
        newPassWord();
    }

    public void newPassWord() {
        String code = bloodbankResitpasswoardEdPin.getText().toString().trim();
        String newpass = bloodbankResitpasswoardEdCode.getText().toString().trim();
        String confirmpass = forgetpasswoardEdittextConfirm.getText().toString().trim();
        if (newpass.isEmpty()) {
            bloodbankResitpasswoardEdCode.setError("code is erorr");
            bloodbankResitpasswoardEdCode.requestFocus();
            return;
        }
        if (code.isEmpty()) {
            bloodbankResitpasswoardEdPin.setError("00");
            bloodbankResitpasswoardEdPin.requestFocus();
            return;

        }
        if (confirmpass.isEmpty()) {
            forgetpasswoardEdittextConfirm.setError("ffff");
            forgetpasswoardEdittextConfirm.requestFocus();
            return;
        }
        apiService.newPassWord(newpass, confirmpass,code ,phone).enqueue(new Callback<Newpassword>() {
            @Override
            public void onResponse(Call<Newpassword> call, Response<Newpassword> response) {
                if (response.body().getStatus()==1) {
                    Toast.makeText(getActivity(), response.body().getMsg() , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), blood_navigationActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Newpassword> call, Throwable t) {

            }
        });

    }
}
