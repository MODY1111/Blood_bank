package com.example.blood_bank_mo.Ui.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.data.api.RetrofitClient;
import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.data.local.SharedPreferencesManger;
import com.example.blood_bank_mo.data.model.Posts.Posts;
import com.example.blood_bank_mo.data.model.Setting.Setting;
import com.example.blood_bank_mo.data.model.contact.Contact;
import com.example.blood_bank_mo.data.model.contact.Data;
import com.example.blood_bank_mo.data.model.login.Client;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Call_UsFragment extends Fragment {


    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.Fragment_callus_tv_phone)
    TextView FragmentCallusTvPhone;
    @BindView(R.id.phoneText)
    TextView phoneText;
    @BindView(R.id.call)
    LinearLayout call;
    @BindView(R.id.Fragment_callus_tv_email)
    TextView FragmentCallusTvEmail;
    @BindView(R.id.mailText)
    TextView mailText;
    @BindView(R.id.mmmmmmmmm)
    LinearLayout mmmmmmmmm;
    @BindView(R.id.Fragment_callus_google)
    ImageView FragmentCallusGoogle;
    @BindView(R.id.Fragment_callus_watsap)
    ImageView FragmentCallusWatsap;
    @BindView(R.id.Fragment_callus_insta)
    ImageView FragmentCallusInsta;
    @BindView(R.id.Fragment_callus_youtube)
    ImageView FragmentCallusYoutube;
    @BindView(R.id.Fragment_callus_twiter)
    ImageView FragmentCallusTwiter;
    @BindView(R.id.Fragment_callus_facbook)
    ImageView FragmentCallusFacbook;
    @BindView(R.id.social)
    LinearLayout social;
    @BindView(R.id.contenu)
    TextView contenu;
    @BindView(R.id.Fragment_callus_ed_massege)
    EditText FragmentCallusEdMassege;
    @BindView(R.id.Fragment_callus_ed_details_massege)
    EditText FragmentCallusEdDetailsMassege;
    @BindView(R.id.ma7shy)
    LinearLayout ma7shy;
    @BindView(R.id.Fragment_callus_send)
    Button FragmentCallusSend;
    Unbinder unbinder;
    ApiService apiService;
    private Client client;

    public Call_UsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call__us, container, false);
        unbinder = ButterKnife.bind(this, view);
        client = SharedPreferencesManger.loadUserData(getActivity());
        apiService = RetrofitClient.getClient().create(ApiService.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_callus_google, R.id.Fragment_callus_watsap, R.id.Fragment_callus_insta, R.id.Fragment_callus_youtube, R.id.Fragment_callus_twiter, R.id.Fragment_callus_facbook, R.id.Fragment_callus_send})
    public void onViewClicked(final View view) {
        apiService.getSetting(client.getApiToken()).enqueue(new Callback<Setting>() {
            @Override
            public void onResponse(Call<Setting> call, Response<Setting> response) {
                if (response.body().getStatus() == 1) {
                    phoneText.setText(response.body().getData().getPhone());
                    mailText.setText(response.body().getData().getEmail());
                    switch (view.getId()) {
                        case R.id.Fragment_callus_google:
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getGoogleUrl())));
                            break;
                        case R.id.Fragment_callus_watsap:
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://api.whatsapp.com/send?phone=" + response.body().getData().getWhatsapp() + "&text=" + "")));
                            break;
                        case R.id.Fragment_callus_insta:
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getInstagramUrl())));
                            break;
                        case R.id.Fragment_callus_youtube:
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getYoutubeUrl())));
                            break;
                        case R.id.Fragment_callus_twiter:
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getTwitterUrl())));
                            break;
                        case R.id.Fragment_callus_facbook:
                            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.body().getData().getFacebookUrl())));
                            break;
                        case R.id.Fragment_callus_send:
                            tittle();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Setting> call, Throwable t) {

            }
        });

    }

    public void tittle() {
        String title = FragmentCallusEdMassege.getText().toString();
        if (title.isEmpty()) {
            FragmentCallusEdMassege.setError("code is erorr");
            FragmentCallusEdMassege.requestFocus();
            return;
        }
        String message = FragmentCallusEdDetailsMassege.getText().toString();
        if (message.isEmpty()) {
            FragmentCallusEdDetailsMassege.setError("code is erorr");
            FragmentCallusEdDetailsMassege.requestFocus();
            return;
        }

        apiService.contactUs(client.getApiToken(), title, message).enqueue(new Callback<Contact>() {
            @Override
            public void onResponse(Call<Contact> call, Response<Contact> response) {
                if (response.body().getStatus()==1) {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<Contact> call, Throwable t) {


            }
        });


    }
}

