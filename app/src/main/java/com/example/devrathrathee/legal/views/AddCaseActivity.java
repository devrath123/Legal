package com.example.devrathrathee.legal.views;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.AccountDetailBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
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

    String[] categoryArray = new String[]{"Civil", "Criminal"};
    String[] stageArray = new String[]{"Admission", "Service", "Not-Heard", "Hearing", "Evidence", "Part-Heard", "Cross", "Arguments", "Reply", "Dismissal", "Bail",
            "Anticipatory-Bail", "Interim", "Add-Interim", "Defence-Witness", "Prosecution-Witness"};

    String selectedCategory, selectedStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Case");

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

    public void addCase(View view) {

        if (validateInput()) {
            Map<String, String> addCaseMap = new HashMap<>();
            addCaseMap.put("action", "insert2");
            addCaseMap.put("filter['court_name']", courtNameEt.getText().toString());
            addCaseMap.put("filter['lawyer_id']", SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_ID));
            addCaseMap.put("lawyer_id", SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_ID));
            addCaseMap.put("filter['case_number']", caseNumberEt.getText().toString());
            addCaseMap.put("filter['category']", selectedCategory);
            addCaseMap.put("filter['client_name']", clientNameEt.getText().toString());
            addCaseMap.put("filter['next_date']", selectNextDate.getText().toString());
            addCaseMap.put("filter['client_phone']", clientPhoneEt.getText().toString());
            addCaseMap.put("filter['court_number']", courtNumberEt.getText().toString());
            addCaseMap.put("filter['judge_name']", judgeNameEt.getText().toString());
            addCaseMap.put("filter['party_a']", partyAEt.getText().toString());
            addCaseMap.put("filter['party_b']", partyBEt.getText().toString());
            addCaseMap.put("filter['stage']", selectedStage);
            addCaseMap.put("user_type", SharedPreferenceManager.getInstance(AddCaseActivity.this).getString(Constants.USER_TYPE));

            GSONRequest<RegistrationBean> addCaseGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.CASES_ALL, RegistrationBean.class, addCaseMap,

                    new Response.Listener<RegistrationBean>() {
                        @Override
                        public void onResponse(RegistrationBean response) {
                            Log.i("Response : ", response.toString());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error : ", "");
                }
            });

            addCaseGSONRequest.setShouldCache(false);
            Utilities.getRequestQueue(this).add(addCaseGSONRequest);
        }

    }
}
