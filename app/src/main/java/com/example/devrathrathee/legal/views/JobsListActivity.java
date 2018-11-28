package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.JobListAdapter;
import com.example.devrathrathee.legal.adapters.SearchLawyerAdapter;
import com.example.devrathrathee.legal.beans.JobBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.beans.SearchLawyerBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobsListActivity extends AppCompatActivity {

    RecyclerView jobsRV;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);

        jobsRV = findViewById(R.id.jobs_rv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_job_button_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobsListActivity.this, AddJobActivity.class));
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Jobs");
    }

    @Override
    protected void onResume() {
        super.onResume();
        jobList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void jobList() {
        if (Connectivity.isConnected(JobsListActivity.this)) {
            Map<String, String> profFeeMap = new HashMap<>();
            profFeeMap.put("action", "select");
            profFeeMap.put("user_type", SharedPreferenceManager.getInstance(JobsListActivity.this).getString(Constants.USER_TYPE));
            profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(JobsListActivity.this).getString(Constants.USER_ID));

            progressDialog.show();
            GSONRequest<JobBean> profFeeGSONRequest = new GSONRequest<JobBean>(Request.Method.POST, API.BASE_URL + API.POST_JOBS, JobBean.class, profFeeMap,

                    new Response.Listener<JobBean>() {
                        @Override
                        public void onResponse(JobBean response) {
                            progressDialog.dismiss();
                            setAdapter(response.getPosted_jobs_list());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            });

            profFeeGSONRequest.setShouldCache(false);
            Utilities.getRequestQueue(this).add(profFeeGSONRequest);
        } else {
            Utilities.internetConnectionError(JobsListActivity.this);
        }
    }

    public void deleteJob(String jobId) {
        if (Connectivity.isConnected(JobsListActivity.this)) {
            Map<String, String> profFeeMap = new HashMap<>();
            profFeeMap.put("action", "delete");
            profFeeMap.put("user_type", SharedPreferenceManager.getInstance(JobsListActivity.this).getString(Constants.USER_TYPE));
            profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(JobsListActivity.this).getString(Constants.USER_ID));
            profFeeMap.put("id", jobId);

            progressDialog.show();
            GSONRequest<RegistrationBean> profFeeGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.POST, API.BASE_URL + API.POST_JOBS, RegistrationBean.class, profFeeMap,

                    new Response.Listener<RegistrationBean>() {
                        @Override
                        public void onResponse(RegistrationBean response) {
                            progressDialog.dismiss();
                            Utilities.showToast(JobsListActivity.this, response.getMessage());
                            jobList();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            });

            profFeeGSONRequest.setShouldCache(false);
            Utilities.getRequestQueue(this).add(profFeeGSONRequest);
        } else {
            Utilities.internetConnectionError(JobsListActivity.this);
        }
    }

    private void setAdapter(List<JobBean.PostedJobList> jobLists) {
        JobListAdapter jobListAdapter = new JobListAdapter(JobsListActivity.this, jobLists);
        jobsRV.setAdapter(jobListAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        jobsRV.setLayoutManager(linearLayoutManager);
        jobListAdapter.notifyDataSetChanged();
    }


}
