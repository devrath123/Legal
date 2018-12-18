package com.example.devrathrathee.legal.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CalendarCaseBean;
import com.example.devrathrathee.legal.views.CalendarActivity;

import java.util.ArrayList;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>{

    Context context;
    List<CalendarCaseBean.LawyerCalendar> calendarCaseBeans = new ArrayList<>();

    public CalendarAdapter(Context context, List<CalendarCaseBean.LawyerCalendar> calendarCaseBeanList){
        this.context = context;
        this.calendarCaseBeans.addAll(calendarCaseBeanList);
    }


    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_row, viewGroup,false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CalendarViewHolder calendarViewHolder, int i) {

        calendarViewHolder.date_tv.setText(calendarCaseBeans.get(i).getDate());
        calendarViewHolder.title_tv.setText(calendarCaseBeans.get(i).getTitle());

        calendarViewHolder.calendar_cv.setTag(i);
        calendarViewHolder.calendar_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) calendarViewHolder.calendar_cv.getTag();
                ((CalendarActivity)context).getCaseDetails(calendarCaseBeans.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return calendarCaseBeans.size();
    }

    public static class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView date_tv, title_tv;
        CardView calendar_cv;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            date_tv = itemView.findViewById(R.id.date_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
            calendar_cv = itemView.findViewById(R.id.calendar_cv);
        }
    }
}
