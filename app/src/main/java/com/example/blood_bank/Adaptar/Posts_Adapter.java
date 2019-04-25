package com.example.blood_bank.Adaptar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blood_bank.data.api.ApiService;
import com.example.blood_bank.R;
import com.example.blood_bank.data.local.SharedPreferencesManger;

import com.example.blood_bank.data.model.Posts.PostsData;
import com.example.blood_bank.data.model.login.Client;
import com.example.blood_bank.data.model.posttogglefavourite.PostToggleFavourite;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.blood_bank.data.api.RetrofitClient.getClient;
import static com.example.blood_bank.helper.HelperMethod.onLoadImageFromUrl;

public class Posts_Adapter extends RecyclerView.Adapter<Posts_Adapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<PostsData> postsData = new ArrayList<>();
    private ApiService apiService;
    private Client client;

    public Posts_Adapter(Context context, Activity activity, List<PostsData> postsData) {
        this.context = context;
        this.activity = activity;
        this.postsData = postsData;
        client = SharedPreferencesManger.loadUserData(activity);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.thewaytoprevent_item,
                parent, false);
        apiService = getClient().create(ApiService.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.txtTitleFavoritFragment.setText(postsData.get(position).getTitle());
        onLoadImageFromUrl(holder.ImageViewFavoritFragment, postsData.get(position).getThumbnailFullPath(), context);
    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.ImageViewFavoritFragmentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiService.addfavorit(client.getApiToken(),postsData.get(position).getId()).enqueue(new Callback<PostToggleFavourite>() {
                    @Override
                    public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                        if (response.body().getStatus()==1){

                            if (response.body().getData().getAttached().isEmpty()) {
                                holder.ImageViewFavoritFragmentAdd.setImageResource(R.drawable.blackheart);
                            }else {
                                holder.ImageViewFavoritFragmentAdd.setImageResource(R.drawable.love);

                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<PostToggleFavourite> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitle_favorit_fragment)
        TextView txtTitleFavoritFragment;
        @BindView(R.id.ImageView_favorit_fragment_add)
        ImageView ImageViewFavoritFragmentAdd;
        @BindView(R.id.ImageView_favorit_fragment)
        ImageView ImageViewFavoritFragment;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}

