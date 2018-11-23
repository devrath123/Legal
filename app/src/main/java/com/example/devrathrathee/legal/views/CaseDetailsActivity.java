package com.example.devrathrathee.legal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaseDetailsActivity extends AppCompatActivity {

    @BindView(R.id.court_tv)
    TextView court_tv;

    @BindView(R.id.judge_tv)
    TextView judge_tv;

    @BindView(R.id.case_number_tv)
    TextView case_number_tv;

    @BindView((R.id.parties_name_tv))
    TextView parties_name_tv;

    @BindView(R.id.next_date_tv)
    TextView next_date_tv;

    @BindView(R.id.stage_tv)
    TextView stage_tv;

    @BindView(R.id.prev_date_tv)
    TextView prev_date_tv;

    @BindView(R.id.client_tv)
    TextView client_tv;

    @BindView(R.id.client_phone_tv)
    TextView client_phone_tv;

    @BindView(R.id.category_tv)
    TextView category_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_details);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Case Details");

        setCase(getCaseBean());
    }

    @OnClick(R.id.edit_case_details_iv)
    public void editCaseDetailsClick(View view) {
        Intent intent = new Intent(CaseDetailsActivity.this, AddCaseActivity.class);
        intent.putExtra(Constants.INTENT_ADD_EDIT_CASE, Constants.INTENT_EDIT_CASE);
        intent.putExtra(Constants.INTENT_CASE, getCaseBean());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private CaseBean.CasesToday getCaseBean() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_CASE) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_CASE);
        }

        return null;
    }

    private void setCase(CaseBean.CasesToday caseBean) {
        court_tv.setText(caseBean.getCourt_name());
        judge_tv.setText(caseBean.getJudge_name());
        case_number_tv.setText(caseBean.getCase_number());
        parties_name_tv.setText(caseBean.getParty_a() + " vs " + caseBean.getParty_b());
        next_date_tv.setText(caseBean.getDisplay_next_date());
        stage_tv.setText(caseBean.getStage());
        client_tv.setText(caseBean.getClient_name());
        client_phone_tv.setText(caseBean.getClient_phone());
        category_tv.setText(caseBean.getCategory());
    }
}
