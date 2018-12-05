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
import android.widget.Toast;

import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvetResponse.CulvertResponse;
import com.spiderindia.departmentsofhighway.ModelClasses.ModelCulvetResponse.DataItem;
import com.spiderindia.departmentsofhighway.NewActivities.CulvertFormActivity;
import com.spiderindia.departmentsofhighway.R;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CulvertAdapter extends RecyclerView.Adapter<CulvertAdapter.MyCulvertViewHolder> {

    private List<DataItem> dataItemList;
    private Context context;

    public CulvertAdapter(List<DataItem> dataItemList, Context context) {
        this.dataItemList = dataItemList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCulvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.culvert_recycle_list, parent, false);
        return new MyCulvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCulvertViewHolder holder, final int position) {

        final DataItem item =dataItemList.get(position);


        holder.BridgeId.setText(dataItemList.get(position).getCULVERTKEYID());
        holder.recycleTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();

                SharedPreferences preferences = context.getSharedPreferences("Modify", MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("data", "true");
                editor.apply();

                Intent intent = new Intent (context, CulvertFormActivity.class);
                intent.putExtra("item", item);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }

    public class MyCulvertViewHolder extends RecyclerView.ViewHolder{

        private TextView BridgeId;

        private RelativeLayout recycleTouch;

        private MyCulvertViewHolder(View itemView) {
            super(itemView);

            BridgeId = itemView.findViewById(R.id.bridge_recycle_id);
            recycleTouch = itemView.findViewById(R.id.recycleTouch);
        }
    }
}
