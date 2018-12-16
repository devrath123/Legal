package com.example.devrathrathee.legal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CaseBean;

import java.util.ArrayList;
import java.util.List;

public class CaseHistoryAdapter extends RecyclerView.Adapter<CaseHistoryAdapter.CaseHistoryViewHolder> {

    List<CaseBean.CasesToday.NextJudgeName> judgeNameList = new ArrayList<>();
    List<CaseBean.CasesToday.NextLawyerName> lawyerNameList = new ArrayList<>();
    List<CaseBean.CasesToday.NextStage> nextStageList = new ArrayList<>();
    List<CaseBean.CasesToday.NextUpdateDate> nextUpdateDateList = new ArrayList<>();
    List<CaseBean.CasesToday.PrevCaseDate> prevCaseDates = new ArrayList<>();

    public CaseHistoryAdapter(List<CaseBean.CasesToday.NextJudgeName> judgeNameList, List<CaseBean.CasesToday.NextLawyerName> lawyerNameList,
                              List<CaseBean.CasesToday.NextStage> nextStageList, List<CaseBean.CasesToday.NextUpdateDate> nextUpdateDateList,
                              List<CaseBean.CasesToday.PrevCaseDate> prevCaseDates) {
        this.judgeNameList.addAll(judgeNameList);
        this.lawyerNameList.addAll(lawyerNameList);
        this.nextStageList.addAll(nextStageList);
        this.nextUpdateDateList.addAll(nextUpdateDateList);
        this.prevCaseDates.addAll(prevCaseDates);
    }

    @NonNull
    @Override
    public CaseHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.case_history_row, viewGroup,false);
        return new CaseHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseHistoryViewHolder caseHistoryViewHolder, int i) {

        caseHistoryViewHolder.judge_name_tv.setText(judgeNameList.get(i).getName());
        caseHistoryViewHolder.attended_by_tv.setText(lawyerNameList.get(i).getNext_id());
        caseHistoryViewHolder.next_date_tv.setText(nextUpdateDateList.get(i).getNext_date());
        caseHistoryViewHolder.stage_tv.setText(nextStageList.get(i).getStages());
        caseHistoryViewHolder.prev_date_tv.setText(prevCaseDates.get(i).getPrev_date());
    }

    @Override
    public int getItemCount() {
        return this.judgeNameList.size();
    }

    public static class CaseHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView prev_date_tv, stage_tv, next_date_tv, attended_by_tv, judge_name_tv;

        public CaseHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            prev_date_tv = itemView.findViewById(R.id.prev_date_tv);
            stage_tv = itemView.findViewById(R.id.stage_tv);
            next_date_tv = itemView.findViewById(R.id.next_date_tv);
            attended_by_tv = itemView.findViewById(R.id.attended_by_tv);
            judge_name_tv = itemView.findViewById(R.id.judge_name_tv);
        }
    }
}
