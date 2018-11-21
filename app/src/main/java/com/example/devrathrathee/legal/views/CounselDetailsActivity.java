package com.example.devrathrathee.legal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.utils.Constants;

import butterknife.BindView;

public class CounselDetailsActivity extends AppCompatActivity {


    TextView counselor_tv, court_tv, judge_tv, parties_name_tv, hearing_date_tv, status_tv, conference_date_tv, conference_time_tv,
            conference_place_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsel_details);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Counsel Details");

        counselor_tv = findViewById(R.id.counselor_tv);
        court_tv = findViewById(R.id.court_tv);
        judge_tv = findViewById(R.id.judge_tv);
        parties_name_tv = findViewById(R.id.parties_name_tv);
        hearing_date_tv = findViewById(R.id.hearing_date_tv);
        status_tv = findViewById(R.id.status_tv);
        conference_date_tv = findViewById(R.id.conference_date_tv);
        conference_time_tv = findViewById(R.id.conference_time_tv);
        conference_place_tv = findViewById(R.id.conference_place_tv);

        setCounselInfo(getCounselBean());
    }

    public CounselDeskBean.CounselBean getCounselBean(){

        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_COUNSEL) != null){
            return getIntent().getParcelableExtra(Constants.INTENT_COUNSEL);
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

    private void setCounselInfo(CounselDeskBean.CounselBean counselBean){
        counselor_tv.setText(counselBean.getCounsel_name());
        court_tv.setText(counselBean.getCourt_name());
        judge_tv.setText(counselBean.getJudge_name());
        parties_name_tv.setText(counselBean.getParties());
        hearing_date_tv.setText(counselBean.getHearing_date());
        status_tv.setText(counselBean.getConference_status());
        conference_date_tv.setText(counselBean.getConference_date());
        conference_time_tv.setText(counselBean.getConference_time());
        conference_place_tv.setText(counselBean.getConference_place());
    }

}
