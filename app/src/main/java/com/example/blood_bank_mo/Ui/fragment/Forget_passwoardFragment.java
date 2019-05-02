package com.example.blood_bank_mo.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.helper.HelperMethod;
import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.data.model.restpasswoard.ResetPassword;

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
public class Forget_passwoardFragment extends Fragment {


    @BindView(R.id.bloodbank_forgetpassword_image)
    ImageView bloodbankForgetpasswordImage;
    @BindView(R.id.bloodbank_forgetpasswoard_edittextphone)
    EditText bloodbankForgetpasswoardEdittextphone;
    @BindView(R.id.bloodbank_forgetpasswoard_butn_send)
    Button bloodbankForgetpasswoardButnSend;
    Unbinder unbinder;
    private ApiService apiService;
    public Forget_passwoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_passwoard, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.bloodbank_forgetpasswoard_butn_send)
    public void onViewClicked() {
        reset();

    }
    public  void   reset(){
        final String phone=bloodbankForgetpasswoardEdittextphone.getText().toString().trim();
        if (phone.isEmpty()){

        }
        apiService.resetpassword(phone).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                if (response.body().getStatus()==1){
                    ResitPasswoard_Fragment resitPasswoard=new ResitPasswoard_Fragment();
                    resitPasswoard.phone = phone;
                    HelperMethod.replaceFragment(getFragmentManager(),R.id.bloodbank_User_fram,resitPasswoard);
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });
    }

}
