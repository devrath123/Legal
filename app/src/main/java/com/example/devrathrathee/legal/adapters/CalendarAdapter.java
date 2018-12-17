package com.example.devrathrathee.legal.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;

public class CalendarAdapter {


    public static class CalendarViewHolder extends RecyclerView.ViewHolder{

        TextView date_tv, title_tv;

        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);

            date_tv = itemView.findViewById(R.id.date_tv);
            title_tv = itemView.findViewById(R.id.title_tv);
        }
    }
}
