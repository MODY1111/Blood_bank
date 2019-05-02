package com.example.blood_bank_mo.Adaptar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blood_bank_mo.data.api.ApiService;
import com.example.blood_bank_mo.R;

import com.example.blood_bank_mo.data.model.login.Client;
import com.example.blood_bank_mo.data.model.notifications.NotificationData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Alirts_Adapter extends RecyclerView.Adapter<Alirts_Adapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<NotificationData> notificationDataList = new ArrayList<>();
    private ApiService apiService;
    private Client client;

    public Alirts_Adapter(Context context, Activity activity, List<NotificationData> notificationDataList) {
        this.context = context;
        this.activity = activity;
        this.notificationDataList = notificationDataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alirtsitem,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.AlertsTvItem.setText(notificationDataList.get(position).getTitle());
        holder.AlertsDateItem.setText(notificationDataList.get(position).getCreatedAt());
    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notificationDataList.size() ;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Context context;
        @BindView(R.id.notificatoin)
        ImageView notificatoin;
        @BindView(R.id.Alerts_tv_item)
        TextView AlertsTvItem;
        @BindView(R.id.Alerts_date_item)
        TextView AlertsDateItem;
        @BindView(R.id.Alerts_clock_item)
        ImageView AlertsClockItem;

        private View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}



