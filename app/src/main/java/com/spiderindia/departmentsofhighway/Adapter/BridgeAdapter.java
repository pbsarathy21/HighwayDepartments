package com.spiderindia.departmentsofhighway.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spiderindia.departmentsofhighway.ModelClasses.ModelBridgeResponse.DataItem;
import com.spiderindia.departmentsofhighway.NewActivities.NewBridgeForm;
import com.spiderindia.departmentsofhighway.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BridgeAdapter extends RecyclerView.Adapter<BridgeAdapter.BridgeViewHolder> {

    private List<DataItem> dataItemList;
    private Context context;

    public BridgeAdapter(List<DataItem> dataItemList, Context context) {
        this.dataItemList = dataItemList;
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BridgeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bridge_recycle_list, parent, false);
        return new BridgeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BridgeViewHolder holder, int position) {

        final DataItem dataItem = dataItemList.get(position);

        holder.BridgeId.setText(dataItemList.get(position).getBRIDGEKEYID());
        holder.BridgeName.setText(dataItemList.get(position).getBRIDGENAME());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = context.getSharedPreferences("Modify", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("data", "true");
                editor.apply();

                Intent intent = new Intent(context, NewBridgeForm.class);
                intent.putExtra("dataItem", dataItem);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }



    public class BridgeViewHolder extends RecyclerView.ViewHolder {

        private TextView BridgeId, BridgeName;
        private RelativeLayout relativeLayout;

        private BridgeViewHolder(View itemView) {
            super(itemView);

            BridgeId = itemView.findViewById(R.id.bridge_recycle_id);
            BridgeName = itemView.findViewById(R.id.bridge_recycle_name);
            relativeLayout = itemView.findViewById(R.id.recycle_bridge_touch);
        }
    }
}
