package com.example.devrathrathee.legal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.MatterReceivedBean;

import java.util.ArrayList;
import java.util.List;

public class MatterReceivedAdapter extends RecyclerView.Adapter<MatterReceivedAdapter.MatterReceivedViewHolder> {

    Context context;
    List<MatterReceivedBean.MatterReceived> matterReceivedList = new ArrayList<>();

    public MatterReceivedAdapter(Context context, List<MatterReceivedBean.MatterReceived> matterReceivedList) {
        this.context = context;
        this.matterReceivedList.addAll(matterReceivedList);
    }

    @NonNull
    @Override
    public MatterReceivedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.matter_received_item, viewGroup, false);

        return new MatterReceivedAdapter.MatterReceivedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatterReceivedViewHolder matterReceivedViewHolder, int i) {

        MatterReceivedBean.MatterReceived matterReceived = matterReceivedList.get(i);
        matterReceivedViewHolder.lawyer_tv.setText(matterReceived.getName());
        matterReceivedViewHolder.parties_tv.setText(matterReceived.getParties());
        matterReceivedViewHolder.client_tv.setText(matterReceived.getClient_name());
    }

    @Override
    public int getItemCount() {
        return matterReceivedList.size();
    }


    public static class MatterReceivedViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cardRelativeLayout;
        TextView lawyer_tv, parties_tv, client_tv;

        public MatterReceivedViewHolder(@NonNull View itemView) {
            super(itemView);

            lawyer_tv = itemView.findViewById(R.id.lawyer_tv);
            client_tv = itemView.findViewById(R.id.client_tv);
            parties_tv = itemView.findViewById(R.id.parties_tv);
            cardRelativeLayout = itemView.findViewById(R.id.card_relative_layout);
        }
    }
}
