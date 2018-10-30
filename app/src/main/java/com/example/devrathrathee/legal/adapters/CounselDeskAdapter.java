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
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.views.CaseDetailsActivity;
import com.example.devrathrathee.legal.views.CounselDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CounselDeskAdapter extends RecyclerView.Adapter<CounselDeskAdapter.CounselDeskViewHolder> {

    Context context;
    List<CounselDeskBean.CounselBean> counselBeanList = new ArrayList<>();

    public CounselDeskAdapter(Context context, List<CounselDeskBean.CounselBean> casesTodayBeanList) {
        this.context = context;
        this.counselBeanList.addAll(casesTodayBeanList);
    }


    @NonNull
    @Override
    public CounselDeskAdapter.CounselDeskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.counsel_item, viewGroup, false);

        return new CounselDeskAdapter.CounselDeskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CounselDeskAdapter.CounselDeskViewHolder todayCasesViewHolder, int i) {

        CounselDeskBean.CounselBean counselBean = counselBeanList.get(i);
        todayCasesViewHolder.counselor_name_tv_name_tv.setText(counselBean.getCounsel_name());
        todayCasesViewHolder.status_tv.setText(counselBean.getConference_status());
        todayCasesViewHolder.parties_name_tv.setText(counselBean.getParties());
        todayCasesViewHolder.cardRelativeLayout.setTag(i);
        todayCasesViewHolder.cardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) todayCasesViewHolder.cardRelativeLayout.getTag();

                Intent intent = new Intent(context, CounselDetailsActivity.class);
                intent.putExtra(Constants.INTENT_COUNSEL, counselBeanList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return counselBeanList.size();
    }

    public static class CounselDeskViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cardRelativeLayout;
        TextView counselor_name_tv_name_tv, parties_name_tv, status_tv;

        public CounselDeskViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRelativeLayout = itemView.findViewById(R.id.card_relative_layout);
            counselor_name_tv_name_tv = itemView.findViewById(R.id.counselor_name_tv_name_tv);
            parties_name_tv = itemView.findViewById(R.id.parties_name_tv);
            status_tv = itemView.findViewById(R.id.status_tv);
        }
    }

}
