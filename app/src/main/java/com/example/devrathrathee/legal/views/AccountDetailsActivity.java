package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.AccountDetailBean;
import com.example.devrathrathee.legal.beans.PracticeAreaBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.RobotoBoldTextView;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountDetailsActivity extends AppCompatActivity {

    @BindView(R.id.firm_name_et)
    EditText firm_name_et;

    @BindView(R.id.email_et)
    EditText email_et;

    @BindView(R.id.phone_et)
    EditText phone_et;

    @BindView(R.id.website_et)
    EditText website_et;

    @BindView(R.id.address_et)
    EditText address_et;

    @BindView(R.id.address2_et)
    EditText address2_et;

    @BindView(R.id.address3_et)
    EditText address3_et;

    @BindView(R.id.about_us_et)
    EditText about_us_et;

    @BindView(R.id.practise_courts_et)
    EditText practise_courts_et;

    @BindView(R.id.language_spinner)
    Spinner languageSpinner;

    @BindView(R.id.practise_areas_spinner)
    Spinner practise_areas_spinner;

    @BindView(R.id.enrollment_no_et)
    EditText enrollment_no_et;

    String[] languagesArray = new String[]{"English", "Marathi", "Hindi", "Gujarati"};
    ArrayAdapter<String> practiseAreaArrayAdapter;
    List<String> practiseList = new ArrayList<>();
    String selectedLanguage, selectedPractiseArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        ButterKnife.bind(this);

        ArrayAdapter<String> languageArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        languagesArray);
        languageArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        languageSpinner.setAdapter(languageArrayAdapter);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Account Details");

        if (Connectivity.isConnected(AccountDetailsActivity.this)) {
            practiceArea();
            accountDetails();
        } else {
            Utilities.internetConnectionError(AccountDetailsActivity.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void update(View view) {

    }

    private void accountDetails() {
        Map<String, String> accountDetailsMap = new HashMap<>();
        accountDetailsMap.put("select", "");
        accountDetailsMap.put("lawyer_id", SharedPreferenceManager.getInstance(AccountDetailsActivity.this).getString(Constants.USER_ID));

        GSONRequest<AccountDetailBean> accountDetailsBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.ACCOUNT_DETAILS, AccountDetailBean.class, accountDetailsMap,

                new Response.Listener<AccountDetailBean>() {
                    @Override
                    public void onResponse(AccountDetailBean response) {
                        if (response.getLawyer_account_details().size() > 0) {
                            setAccountDetails(response.getLawyer_account_details().get(0));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        accountDetailsBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(accountDetailsBeanGSONRequest);
    }

    private void practiceArea() {
        Map<String, String> practiceMap = new HashMap<>();
        practiceMap.put("practice_area_dropdown", "");

        GSONRequest<PracticeAreaBean> practiceBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.ACCOUNT_DETAILS, PracticeAreaBean.class, practiceMap,

                new Response.Listener<PracticeAreaBean>() {
                    @Override
                    public void onResponse(PracticeAreaBean response) {
                        if (response.getPractice_area_dropdown().size() > 0) {
                            practiseList.clear();

                            for (PracticeAreaBean.PracticeAreaDropdown bean : response.getPractice_area_dropdown()) {
                                practiseList.add(bean.getPa_name());
                            }

                            if (practiseList.size() > 0) {
                                setPractiseSpinnerData(practiseList);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        practiceBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(practiceBeanGSONRequest);
    }

    private void setPractiseSpinnerData(List<String> practiceAreaList) {
        practiseAreaArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        practiceAreaList);
        practiseAreaArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        practise_areas_spinner.setAdapter(practiseAreaArrayAdapter);

        practise_areas_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPractiseArea = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAccountDetails(AccountDetailBean.LawyerAccountDetail accountDetails) {
        firm_name_et.setText(accountDetails.getName());
        email_et.setText(accountDetails.getEmail());
        phone_et.setText(accountDetails.getPhone());
        website_et.setText(accountDetails.getWebsite());
        address_et.setText(accountDetails.getAddress());
        practise_courts_et.setText(accountDetails.getPractice_courts());
        about_us_et.setText(accountDetails.getAbout_us());
        address2_et.setText(accountDetails.getAddress2());
        address3_et.setText(accountDetails.getAddress3());
        setSelectedLanguage(accountDetails.getLanguages());
    }

    private void setSelectedLanguage(String language) {
        if (language.contains("English")) {
            languageSpinner.setSelection(0);
        }
        if (language.contains("Marathi")) {
            languageSpinner.setSelection(1);
        }
        if (language.contains("Hindi")) {
            languageSpinner.setSelection(2);
        }
        if (language.contains("Gujarati")){
            languageSpinner.setSelection(3);
        }
    }
}
