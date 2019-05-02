package com.example.blood_bank_mo.Ui.fragment.Artical_dontaion;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.blood_bank_mo.Adaptar.Posts_Adapter;
import com.example.blood_bank_mo.R;
import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.data.local.OnEndless;
import com.example.blood_bank_mo.data.local.SharedPreferencesManger;
import com.example.blood_bank_mo.data.model.Posts.Posts;
import com.example.blood_bank_mo.data.model.Posts.PostsData;
import com.example.blood_bank_mo.data.model.categories.CateGories;
import com.example.blood_bank_mo.data.model.login.Client;
import com.example.blood_bank_mo.data.model.postsfilter.PostsFilter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank_mo.data.api.RetrofitClient.getClient;
import static com.example.blood_bank_mo.helper.HelperMethod.disappearKeypad;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dontaion_ArticalsFragment extends Fragment {

    @BindView(R.id.Favorit_recycler_fragment)
    RecyclerView FavoritRecyclerFragment;
    Unbinder unbinder;
    @BindView(R.id.bloodbank_dontaion_artical_ed_)
    EditText bloodbankDontaionArticalEdSearch;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.bloodbank_dontaion_artical_search_image)
    ImageView bloodbankDontaionArticalSearchImage;
    @BindView(R.id.navigation_keypoard)
    RelativeLayout navigationKeypoard;

    private ApiService apiService;
    private Posts_Adapter posts_adapter;
    private List<PostsData> postsData = new ArrayList<>();
    private int max;
    private Client client;
    private List<String> categorisText = new ArrayList<>();
    private List<Integer> categorisId = new ArrayList<>();
    private int category_id = 0;

    public Dontaion_ArticalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dontaion__articals, container, false);
        unbinder = ButterKnife.bind(this, view);

        apiService = getClient().create(ApiService.class);
        client = SharedPreferencesManger.loadUserData(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        FavoritRecyclerFragment.setLayoutManager(linearLayoutManager);
        OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    getposts(current_page);


                }
            }
        };
        FavoritRecyclerFragment.addOnScrollListener(onEndless);
        posts_adapter = new Posts_Adapter(getActivity(), getActivity(), postsData);
        FavoritRecyclerFragment.setAdapter(posts_adapter);
        getposts(1);
        getcategoris();

        return view;

    }

    private void getposts(int page) {
        apiService.posts(client.getApiToken(), page).enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                if (response.body().getStatus() == 1) {
                    max = response.body().getData().getLastPage();
                    postsData.addAll(response.body().getData().getData());
                    posts_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Posts> call, Throwable t) {


            }
        });


    }

    private void filterposts(int page) {
        String keyword = bloodbankDontaionArticalEdSearch.getText().toString().trim();
        if (keyword.equals("")) {
        }
        apiService.getfilter(client.getApiToken(), page, keyword, getId()).enqueue(new Callback<PostsFilter>() {
            @Override
            public void onResponse(Call<PostsFilter> call, Response<PostsFilter> response) {
                if (response.body().getStatus() == 1) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    FavoritRecyclerFragment.setLayoutManager(linearLayoutManager);
                    OnEndless onEndless = new OnEndless(linearLayoutManager, 1) {
                        @Override
                        public void onLoadMore(int current_page) {
                            if (current_page <= max) {
                                filterposts(current_page);


                            }
                        }
                    };
                    max = response.body().getData().getLastPage();
                    FavoritRecyclerFragment.addOnScrollListener(onEndless);
                    posts_adapter = new Posts_Adapter(getActivity(), getActivity(), postsData);
                    FavoritRecyclerFragment.setAdapter(posts_adapter);

                }
            }

            @Override
            public void onFailure(Call<PostsFilter> call, Throwable t) {

            }
        });

    }


    public void getcategoris() {
        apiService.getListcategoris().enqueue(new Callback<CateGories>() {
            @Override
            public void onResponse(Call<CateGories> call, Response<CateGories> response) {
                if (response.body().getStatus() == 1) {
                    categorisText.add("الفئات");
                    categorisId.add(0);
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        categorisText.add(response.body().getData().get(i).getName());
                        categorisId.add(Integer.valueOf(response.body().getData().get(i).getId()));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spineritem, categorisText);
                    spinner2.setAdapter(adapter);
                    spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                            if (i != 0) {
                                category_id = (categorisId.get(i));

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CateGories> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.bloodbank_dontaion_artical_search_image, R.id.navigation_keypoard})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bloodbank_dontaion_artical_search_image:
                filterposts(1);
                break;
            case R.id.navigation_keypoard:
                disappearKeypad(getActivity(),getView());
                break;
        }
    }
}