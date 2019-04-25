package com.example.blood_bank.Adaptar;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.blood_bank.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Check_Box_Adapter extends RecyclerView.Adapter<Check_Box_Adapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<String> names = new ArrayList<>();
    private List<Integer> id = new ArrayList<>();
    private List<Integer> Selected_id = new ArrayList<>();
    public List<Integer> idSelection = new ArrayList<>();


    public Check_Box_Adapter(Context context, FragmentActivity activity, List<String> bloodTypsNames, List<Integer> bloodTypsId) {
        this.context = context;
        this.activity = activity;
        this.names = bloodTypsNames;
        this.id = bloodTypsId;
        this.Selected_id = Selected_id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chek, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(ViewHolder holder, int position) {
        holder.ItemCB.setText(names.get(position));

        if (Selected_id.contains(id.get(position))) {
            holder.ItemCB.setChecked(true);
            idSelection.add(id.get(position));
        } else {
            holder.ItemCB.setChecked(false);
        }

    }

    private void setAction(ViewHolder holder, final int position) {
        holder.ItemCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    idSelection.add(id.get(position));
                } else {
                    for (int i = 0; i < idSelection.size(); i++) {
                        if (idSelection.get(i).equals(id.get(position))) {
                            idSelection.remove(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Item_CB)
        CheckBox ItemCB;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}




