package com.example.devrathrathee.legal.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.beans.LegalQueryBean;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.views.CounselDetailsActivity;
import com.example.devrathrathee.legal.views.QueryDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class LegalQueryAdapter extends RecyclerView.Adapter<LegalQueryAdapter.LegalQueryViewHolder> {

    Context context;
    List<LegalQueryBean.ManageQueryBean> manageQueryBeanArrayList = new ArrayList<>();

    public LegalQueryAdapter(Context context, List<LegalQueryBean.ManageQueryBean> manageQueryBeanArrayList) {
        this.context = context;
        this.manageQueryBeanArrayList.addAll(manageQueryBeanArrayList);
    }


    @NonNull
    @Override
    public LegalQueryAdapter.LegalQueryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.legal_query_item, viewGroup, false);

        return new LegalQueryAdapter.LegalQueryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final LegalQueryAdapter.LegalQueryViewHolder legalQueryViewHolder, int i) {

        LegalQueryBean.ManageQueryBean manageQueryBean = manageQueryBeanArrayList.get(i);
        legalQueryViewHolder.customer_name_tv.setText(manageQueryBean.getCust_name());
        legalQueryViewHolder.status_tv.setText(manageQueryBean.getQuote_status());
        legalQueryViewHolder.quote_type_tv.setText(manageQueryBean.getQuote_type());
        legalQueryViewHolder.cardRelativeLayout.setTag(i);
        legalQueryViewHolder.cardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) legalQueryViewHolder.cardRelativeLayout.getTag();

                Intent intent = new Intent(context, QueryDetailsActivity.class);
                intent.putExtra(Constants.INTENT_LEGAL_QUERY, manageQueryBeanArrayList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return manageQueryBeanArrayList.size();
    }

    public static class LegalQueryViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cardRelativeLayout;
        TextView customer_name_tv, quote_type_tv, status_tv;

        public LegalQueryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRelativeLayout = itemView.findViewById(R.id.card_relative_layout);
            customer_name_tv = itemView.findViewById(R.id.customer_name_tv);
            quote_type_tv = itemView.findViewById(R.id.quote_type_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
        }
    }
}
