package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CaseIdBean;
import com.example.devrathrathee.legal.beans.MatterReceivedBean;
import com.example.devrathrathee.legal.beans.PaymentStatusBean;
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfessionFeeDetailActivity extends AppCompatActivity {

    @BindView(R.id.case_number_tv)
    TextView case_number_tv;

    @BindView(R.id.client_tv)
    TextView client_tv;

    @BindView(R.id.court_tv)
    TextView court_tv;

    @BindView(R.id.category_tv)
    TextView category_tv;

    @BindView(R.id.client_phone_tv)
    TextView client_phone_tv;

    @BindView(R.id.payment_status_tv)
    TextView payment_status_tv;

    ProgressDialog progressDialog;
    List<String> paymentStatusList = new ArrayList<>();
    Map<String, String> paymentStatusMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_fee_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment Details");

        setPaymentDetails(getPaymentDetails());

        if (Connectivity.isConnected(ProfessionFeeDetailActivity.this)) {
            getPaymentStatus();
        } else {
            Utilities.internetConnectionError(ProfessionFeeDetailActivity.this);
        }

        payment_status_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Connectivity.isConnected(ProfessionFeeDetailActivity.this)) {
                    if (getPaymentDetails().getPaymt_status().equals("Pending")) {
                        updatePaymentStatus("Paid");
                    } else if (getPaymentDetails().getPaymt_status().equals("Paid")) {
                        updatePaymentStatus("Pending");
                    }
                } else {
                    Utilities.internetConnectionError(ProfessionFeeDetailActivity.this);
                }
            }
        });
    }

    private void setPaymentDetails(ProfessionalFeeBean.PaymentDetail paymentDetails) {
        case_number_tv.setText(paymentDetails.getCase_number());
        court_tv.setText(paymentDetails.getCourt_name());
        category_tv.setText(paymentDetails.getCategory());
        client_tv.setText(paymentDetails.getClient_name());
        client_phone_tv.setText(paymentDetails.getClient_phone());
        payment_status_tv.setText(paymentDetails.getPaymt_status());
    }

    @OnClick(R.id.edit_payment_details_iv)
    public void paymentDetailsEdit(View view) {
        Intent intent = new Intent(ProfessionFeeDetailActivity.this, AddPaymentActivity.class);
        intent.putExtra(Constants.INTENT_ADD_EDIT_FEE, Constants.INTENT_EDIT_FEE);
        intent.putExtra(Constants.INTENT_PAYMENT_DETAIL, getPaymentDetails());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private ProfessionalFeeBean.PaymentDetail getPaymentDetails() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_PAYMENT_DETAIL) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_PAYMENT_DETAIL);
        }
        return null;
    }

    private void updatePaymentStatus(final String status) {
        Map<String, String> profFeeMap = new HashMap<>();
        profFeeMap.put("action", "update2");
        profFeeMap.put("user_type", SharedPreferenceManager.getInstance(ProfessionFeeDetailActivity.this).getString(Constants.USER_TYPE));
        profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(ProfessionFeeDetailActivity.this).getString(Constants.USER_ID));
        profFeeMap.put("case_id", getPaymentDetails().getCase_id());
        profFeeMap.put("paymt_status", status);

        progressDialog.show();
        GSONRequest<RegistrationBean> profFeeGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.POST, API.BASE_URL + API.CASE_PAYMENT, RegistrationBean.class, profFeeMap,

                new Response.Listener<RegistrationBean>() {
                    @Override
                    public void onResponse(RegistrationBean response) {
                        progressDialog.dismiss();
                        getPaymentDetails().setPaymt_status(status);
                        payment_status_tv.setText(status);
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

    private void getPaymentStatus() {

        Map<String, String> profFeeMap = new HashMap<>();
        profFeeMap.put("action", "select3");
        profFeeMap.put("user_type", SharedPreferenceManager.getInstance(ProfessionFeeDetailActivity.this).getString(Constants.USER_TYPE));
        profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(ProfessionFeeDetailActivity.this).getString(Constants.USER_ID));

        progressDialog.show();
        GSONRequest<PaymentStatusBean> profFeeGSONRequest = new GSONRequest<PaymentStatusBean>(Request.Method.POST, API.BASE_URL + API.CASE_PAYMENT, PaymentStatusBean.class, profFeeMap,

                new Response.Listener<PaymentStatusBean>() {
                    @Override
                    public void onResponse(PaymentStatusBean response) {
                        progressDialog.dismiss();
                        if (response.getClient_status() != null && response.getClient_status().size() > 0) {

                            paymentStatusList.clear();
                            paymentStatusMap.clear();

                            for (PaymentStatusBean.ClientStatus clientStatus : response.getClient_status()) {
                                if (!paymentStatusList.contains(clientStatus.getPaymt_status())) {
                                    paymentStatusList.add(clientStatus.getPaymt_status());
                                    paymentStatusMap.put(clientStatus.getPaymt_status(), clientStatus.getCase_id());
                                }
                            }
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
}
