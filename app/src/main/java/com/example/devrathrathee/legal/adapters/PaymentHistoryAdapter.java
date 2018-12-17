package com.example.devrathrathee.legal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.PaymentHistoryViewHolder> {

    List<ProfessionalFeeBean.PaymentDetail.PaymentDate> paymentDateList = new ArrayList<>();
    List<ProfessionalFeeBean.PaymentDetail.PaymentMode> paymentModeList = new ArrayList<>();
    List<ProfessionalFeeBean.PaymentDetail.PaymentChequeNumber> paymentChequeNumberList = new ArrayList<>();
    List<ProfessionalFeeBean.PaymentDetail.PaymentAmount> paymentAmountList = new ArrayList<>();
    List<ProfessionalFeeBean.PaymentDetail.PaymentRemark> paymentRemarkList = new ArrayList<>();

    public PaymentHistoryAdapter(List<ProfessionalFeeBean.PaymentDetail.PaymentDate> paymentDateList, List<ProfessionalFeeBean.PaymentDetail.PaymentMode> paymentModeList,
                                 List<ProfessionalFeeBean.PaymentDetail.PaymentChequeNumber> paymentChequeNumberList, List<ProfessionalFeeBean.PaymentDetail.PaymentAmount> paymentAmountList,
                                 List<ProfessionalFeeBean.PaymentDetail.PaymentRemark> paymentRemarkList) {
        this.paymentDateList.addAll(paymentDateList);
        this.paymentModeList.addAll(paymentModeList);
        this.paymentChequeNumberList.addAll(paymentChequeNumberList);
        this.paymentAmountList.addAll(paymentAmountList);
        this.paymentRemarkList.addAll(paymentRemarkList);
    }

    @NonNull
    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.payment_history_row, viewGroup, false);
        return new PaymentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryViewHolder paymentHistoryViewHolder, int i) {
        paymentHistoryViewHolder.remarks_tv.setText(paymentRemarkList.get(i).getRemark());
        paymentHistoryViewHolder.payment_date_tv.setText(paymentDateList.get(i).getDate());
        if (paymentModeList.get(i).getMode().equals("Cash")) {
            paymentHistoryViewHolder.mode_tv.setText(paymentModeList.get(i).getMode());
        } else {
            paymentHistoryViewHolder.mode_tv.setText(paymentModeList.get(i).getMode() + " " + paymentChequeNumberList.get(i).getC_number());
        }
        paymentHistoryViewHolder.payment_amount_tv.setText(paymentAmountList.get(i).getAmount());
    }

    @Override
    public int getItemCount() {
        return paymentAmountList.size();
    }

    public static class PaymentHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView payment_date_tv, mode_tv, payment_amount_tv, remarks_tv;

        public PaymentHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            payment_date_tv = itemView.findViewById(R.id.payment_date_tv);
            mode_tv = itemView.findViewById(R.id.mode_tv);
            payment_amount_tv = itemView.findViewById(R.id.payment_amount_tv);
            remarks_tv = itemView.findViewById(R.id.remarks_tv);
        }
    }
}
