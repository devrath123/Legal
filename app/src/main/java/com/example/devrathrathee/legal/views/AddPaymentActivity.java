package com.example.devrathrathee.legal.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CaseIdBean;
import com.example.devrathrathee.legal.beans.ProfessionalFeeBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPaymentActivity extends AppCompatActivity {

    @BindView(R.id.case_number_tv)
    TextView case_number_tv;

    @BindView(R.id.case_number_spinner)
    Spinner case_number_spinner;

    @BindView(R.id.payment_mode_spinner)
    Spinner payment_mode_spinner;

    @BindView(R.id.payment_amount_et)
    EditText payment_amount_et;

    @BindView(R.id.cheque_number_et)
    EditText cheque_number_et;

    @BindView(R.id.payment_date_et)
    EditText payment_date_et;

    @BindView(R.id.comments_et)
    EditText comments_et;

    @BindView(R.id.add_payment_button)
    Button add_payment_button;

    String selectedPaymentMode = "", selectedCaseId = "", selectedCaseNumber = "", url = "";
    ProgressDialog progressDialog;
    String[] paymentModeArray = new String[]{"Cash", "Credit/Debit Card", "Cheque"};
    List<String> caseNumberList = new ArrayList<>();
    Map<String, String> caseNumberMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getAddEditIntent() != null && getAddEditIntent().equals(Constants.INTENT_EDIT_FEE)) {
            getSupportActionBar().setTitle("Edit Payment");
            setPaymentDetails(getPaymentDetails());
            case_number_tv.setVisibility(View.GONE);
            case_number_spinner.setVisibility(View.GONE);
            selectedCaseId = getPaymentDetails().getCase_id();
            selectedCaseNumber = getPaymentDetails().getCase_number();
            add_payment_button.setText("Update");

            if (getPaymentDetails().getPayment_amt() != null && getPaymentDetails().getPayment_amt().size() > 0) {
                payment_amount_et.setText(getPaymentDetails().getPayment_amt().get(getPaymentDetails().getPayment_amt().size() - 1).getAmount());
            }

            if (getPaymentDetails().getPayment_check_number() != null && getPaymentDetails().getPayment_check_number().size() > 0) {
                cheque_number_et.setText(getPaymentDetails().getPayment_check_number().get(getPaymentDetails().getPayment_check_number().size() - 1).getC_number());
            }

            if (getPaymentDetails().getPayment_date() != null && getPaymentDetails().getPayment_date().size() > 0) {
                payment_date_et.setText(getPaymentDetails().getPayment_date().get(getPaymentDetails().getPayment_date().size() - 1).getDate());
            }

            if (getPaymentDetails().getPayment_remarks() != null && getPaymentDetails().getPayment_remarks().size() > 0) {
                comments_et.setText(getPaymentDetails().getPayment_remarks().get(getPaymentDetails().getPayment_remarks().size() - 1).getRemark());
            }
        } else {
            add_payment_button.setText("Add");
            getSupportActionBar().setTitle("Add Payment");
            if (Connectivity.isConnected(AddPaymentActivity.this)) {
                getCaseId();
            } else {
                Utilities.internetConnectionError(AddPaymentActivity.this);
            }
        }

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                payment_date_et.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        };

        payment_date_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddPaymentActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<String> paymentModeArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        paymentModeArray);
        paymentModeArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        payment_mode_spinner.setAdapter(paymentModeArrayAdapter);

        payment_mode_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPaymentMode = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        add_payment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connectivity.isConnected(AddPaymentActivity.this)) {
                    addPayment();
                } else {
                    Utilities.internetConnectionError(AddPaymentActivity.this);
                }
            }
        });
    }

    private void setPaymentDetails(ProfessionalFeeBean.PaymentDetail paymentDetails) {

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

    private String getAddEditIntent() {
        if (getIntent() != null && getIntent().getStringExtra(Constants.INTENT_ADD_EDIT_FEE) != null) {
            return getIntent().getStringExtra(Constants.INTENT_ADD_EDIT_FEE);
        }
        return null;
    }


    private void addPayment() {

        if (getAddEditIntent() != null && getAddEditIntent().equals(Constants.INTENT_EDIT_FEE)) {
            url = API.BASE_URL + API.CASE_PAYMENT + "?action=update&case_id=" + selectedCaseId + "&filter[case_no]=" + selectedCaseNumber + "&filter[paymt_dt]=" + payment_date_et.getText().toString() + "&filter[paymt_mode]=" + selectedPaymentMode +
                    "&filter[cnumber]=" + cheque_number_et.getText().toString() + "&filter[paymt_amt]=" + payment_amount_et.getText().toString() + "&filter[remarks]=" + comments_et.getText().toString() + "&user_type=" +
                    SharedPreferenceManager.getInstance(AddPaymentActivity.this).getString(Constants.USER_TYPE) + "&lawyer_id=" + SharedPreferenceManager.getInstance(AddPaymentActivity.this).getString(Constants.USER_ID);
        } else {
            url = API.BASE_URL + API.CASE_PAYMENT + "?action=insert&case_id=" + selectedCaseId + "&filter[case_no]=" + selectedCaseNumber + "&filter[paymt_dt]=" + payment_date_et.getText().toString() + "&filter[paymt_mode]=" + selectedPaymentMode +
                    "&filter[cnumber]=" + cheque_number_et.getText().toString() + "&filter[paymt_amt]=" + payment_amount_et.getText().toString() + "&filter[remarks]=" + comments_et.getText().toString() + "&user_type=" +
                    SharedPreferenceManager.getInstance(AddPaymentActivity.this).getString(Constants.USER_TYPE) + "&lawyer_id=" + SharedPreferenceManager.getInstance(AddPaymentActivity.this).getString(Constants.USER_ID);
        }
        progressDialog.show();
        GSONRequest<RegistrationBean> profFeeGSONRequest = new GSONRequest<>(Request.Method.POST, url, RegistrationBean.class, null,

                new Response.Listener<RegistrationBean>() {
                    @Override
                    public void onResponse(RegistrationBean response) {
                        progressDialog.dismiss();
                        Utilities.showToast(AddPaymentActivity.this, response.getMessage());

                        if (getAddEditIntent() != null && getAddEditIntent().equals(Constants.INTENT_EDIT_FEE)) {
                            Intent intent = new Intent(AddPaymentActivity.this, ProfessionalFeeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            finish();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        profFeeGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(profFeeGSONRequest);
    }

    private void getCaseId() {

        Map<String, String> profFeeMap = new HashMap<>();
        profFeeMap.put("action", "select2");
        profFeeMap.put("user_type", SharedPreferenceManager.getInstance(AddPaymentActivity.this).getString(Constants.USER_TYPE));
        profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(AddPaymentActivity.this).getString(Constants.USER_ID));

        progressDialog.show();
        GSONRequest<CaseIdBean> profFeeGSONRequest = new GSONRequest<CaseIdBean>(Request.Method.POST, API.BASE_URL + API.CASE_PAYMENT, CaseIdBean.class, profFeeMap,

                new Response.Listener<CaseIdBean>() {
                    @Override
                    public void onResponse(CaseIdBean response) {
                        progressDialog.dismiss();
                        if (response.getCase_details() != null && response.getCase_details().size() > 0) {

                            caseNumberList.clear();
                            caseNumberMap.clear();

                            for (CaseIdBean.CaseDetail caseDetail : response.getCase_details()) {
                                caseNumberList.add(caseDetail.getCase_number());
                                caseNumberMap.put(caseDetail.getCase_number(), caseDetail.getCase_id());
                            }

                            setCaseId(caseNumberList);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        profFeeGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(profFeeGSONRequest);
    }


    private void setCaseId(List<String> caseId) {
        ArrayAdapter<String> caseIdArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        caseId);
        caseIdArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        case_number_spinner.setAdapter(caseIdArrayAdapter);

        case_number_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCaseNumber = (String) parent.getSelectedItem();
                for (Map.Entry<String, String> caseNumber : caseNumberMap.entrySet()) {
                    if (caseNumber.getKey().equals(selectedCaseNumber)) {
                        selectedCaseId = caseNumber.getValue();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
