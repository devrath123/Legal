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
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.RobotoLightTextView;
import com.example.devrathrathee.legal.utils.RobotoRegularTextView;
import com.example.devrathrathee.legal.views.CaseDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CasesAdapter extends RecyclerView.Adapter<CasesAdapter.TodayCasesViewHolder> {

    Context context;
    List<CaseBean.CasesToday> casesTodayBeanList = new ArrayList<>();

    public CasesAdapter(Context context, List<CaseBean.CasesToday> casesTodayBeanList) {
        this.context = context;
        this.casesTodayBeanList.addAll(casesTodayBeanList);
    }


    @NonNull
    @Override
    public TodayCasesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cases_item, viewGroup, false);

        return new TodayCasesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TodayCasesViewHolder todayCasesViewHolder, int i) {

        CaseBean.CasesToday casesTodayBean = casesTodayBeanList.get(i);
        todayCasesViewHolder.next_date_tv.setText(casesTodayBean.getDisplay_next_date());
        if (casesTodayBean.getStage().equals("Evidence") || casesTodayBean.equals("Part-Heard") || casesTodayBean.getStage().equals("Cross") ||
                casesTodayBean.getStage().equals("Arguments") || casesTodayBean.getStage().equals("Dismissal") || casesTodayBean.getStage().equals("Withdrawn")) {
            todayCasesViewHolder.stage_tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            todayCasesViewHolder.stage_tv.setTextColor(context.getResources().getColor(R.color.black));
        }
        todayCasesViewHolder.stage_tv.setText(casesTodayBean.getStage());
        todayCasesViewHolder.court_name_tv.setText(casesTodayBean.getCourt_name());
        todayCasesViewHolder.parties_name_tv.setText(casesTodayBean.getParty_a() + " vs " + casesTodayBean.getParty_b());
        todayCasesViewHolder.case_number_tv.setText(casesTodayBean.getCase_number());
        todayCasesViewHolder.cardRelativeLayout.setTag(i);
        todayCasesViewHolder.cardRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) todayCasesViewHolder.cardRelativeLayout.getTag();

                Intent intent = new Intent(context, CaseDetailsActivity.class);
                intent.putExtra(Constants.INTENT_CASE, casesTodayBeanList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return casesTodayBeanList.size();
    }

    public static class TodayCasesViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout cardRelativeLayout;
        RobotoLightTextView court_name_tv, case_number_tv, parties_name_tv, next_date_tv, stage_tv;

        public TodayCasesViewHolder(@NonNull View itemView) {
            super(itemView);
            cardRelativeLayout = itemView.findViewById(R.id.card_relative_layout);
            next_date_tv = itemView.findViewById(R.id.next_date_tv);
            court_name_tv = itemView.findViewById(R.id.court_name_tv);
            parties_name_tv = itemView.findViewById(R.id.parties_name_tv);
            stage_tv = itemView.findViewById(R.id.stage_tv);
            case_number_tv = itemView.findViewById(R.id.case_number_tv);
        }
    }
}
