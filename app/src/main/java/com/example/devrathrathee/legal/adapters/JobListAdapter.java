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
import com.example.devrathrathee.legal.beans.JobBean;
import com.example.devrathrathee.legal.views.JobsListActivity;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder>{

    Context context;
    List<JobBean.PostedJobList> jobList = new ArrayList<>();

    public JobListAdapter(Context context, List<JobBean.PostedJobList> jobBeansList){
        this.context = context;
        this.jobList.addAll(jobBeansList);
    }


    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.job_item,viewGroup,false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JobViewHolder jobViewHolder, int i) {

        JobBean.PostedJobList job = jobList.get(i);
        jobViewHolder.location_tv.setText(job.getLocation());
        jobViewHolder.specialization_tv.setText(job.getSpecialization());
        jobViewHolder.description_tv.setText(job.getDescription());
        jobViewHolder.posted_date_tv.setText(job.getPosted_date());

        jobViewHolder.delete_job_iv.setTag(i);
        jobViewHolder.delete_job_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) jobViewHolder.delete_job_iv.getTag();
                ((JobsListActivity)context).deleteJob(jobList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder{

        ImageView delete_job_iv;
        TextView location_tv, specialization_tv, description_tv, posted_date_tv;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            location_tv = itemView.findViewById(R.id.location_tv);
            specialization_tv = itemView.findViewById(R.id.specialization_tv);
            description_tv = itemView.findViewById(R.id.description_tv);
            posted_date_tv = itemView.findViewById(R.id.posted_date_tv);
            delete_job_iv = itemView.findViewById(R.id.delete_job_iv);
        }
    }
}
