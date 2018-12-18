package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.CalendarAdapter;
import com.example.devrathrathee.legal.adapters.CaseHistoryAdapter;
import com.example.devrathrathee.legal.beans.CalendarCaseBean;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.CheckInDialogView;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.DateSelectListener;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity implements DateSelectListener {

    ProgressDialog progressDialog;
    RelativeLayout calenderRelativeLayout;
    RecyclerView cases_calendar_rv;
    CheckInDialogView checkInDialogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        cases_calendar_rv = findViewById(R.id.cases_calendar_rv);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Calendar");

        calenderRelativeLayout = findViewById(R.id.calenderRelativeLayout);
        checkInDialogView = new CheckInDialogView(this, this, true);
        calenderRelativeLayout.addView(checkInDialogView.Create_CheckInView());

        getTodayCases();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCheckInDate(String checkInDate) {

    }

    public void getTodayCases() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "select");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(this).getString(Constants.USER_ID));
        todayCasesMap.put("month", checkInDialogView.getMonth());
        todayCasesMap.put("year", checkInDialogView.getYear());

        GSONRequest<CalendarCaseBean> casesTodayBeanGSONRequest = new GSONRequest<CalendarCaseBean>(Request.Method.POST, API.BASE_URL + API.LAWYER_CALENDAR, CalendarCaseBean.class, todayCasesMap,
                new Response.Listener<CalendarCaseBean>() {
                    @Override
                    public void onResponse(CalendarCaseBean response) {
                        setAdapter(response.getLawyer_calender());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }

    private void setAdapter(List<CalendarCaseBean.LawyerCalendar> cases_today) {
        CalendarAdapter casesAdapter = new CalendarAdapter(CalendarActivity.this, cases_today);
        cases_calendar_rv.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        cases_calendar_rv.setLayoutManager(mLayoutManager);

    }

    public void getCaseDetails(String id) {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "view");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(this).getString(Constants.USER_ID));
        todayCasesMap.put("id", id);

        GSONRequest<CaseBean> casesTodayBeanGSONRequest = new GSONRequest<CaseBean>(Request.Method.POST, API.BASE_URL + API.LAWYER_CALENDAR, CaseBean.class, todayCasesMap,
                new Response.Listener<CaseBean>() {
                    @Override
                    public void onResponse(CaseBean response) {
                        Log.i("Response : ", response.getCases_details().toString());
                        Intent intent = new Intent(CalendarActivity.this, CaseDetailsActivity.class);
                        intent.putExtra(Constants.INTENT_CASE, response.getCases_details().get(0));
                        intent.putExtra(Constants.CASE_TYPE, Constants.TYPE_CALENDAR);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }

}
