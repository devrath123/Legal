package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.beans.LegalQueryBean;
import com.example.devrathrathee.legal.utils.Constants;

public class QueryDetailsActivity extends AppCompatActivity {

    TextView customer_tv,phone_tv,email_tv,date_tv,quote_type_tv,status_tv,query_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        setLegalQueryDetails(getLegalQueryBean());

    }

    private void setLegalQueryDetails(LegalQueryBean.ManageQueryBean legalQueryBean) {

        customer_tv.setText(legalQueryBean.getCust_name());
        phone_tv.setText(legalQueryBean.getCust_phone());
        email_tv.setText(legalQueryBean.getCust_email());
        date_tv.setText(legalQueryBean.getDate());
        quote_type_tv.setText(legalQueryBean.getQuote_type());
        status_tv.setText(legalQueryBean.getQuote_status());
        query_tv.setText(legalQueryBean.getCust_query());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public LegalQueryBean.ManageQueryBean getLegalQueryBean(){

        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_LEGAL_QUERY) != null){
            return getIntent().getParcelableExtra(Constants.INTENT_LEGAL_QUERY);
        }

        return null;
    }

}
