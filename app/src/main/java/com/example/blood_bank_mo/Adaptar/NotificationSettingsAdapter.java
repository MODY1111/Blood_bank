package com.example.blood_bank_mo.Adaptar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.blood_bank_mo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.blood_bank_mo.Ui.fragment.Siting_NavigationFragment.bloodtypsSelectedId;
import static com.example.blood_bank_mo.Ui.fragment.Siting_NavigationFragment.governmentsSelectedIds;

public class NotificationSettingsAdapter extends RecyclerView.Adapter<NotificationSettingsAdapter.ViewHolder> {

    private int type;
    private List<String> Names = new ArrayList<>();
    private List<Integer> Ids = new ArrayList<>();
    private List<String> SelectedId = new ArrayList<>();

    public List<Integer> NewSelectedId = new ArrayList<>();
    private Context context;
    private Activity activity;

    public NotificationSettingsAdapter(Context context, Activity activity, List<String> names, List<Integer> ids
            , List<String> selectedId, int type) {
        Names = names;
        Ids = ids;
        SelectedId = selectedId;
        this.type= type;
        this.context = context;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chek,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        holder.ItemCB.setText(Names.get(position));

        if (type == 0) {
            if (bloodtypsSelectedId.contains(String.valueOf(Ids.get(position)))) {
                holder.ItemCB.setChecked(true);
                NewSelectedId.add(Ids.get(position));
            } else {
                holder.ItemCB.setChecked(false);
            }
        }else {
            if (governmentsSelectedIds.contains(String.valueOf(Ids.get(position)))) {
                holder.ItemCB.setChecked(true);
                NewSelectedId.add(Ids.get(position));
            } else {
                holder.ItemCB.setChecked(false);
            }
        }

    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.ItemCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    NewSelectedId.add(Ids.get(position));
                } else {
                    for (int i = 0; i < SelectedId.size(); i++) {
                        if (NewSelectedId.get(i).equals(Ids.get(position))) {
                            NewSelectedId.remove(i);
                            break;
                        }
                    }
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return Names.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.Item_CB)
        CheckBox ItemCB;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
