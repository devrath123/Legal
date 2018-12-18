package com.example.devrathrathee.legal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.SearchCounselBean;
import com.example.devrathrathee.legal.views.SearchCounsellerActivity;
import com.example.devrathrathee.legal.views.SearchLawyerActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchCounselAdapter extends RecyclerView.Adapter<SearchCounselAdapter.SearchCounselViewHolder> {

    Context context;
    List<SearchCounselBean.CounselResult> counselResultList = new ArrayList<>();

    public SearchCounselAdapter(Context context, List<SearchCounselBean.CounselResult> counselResultList) {
        this.context = context;
        this.counselResultList.addAll(counselResultList);
    }

    @NonNull
    @Override
    public SearchCounselViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.search_counsel_row, viewGroup, false);
        return new SearchCounselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchCounselViewHolder searchCounselViewHolder, int i) {
        searchCounselViewHolder.counsel_name_tv.setText(counselResultList.get(i).getCounsel_name());
        searchCounselViewHolder.designation_tv.setText(counselResultList.get(i).getCounsel_designation());
        searchCounselViewHolder.phone_tv.setText(counselResultList.get(i).getCounsel_phone());
        searchCounselViewHolder.email_tv.setText(counselResultList.get(i).getCounsel_email());
        searchCounselViewHolder.practice_area_tv.setText(counselResultList.get(i).getCounsel_areaofpractice());

        searchCounselViewHolder.send_iv.setTag(i);
        searchCounselViewHolder.send_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) searchCounselViewHolder.send_iv.getTag();
                ((SearchCounsellerActivity) context).transferCase(counselResultList.get(position).getCounsel_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return counselResultList.size();
    }

    public static class SearchCounselViewHolder extends RecyclerView.ViewHolder {
        TextView counsel_name_tv, designation_tv, phone_tv, email_tv, practice_area_tv;
        ImageView send_iv;

        public SearchCounselViewHolder(@NonNull View itemView) {
            super(itemView);
            counsel_name_tv = itemView.findViewById(R.id.counsel_name_tv);
            designation_tv = itemView.findViewById(R.id.designation_tv);
            phone_tv = itemView.findViewById(R.id.phone_tv);
            email_tv = itemView.findViewById(R.id.email_tv);
            practice_area_tv = itemView.findViewById(R.id.practice_area_tv);
            send_iv = itemView.findViewById(R.id.send_iv);
        }
    }
}
