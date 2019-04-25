package com.example.blood_bank.Ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blood_bank.Adaptar.Favorit_Adapter;
import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.OnEndless;
import com.example.blood_bank.data.local.SharedPreferencesManger;
import com.example.blood_bank.data.model.Posts.Posts;
import com.example.blood_bank.data.model.Posts.PostsData;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Favorit_Fragment extends Fragment {

    @BindView(R.id.bloodbank_Favorit_fragment_rc_fav)
    RecyclerView bloodbankFavoritFragmentRcFav;
    Unbinder unbinder;
    private Favorit_Adapter favorit_adapter;
    private ApiService apiService;
    private List<PostsData> postsData = new ArrayList<>();
    private int max;
    private Client client;
    private List<String> categorisText = new ArrayList<>();
    private List<Integer> categorisId = new ArrayList<>();
    private int category_id = 0;

    public Favorit_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_favorit_, container, false);
        unbinder = ButterKnife.bind(this, view);
        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        bloodbankFavoritFragmentRcFav.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    getposts(current_page, view);


                }
            }
        };
        bloodbankFavoritFragmentRcFav.addOnScrollListener(onEndless);
        favorit_adapter = new Favorit_Adapter(getActivity(), getActivity(), postsData);
        bloodbankFavoritFragmentRcFav.setAdapter(favorit_adapter);
        getposts(1, view);


        return view;

    }

    private void getposts(int page, Object view) {
        apiService.posts(client.getApiToken(), page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.body().getStatus() == 1) {
                    max = response.body().getData().getLastPage();
                    postsData.addAll(response.body().getData().getData());
                    favorit_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {


            }
        });



    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

