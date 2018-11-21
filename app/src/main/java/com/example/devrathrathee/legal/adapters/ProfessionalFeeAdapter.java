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
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.views.MatterDetailsActivity;
import com.example.devrathrathee.legal.views.ProfessionFeeDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ProfessionalFeeAdapter extends RecyclerView.Adapter<ProfessionalFeeAdapter.ProfFeeViewHolder> {

    Context context;
    List<ProfessionalFeeBean.PaymentDetail> paymentDetailList = new ArrayList<>();

    public ProfessionalFeeAdapter(Context context, List<ProfessionalFeeBean.PaymentDetail> paymentDetails) {
        this.context = context;
        this.paymentDetailList.addAll(paymentDetails);
    }

    @NonNull
    @Override
    public ProfFeeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.prof_fee_item, viewGroup, false);
        return new ProfFeeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull ProfFeeViewHolder profFeeViewHolder, int i) {

        ProfessionalFeeBean.PaymentDetail paymentDetail = paymentDetailList.get(i);

        profFeeViewHolder.client_tv.setText(paymentDetail.getClient_name());
        profFeeViewHolder.court_tv.setText(paymentDetail.getCourt_name());
        profFeeViewHolder.payment_status_tv.setText(paymentDetail.getPaymt_status());

        profFeeViewHolder.card_relative_layout.setTag(i);
        profFeeViewHolder.card_relative_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) profFeeViewHolder.card_relative_layout.getTag();
                Intent intent = new Intent(context, ProfessionFeeDetailActivity.class);
                intent.putExtra(Constants.INTENT_PAYMENT_DETAIL, paymentDetailList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentDetailList.size();
    }

    public static class ProfFeeViewHolder extends RecyclerView.ViewHolder {

        TextView client_tv, court_tv, payment_status_tv;
        RelativeLayout card_relative_layout;

        public ProfFeeViewHolder(@NonNull View itemView) {
            super(itemView);

            client_tv = itemView.findViewById(R.id.client_tv);
            court_tv = itemView.findViewById(R.id.court_tv);
            payment_status_tv = itemView.findViewById(R.id.payment_status_tv);
            card_relative_layout = itemView.findViewById(R.id.card_relative_layout);
        }
    }
}
