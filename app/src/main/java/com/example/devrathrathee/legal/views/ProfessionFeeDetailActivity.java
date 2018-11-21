package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.MatterReceivedBean;
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;
import com.example.devrathrathee.legal.utils.Constants;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profession_fee_detail);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Payment Details");

        setPaymentDetails(getPaymentDetails());
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
    public void paymentDetailsEdit(View view){

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
}
