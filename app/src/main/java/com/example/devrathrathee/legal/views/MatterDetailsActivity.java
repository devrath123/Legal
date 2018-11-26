package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.MatterReceivedBean;
import com.example.devrathrathee.legal.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatterDetailsActivity extends AppCompatActivity {

    @BindView(R.id.lawyer_tv)
    TextView lawyer_tv;

    @BindView(R.id.court_tv)
    TextView court_tv;

    @BindView(R.id.judge_tv)
    TextView judge_tv;

    @BindView(R.id.parties_name_tv)
    TextView parties_name_tv;

    @BindView(R.id.client_tv)
    TextView client_tv;

    @BindView(R.id.stage_tv)
    TextView stage_tv;

    @BindView(R.id.next_date_tv)
    TextView next_date_tv;

    @BindView(R.id.lawyer_phone_tv)
    TextView lawyer_phone_tv;

    @BindView(R.id.lawyer_email_tv)
    TextView lawyer_email_tv;

    @BindView(R.id.court_no_tv)
    TextView court_no_tv;

    @BindView(R.id.accept_ll)
    LinearLayout accept_ll;

    @BindView(R.id.lawyer_label)
    TextView lawyer_label;

    @BindView(R.id.status_tv)
    TextView status_tv;

    @BindView(R.id.status_ll)
    LinearLayout status_ll;

    @BindView(R.id.accept_button)
    Button accept_button;

    @BindView(R.id.reject_button)
    Button reject_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter_details);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Matter Details");

        setMatterDetails(getMatter());

        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private String getMatterType() {
        if (getIntent() != null && getIntent().getStringExtra(Constants.INTENT_MATTER_TYPE) != null) {
            return getIntent().getStringExtra(Constants.INTENT_MATTER_TYPE);
        }
        return null;
    }


    private MatterReceivedBean.MatterReceived getMatter() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_MATTER) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_MATTER);
        }
        return null;
    }

    private void setMatterDetails(MatterReceivedBean.MatterReceived matterReceived) {
        court_tv.setText(matterReceived.getCourt_name());
        client_tv.setText(matterReceived.getClient_name());
        stage_tv.setText(matterReceived.getStage());
        next_date_tv.setText(matterReceived.getNext_date());
        parties_name_tv.setText(matterReceived.getParties());
        lawyer_email_tv.setText(matterReceived.getEmail());
        lawyer_phone_tv.setText(matterReceived.getPhone());
        court_no_tv.setText(matterReceived.getCourt_number());
        judge_tv.setText(matterReceived.getJudge_name());

        if (getMatterType() != null && getMatterType().equals(Constants.INTENT_MATTER_SENT)){
            lawyer_label.setText("Sent To");
            status_tv.setText(matterReceived.getLawyer_case_status());
            accept_ll.setVisibility(View.GONE);
            status_ll.setVisibility(View.VISIBLE);
            lawyer_tv.setText(matterReceived.getFirm_name());
        }else{
            accept_ll.setVisibility(View.VISIBLE);
            status_ll.setVisibility(View.GONE);
            lawyer_tv.setText(matterReceived.getName());
        }

    }
}
