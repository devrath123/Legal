package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.SearchLawyerAdapter;
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

public class SearchLawyerActivity extends AppCompatActivity {

    RecyclerView lawyer_rv;
    ProgressDialog progressDialog;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_lawyer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lawyer_rv = findViewById(R.id.lawyer_rv);
        searchView = findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (Connectivity.isConnected(SearchLawyerActivity.this)) {
                    searchLawyers(s);
                } else {
                    Utilities.internetConnectionError(SearchLawyerActivity.this);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Lawyer Availability");
    }

    private String getCaseId() {
        if (getIntent() != null && getIntent().getStringExtra(Constants.INTENT_CASE_ID) != null) {
            return getIntent().getStringExtra(Constants.INTENT_CASE_ID);
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void transferCase(String lawyer_id) {

        if (Connectivity.isConnected(SearchLawyerActivity.this)) {
            Map<String, String> profFeeMap = new HashMap<>();
            profFeeMap.put("action", "transfer");
            profFeeMap.put("user_type", SharedPreferenceManager.getInstance(SearchLawyerActivity.this).getString(Constants.USER_TYPE));
            profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(SearchLawyerActivity.this).getString(Constants.USER_ID));
            profFeeMap.put("case_id", getCaseId());
            profFeeMap.put("receiver_lawyer_id", lawyer_id);

            progressDialog.show();
            GSONRequest<RegistrationBean> profFeeGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.POST, API.BASE_URL + API.CASE_TRANSFER, RegistrationBean.class, profFeeMap,

                    new Response.Listener<RegistrationBean>() {
                        @Override
                        public void onResponse(RegistrationBean response) {
                            progressDialog.dismiss();
                            Utilities.showToast(SearchLawyerActivity.this, response.getMessage());
                            Intent intent = new Intent(SearchLawyerActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
            Utilities.internetConnectionError(SearchLawyerActivity.this);
        }
    }

    private void searchLawyers(final String city) {
        if (Connectivity.isConnected(SearchLawyerActivity.this)) {
            Map<String, String> profFeeMap = new HashMap<>();
            profFeeMap.put("action", "search");
            profFeeMap.put("user_type", SharedPreferenceManager.getInstance(SearchLawyerActivity.this).getString(Constants.USER_TYPE));
            profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(SearchLawyerActivity.this).getString(Constants.USER_ID));
            profFeeMap.put("search_filter", city);

            progressDialog.show();
            GSONRequest<SearchLawyerBean> profFeeGSONRequest = new GSONRequest<SearchLawyerBean>(Request.Method.POST, API.BASE_URL + API.CASE_TRANSFER, SearchLawyerBean.class, profFeeMap,

                    new Response.Listener<SearchLawyerBean>() {
                        @Override
                        public void onResponse(SearchLawyerBean response) {
                            progressDialog.dismiss();
                            setAdapter(response.getLawyer_detail_trans());
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
            Utilities.internetConnectionError(SearchLawyerActivity.this);
        }
    }

    private void setAdapter(List<SearchLawyerBean.LawyerDetail> lawyer_detail_trans) {
        SearchLawyerAdapter searchLawyerAdapter = new SearchLawyerAdapter(SearchLawyerActivity.this, lawyer_detail_trans);
        lawyer_rv.setAdapter(searchLawyerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lawyer_rv.setLayoutManager(linearLayoutManager);
        searchLawyerAdapter.notifyDataSetChanged();
    }

}
