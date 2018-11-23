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
import com.example.devrathrathee.legal.adapters.ProfessionalFeeAdapter;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfessionalFeeActivity extends AppCompatActivity {

    RecyclerView professionalFeeRV;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professional_fee);

        professionalFeeRV = findViewById(R.id.professional_fees_rv);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.add_payment_button_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfessionalFeeActivity.this, AddPaymentActivity.class));
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Case Payments");

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Connectivity.isConnected(ProfessionalFeeActivity.this)) {
            getPaymentDetails();
        } else {
            Utilities.internetConnectionError(ProfessionalFeeActivity.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getPaymentDetails() {
        Map<String, String> profFeeMap = new HashMap<>();
        profFeeMap.put("action", "select");
        profFeeMap.put("user_type", SharedPreferenceManager.getInstance(ProfessionalFeeActivity.this).getString(Constants.USER_TYPE));
        profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(ProfessionalFeeActivity.this).getString(Constants.USER_ID));

        progressDialog.show();
        GSONRequest<ProfessionalFeeBean> profFeeGSONRequest = new GSONRequest<ProfessionalFeeBean>(Request.Method.POST, API.BASE_URL + API.CASE_PAYMENT, ProfessionalFeeBean.class, profFeeMap,

                new Response.Listener<ProfessionalFeeBean>() {
                    @Override
                    public void onResponse(ProfessionalFeeBean response) {
                        progressDialog.dismiss();
                        if (response.getClient_payment_detail() != null) {
                            setAdapter(response.getClient_payment_detail());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        profFeeGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(profFeeGSONRequest);
    }

    private void setAdapter(List<ProfessionalFeeBean.PaymentDetail> client_payment_detail) {
        ProfessionalFeeAdapter professionalFeeAdapter = new ProfessionalFeeAdapter(ProfessionalFeeActivity.this, client_payment_detail);
        professionalFeeRV.setAdapter(professionalFeeAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        professionalFeeRV.setLayoutManager(mLayoutManager);
    }
}
