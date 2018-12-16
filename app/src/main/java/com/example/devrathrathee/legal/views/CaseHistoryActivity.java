package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.CaseHistoryAdapter;
import com.example.devrathrathee.legal.adapters.CasesAdapter;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.utils.Constants;


public class CaseHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_history);

        recyclerView = findViewById(R.id.case_history_rv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Case History");

        setAdapter(getCaseBean());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private CaseBean.CasesToday getCaseBean() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_CASE_TODAY) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_CASE_TODAY);
        }

        return null;
    }

    private void setAdapter(CaseBean.CasesToday cases_today) {
        CaseHistoryAdapter casesAdapter = new CaseHistoryAdapter(cases_today.getNext_judge_name(), cases_today.getNext_lawyer_name(),
                cases_today.getNext_stage(), cases_today.getNext_update_date(),
                cases_today.getPrev_case_date());
        recyclerView.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

    }
}
