package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.CaseHistoryAdapter;
import com.example.devrathrathee.legal.adapters.PaymentHistoryAdapter;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;
import com.example.devrathrathee.legal.utils.Constants;

public class PaymentHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView total_payment_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        recyclerView = findViewById(R.id.payment_history_rv);
        total_payment_tv = findViewById(R.id.total_payment_tv);

        total_payment_tv.setText("Total Payment Received " + getPaymentDetails().getPayment_total());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Case Payment (Case Number : " + getPaymentDetails().getCase_number() + ")");

        setAdapter(getPaymentDetails());
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

    private void setAdapter(ProfessionalFeeBean.PaymentDetail paymentDetail) {
        PaymentHistoryAdapter casesAdapter = new PaymentHistoryAdapter(paymentDetail.getPayment_date(), paymentDetail.getPayment_mode(),
                paymentDetail.getPayment_check_number(), paymentDetail.getPayment_amt(),
                paymentDetail.getPayment_remarks());
        recyclerView.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

    }
}
