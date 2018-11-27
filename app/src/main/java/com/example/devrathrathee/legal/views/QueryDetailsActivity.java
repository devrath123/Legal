package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.beans.LegalQueryBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

public class QueryDetailsActivity extends AppCompatActivity {

    TextView customer_tv, phone_tv, email_tv, date_tv, quote_type_tv, status_tv, query_tv, lawyer_tv;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Legal Query");

        customer_tv = findViewById(R.id.customer_tv);
        phone_tv = findViewById(R.id.phone_tv);
        email_tv = findViewById(R.id.email_tv);
        date_tv = findViewById(R.id.date_tv);
        quote_type_tv = findViewById(R.id.quote_type_tv);
        status_tv = findViewById(R.id.status_tv);
        query_tv = findViewById(R.id.query_tv);
        lawyer_tv = findViewById(R.id.lawyer_tv);

        setLegalQueryDetails(getLegalQueryBean());

        ImageView delete_case_details_iv = findViewById(R.id.delete_case_details_iv);
        delete_case_details_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteLegalQuery();
            }
        });

        status_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getLegalQueryBean().getQuote_status().equals("New")) {
                    changeStatusLegalQuery("Pending");
                } else if (getLegalQueryBean().getQuote_status().equals("Pending")) {
                    changeStatusLegalQuery("Accepted");
                } else if (getLegalQueryBean().getQuote_status().equals("Accepted")) {
                    changeStatusLegalQuery("Declined");
                } else if (getLegalQueryBean().getQuote_status().equals("Declined")) {
                    changeStatusLegalQuery("Closed");
                } else if (getLegalQueryBean().getQuote_status().equals("Closed")) {
                    changeStatusLegalQuery("New");
                }
            }
        });

    }

    private void changeStatusLegalQuery(final String status) {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "update");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(QueryDetailsActivity.this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(QueryDetailsActivity.this).getString(Constants.USER_ID));
        todayCasesMap.put("quote_id", getLegalQueryBean().getQuote_id());
        todayCasesMap.put("quote_status", status);

        progressDialog.show();
        GSONRequest<RegistrationBean> casesTodayBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.LEGAL_QUERIES, RegistrationBean.class, todayCasesMap,

                new Response.Listener<RegistrationBean>() {
                    @Override
                    public void onResponse(RegistrationBean response) {
                        progressDialog.dismiss();
                        Utilities.showToast(QueryDetailsActivity.this, response.getMessage());
                        getLegalQueryBean().setQuote_status(status);
                        status_tv.setText(status);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }

    private void deleteLegalQuery() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "delete");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(QueryDetailsActivity.this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(QueryDetailsActivity.this).getString(Constants.USER_ID));
        todayCasesMap.put("quote_id", getLegalQueryBean().getQuote_id());

        progressDialog.show();
        GSONRequest<RegistrationBean> casesTodayBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.LEGAL_QUERIES, RegistrationBean.class, todayCasesMap,

                new Response.Listener<RegistrationBean>() {
                    @Override
                    public void onResponse(RegistrationBean response) {
                        progressDialog.dismiss();
                        Utilities.showToast(QueryDetailsActivity.this, response.getMessage());
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }

    private void setLegalQueryDetails(LegalQueryBean.ManageQueryBean legalQueryBean) {

        customer_tv.setText(legalQueryBean.getCust_name());
        phone_tv.setText(legalQueryBean.getCust_phone());
        email_tv.setText(legalQueryBean.getCust_email());
        date_tv.setText(legalQueryBean.getDate());
        quote_type_tv.setText(legalQueryBean.getQuote_type());
        status_tv.setText(legalQueryBean.getQuote_status());
        query_tv.setText(legalQueryBean.getCust_query());
        lawyer_tv.setText(legalQueryBean.getLawyer_name());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public LegalQueryBean.ManageQueryBean getLegalQueryBean() {

        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_LEGAL_QUERY) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_LEGAL_QUERY);
        }

        return null;
    }


}
