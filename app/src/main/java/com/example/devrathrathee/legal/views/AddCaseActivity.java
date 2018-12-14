package com.example.devrathrathee.legal.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.AccountDetailBean;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.RobotoBoldTextView;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCaseActivity extends AppCompatActivity {

    @BindView(R.id.court_name_et)
    EditText courtNameEt;

    @BindView(R.id.court_number_et)
    EditText courtNumberEt;

    @BindView(R.id.case_number_et)
    EditText caseNumberEt;

    @BindView(R.id.judge_name_et)
    EditText judgeNameEt;

    @BindView(R.id.category_spinner)
    Spinner categorySpinner;

    @BindView(R.id.client_name_et)
    EditText clientNameEt;

    @BindView(R.id.client_phone_et)
    EditText clientPhoneEt;

    @BindView(R.id.stage_spinner)
    Spinner stageSpinner;

    @BindView(R.id.party_a_et)
    EditText partyAEt;

    @BindView(R.id.party_b_et)
    EditText partyBEt;

    @BindView(R.id.select_next_date_et)
    EditText selectNextDate;

    @BindView(R.id.add_case_button)
    Button addCaseButton;

    String[] categoryArray = new String[]{"Civil", "Criminal"};
    String[] stageArray = new String[]{"Admission", "Service", "Not-Heard", "Hearing", "Evidence", "Part-Heard", "Cross", "Arguments", "Reply", "Dismissal", "Bail",
            "Anticipatory-Bail", "Interim", "Add-Interim", "Defence-Witness", "Prosecution-Witness", "Withdrawn"};

    String selectedCategory, selectedStage;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        categoryArray);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        categorySpinner.setAdapter(categoryArrayAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> stageArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        stageArray);
        stageArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        stageSpinner.setAdapter(stageArrayAdapter);

        stageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStage = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                selectNextDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };

        selectNextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddCaseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        if (getAddEditIntent() != null && getAddEditIntent().equals(Constants.INTENT_EDIT_CASE)) {
            getSupportActionBar().setTitle("Update Case");
            addCaseButton.setText("Update");
            setCaseDetails(getCaseDetails());
        } else {
            getSupportActionBar().setTitle("Add Case");
            addCaseButton.setText("Add");
        }
    }

    private void setCaseDetails(CaseBean.CasesToday caseDetails) {
        courtNameEt.setText(caseDetails.getCourt_name());
        courtNumberEt.setText(caseDetails.getCourt_number());
        caseNumberEt.setText(caseDetails.getCase_number());
        judgeNameEt.setText(caseDetails.getJudge_name());
        selectNextDate.setText(caseDetails.getDisplay_next_date());
        clientNameEt.setText(caseDetails.getClient_name());
        clientPhoneEt.setText(caseDetails.getClient_phone());
        partyAEt.setText(caseDetails.getParty_a());
        partyBEt.setText(caseDetails.getParty_b());
        categorySpinner.setSelection(getSelectedCategory(caseDetails.getCategory()));
        stageSpinner.setSelection(getSelectedStage(caseDetails.getStage()));
    }

    private int getSelectedCategory(String category) {
        if (category.equals("Civil")) {
            return 0;
        } else {
            return 1;
        }
    }

    private CaseBean.CasesToday getCaseDetails() {
        if (getIntent() != null && getIntent().getParcelableExtra(Constants.INTENT_CASE) != null) {
            return getIntent().getParcelableExtra(Constants.INTENT_CASE);
        }
        return null;
    }

    private int getSelectedStage(String stage) {
        if (stage.equals("Admission")) {
            return 0;
        } else if (stage.equals("Service")) {
            return 1;
        } else if (stage.equals("Not-Heard")) {
            return 2;
        } else if (stage.equals("Hearing")) {
            return 3;
        } else if (stage.equals("Evidence")) {
            return 4;
        } else if (stage.equals("Part-Heard")) {
            return 5;
        } else if (stage.equals("Cross")) {
            return 6;
        } else if (stage.equals("Arguments")) {
            return 7;
        } else if (stage.equals("Reply")) {
            return 8;
        } else if (stage.equals("Dismissal")) {
            return 9;
        } else if (stage.equals("Bail")) {
            return 10;
        } else if (stage.equals("Anticipatory-Bail")) {
            return 11;
        } else if (stage.equals("Interim")) {
            return 12;
        } else if (stage.equals("Add-Interim")) {
            return 13;
        } else if (stage.equals("Defence-Witness")) {
            return 14;
        } else if (stage.equals("Prosecution-Witness")) {
            return 15;
        }

        return 0;
    }

    private String getAddEditIntent() {
        if (getIntent() != null && getIntent().getStringExtra(Constants.INTENT_ADD_EDIT_CASE) != null) {
            return getIntent().getStringExtra(Constants.INTENT_ADD_EDIT_CASE);
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


    private boolean validateInput() {
        if (courtNameEt.getText().toString().length() > 0) {
            if (caseNumberEt.getText().toString().length() > 0) {
                if (selectNextDate.getText().toString().length() > 0) {
                    if (clientNameEt.getText().toString().length() > 0) {
                        if (partyAEt.getText().toString().length() > 0) {
                            if (partyBEt.getText().toString().length() > 0) {
                                return true;
                            } else {
                                Utilities.showToast(AddCaseActivity.this, "Enter Party B");
                            }
                        } else {
                            Utilities.showToast(AddCaseActivity.this, "Enter Party A");
                        }
                    } else {
                        Utilities.showToast(AddCaseActivity.this, "Enter Client name");
                    }
                } else {
                    Utilities.showToast(AddCaseActivity.this, "Select Next date");
                }
            } else {
                Utilities.showToast(AddCaseActivity.this, "Enter Case number");
            }
        } else {
            Utilities.showToast(AddCaseActivity.this, "Enter Court name");
        }

        return false;
    }

    @OnClick(R.id.add_case_button)
    public void addCase(View view) {

        if (Connectivity.isConnected(AddCaseActivity.this)) {
            if (validateInput()) {
                progressDialog.show();
                String url;

                if (getAddEditIntent() != null && getAddEditIntent().equals(Constants.INTENT_EDIT_CASE)) {
                    url = API.BASE_URL + API.CASES_ALL + "?action=update&filter[court_name]=" + courtNameEt.getText().toString() + "&case_id=" + getCaseDetails().getCase_id() + "&filter[case_number]=" + caseNumberEt.getText().toString() + "&filter[category]=" + selectedCategory +
                            "&filter[client_name]=" + clientNameEt.getText().toString() + "&filter[next_date]=" + selectNextDate.getText().toString() + "&filter[client_phone]=" + clientPhoneEt.getText().toString() +
                            "&filter[court_number]=" + courtNumberEt.getText().toString() + "&filter[judge_name]=" + judgeNameEt.getText().toString() + "&filter[party_a]=" + partyAEt.getText().toString() + "&filter[party_b]=" + partyBEt.getText().toString() +
                            "&filter[stage]=" + selectedStage + "&user_type=" + SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_TYPE) + "&filter[lawyer_id]=" + SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_ID) +
                            "&lawyer_id=" + SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_ID);

                } else {
                    url = API.BASE_URL + API.CASES_ALL + "?action=insert2&filter[court_name]=" + courtNameEt.getText().toString() + "&filter[case_number]=" + caseNumberEt.getText().toString() + "&filter[category]=" + selectedCategory +
                            "&filter[client_name]=" + clientNameEt.getText().toString() + "&filter[next_date]=" + selectNextDate.getText().toString() + "&filter[client_phone]=" + clientPhoneEt.getText().toString() +
                            "&filter[court_number]=" + courtNumberEt.getText().toString() + "&filter[judge_name]=" + judgeNameEt.getText().toString() + "&filter[party_a]=" + partyAEt.getText().toString() + "&filter[party_b]=" + partyBEt.getText().toString() +
                            "&filter[stage]=" + selectedStage + "&user_type=" + SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_TYPE) + "&filter[lawyer_id]=" + SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_ID) +
                            "&lawyer_id=" + SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_ID);
                }
                GSONRequest<RegistrationBean> addCaseGSONRequest = new GSONRequest<>(Request.Method.POST, url, RegistrationBean.class, null,

                        new Response.Listener<RegistrationBean>() {
                            @Override
                            public void onResponse(RegistrationBean response) {
                                progressDialog.dismiss();
                                if (getAddEditIntent() != null && getAddEditIntent().equals(Constants.INTENT_EDIT_CASE)) {
                                    Utilities.showToast(AddCaseActivity.this, "Case updated");
                                } else {
                                    Utilities.showToast(AddCaseActivity.this, "Case added");
                                }

                                Intent intent = new Intent(AddCaseActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Utilities.serverError(AddCaseActivity.this);
                    }
                });

                addCaseGSONRequest.setShouldCache(false);
                Utilities.getRequestQueue(this).add(addCaseGSONRequest);
            }
        } else {
            Utilities.internetConnectionError(AddCaseActivity.this);
        }
    }
}
