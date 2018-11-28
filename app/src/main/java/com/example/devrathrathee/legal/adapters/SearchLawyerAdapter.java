package com.example.devrathrathee.legal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.SearchLawyerBean;
import com.example.devrathrathee.legal.views.SearchLawyerActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchLawyerAdapter extends RecyclerView.Adapter<SearchLawyerAdapter.SearchLawyerViewHolder> {

    Context context;
    List<SearchLawyerBean.LawyerDetail> lawyerDetailList = new ArrayList<>();

    public SearchLawyerAdapter(Context context, List<SearchLawyerBean.LawyerDetail> lawyerDetailList) {
        this.lawyerDetailList.addAll(lawyerDetailList);
        this.context = context;
    }

    @NonNull
    @Override
    public SearchLawyerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_lawyer_item, viewGroup, false);
        return new SearchLawyerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchLawyerViewHolder searchLawyerViewHolder, int i) {

        SearchLawyerBean.LawyerDetail lawyerAccountDetail = lawyerDetailList.get(i);
        searchLawyerViewHolder.court_tv.setText(lawyerAccountDetail.getCourt_name());
        searchLawyerViewHolder.lawyer_tv.setText(lawyerAccountDetail.getName());
        searchLawyerViewHolder.phone_tv.setText(lawyerAccountDetail.getPhone());
        searchLawyerViewHolder.email_tv.setText(lawyerAccountDetail.getEmail());

        searchLawyerViewHolder.send_iv.setTag(i);
        searchLawyerViewHolder.send_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) searchLawyerViewHolder.send_iv.getTag();
                ((SearchLawyerActivity) context).transferCase(lawyerDetailList.get(position).getSend_lawyer_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lawyerDetailList.size();
    }

    public static class SearchLawyerViewHolder extends RecyclerView.ViewHolder {

        TextView court_tv, lawyer_tv, email_tv, phone_tv;
        RelativeLayout card_relative_layout;
        ImageView send_iv;

        public SearchLawyerViewHolder(@NonNull View itemView) {
            super(itemView);

            card_relative_layout = itemView.findViewById(R.id.card_relative_layout);
            court_tv = itemView.findViewById(R.id.court_name_tv);
            lawyer_tv = itemView.findViewById(R.id.lawyer_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            phone_tv = itemView.findViewById(R.id.phone_tv);
            send_iv = itemView.findViewById(R.id.send_iv);
        }
    }
}
