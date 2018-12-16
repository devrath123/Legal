package com.example.devrathrathee.legal.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaseDetailsActivity extends AppCompatActivity {

    @BindView(R.id.court_tv)
    TextView court_tv;

    @BindView(R.id.court_number_tv)
    TextView court_number_tv;

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

    @BindView(R.id.lawyer_ll)
    LinearLayout lawyer_ll;

    @BindView(R.id.lawyer_tv)
    TextView lawyer_tv;

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

    @OnClick(R.id.send_case_details_iv)
    public void sendCaseDetailsClick(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CaseDetailsActivity.this);

        builder.setTitle("Send To ?")
                .setPositiveButton("Lawyer", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(CaseDetailsActivity.this, SearchLawyerActivity.class);
                        intent.putExtra(Constants.INTENT_CASE_ID, getCaseBean().getCase_id());
                        CaseDetailsActivity.this.startActivity(intent);
                    }
                })
                .setNegativeButton("Counseller", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    @OnClick(R.id.delete_case_details_iv)
    public void deleteCaseDetailsClick(View view) {

        if (Connectivity.isConnected(CaseDetailsActivity.this)) {
            String url;

            url = API.BASE_URL + API.CASES_ALL + "?action=delete&case_id=" + getCaseBean().getCase_id() + "&user_type=" + SharedPreferenceManager.getInstance(CaseDetailsActivity.this).getString(Constants.USER_TYPE) +
                    "&lawyer_id=" + SharedPreferenceManager.getInstance(CaseDetailsActivity.this).getString(Constants.USER_ID);

            GSONRequest<RegistrationBean> deleteCaseGSONRequest = new GSONRequest<>(Request.Method.POST, url, RegistrationBean.class, null,

                    new Response.Listener<RegistrationBean>() {
                        @Override
                        public void onResponse(RegistrationBean response) {
                            Utilities.showToast(CaseDetailsActivity.this, "Case deleted");
                            finish();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Utilities.serverError(CaseDetailsActivity.this);
                }
            });

            deleteCaseGSONRequest.setShouldCache(false);
            Utilities.getRequestQueue(this).add(deleteCaseGSONRequest);

        } else {
            Utilities.internetConnectionError(CaseDetailsActivity.this);
        }

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

    private void setCase(final CaseBean.CasesToday caseBean) {

        if (SharedPreferenceManager.getInstance(CaseDetailsActivity.this).getString(Constants.USER_TYPE).equals("firm")) {
            lawyer_ll.setVisibility(View.VISIBLE);
            lawyer_tv.setText(caseBean.getName());
        }

        court_tv.setText(caseBean.getCourt_name());
        judge_tv.setText(caseBean.getJudge_name());
        case_number_tv.setText(caseBean.getCase_number());
        case_number_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (caseBean.getNext_judge_name() != null && caseBean.getNext_judge_name().size() > 0) {
                    Intent intent = new Intent(CaseDetailsActivity.this, CaseHistoryActivity.class);
                    intent.putExtra(Constants.INTENT_CASE_TODAY, caseBean);
                    startActivity(intent);
                }

            }
        });
        parties_name_tv.setText(caseBean.getParty_a() + " vs " + caseBean.getParty_b());
        next_date_tv.setText(caseBean.getDisplay_next_date());
        prev_date_tv.setText(caseBean.getDisplay_prev_date());
        stage_tv.setText(caseBean.getStage());
        client_tv.setText(caseBean.getClient_name());
        client_phone_tv.setText(caseBean.getClient_phone());
        category_tv.setText(caseBean.getCategory());
        court_number_tv.setText(caseBean.getCourt_number());
        if (caseBean.getStage().equals("Evidence") || caseBean.equals("Part-Heard") || caseBean.getStage().equals("Cross") ||
                caseBean.getStage().equals("Arguments") || caseBean.getStage().equals("Dismissal") || caseBean.getStage().equals("Withdrawn")) {
            stage_tv.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            stage_tv.setTextColor(getResources().getColor(R.color.black));
        }
    }
}
