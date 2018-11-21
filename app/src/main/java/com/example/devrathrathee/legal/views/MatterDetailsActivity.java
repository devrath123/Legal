package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private MatterReceivedBean.MatterReceived getMatter() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_MATTER) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_MATTER);
        }
        return null;
    }

    private void setMatterDetails(MatterReceivedBean.MatterReceived matterReceived) {
        lawyer_tv.setText(matterReceived.getName());
        court_tv.setText(matterReceived.getCourt_name());
        client_tv.setText(matterReceived.getClient_name());
        stage_tv.setText(matterReceived.getStage());
        next_date_tv.setText(matterReceived.getNext_date());
        parties_name_tv.setText(matterReceived.getParties());
    }
}
