package com.example.devrathrathee.legal.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.SearchCounselAdapter;
import com.example.devrathrathee.legal.adapters.SearchLawyerAdapter;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.beans.SearchCounselBean;
import com.example.devrathrathee.legal.beans.SearchLawyerBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchCounsellerActivity extends AppCompatActivity {

    RecyclerView lawyer_rv;
    ProgressDialog progressDialog;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_counseller);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lawyer_rv = findViewById(R.id.counseller_rv);
        searchView = findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                if (Connectivity.isConnected(SearchCounsellerActivity.this)) {
                    searchLawyers(s);
                } else {
                    Utilities.internetConnectionError(SearchCounsellerActivity.this);
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Counsels");
    }

    private String getCaseId() {
        if (getIntent() != null && getIntent().getStringExtra(Constants.INTENT_CASE_ID) != null) {
            return getIntent().getStringExtra(Constants.INTENT_CASE_ID);
        }
        return null;
    }

    private String getNextDate() {
        if (getIntent() != null && getIntent().getStringExtra("NextDate") != null) {
            return getIntent().getStringExtra("NextDate");
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

    private void searchLawyers(final String city) {
        if (Connectivity.isConnected(SearchCounsellerActivity.this)) {
            Map<String, String> profFeeMap = new HashMap<>();
            profFeeMap.put("action", "select");
            profFeeMap.put("user_type", SharedPreferenceManager.getInstance(SearchCounsellerActivity.this).getString(Constants.USER_TYPE));
            profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(SearchCounsellerActivity.this).getString(Constants.USER_ID));
            profFeeMap.put("search_filter", city);

            progressDialog.show();
            GSONRequest<SearchCounselBean> profFeeGSONRequest = new GSONRequest<SearchCounselBean>(Request.Method.POST, API.BASE_URL + API.CASE_TRANSFER, SearchCounselBean.class, profFeeMap,

                    new Response.Listener<SearchCounselBean>() {
                        @Override
                        public void onResponse(SearchCounselBean response) {
                            progressDialog.dismiss();
                            setAdapter(response.getCounsel_result());
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            });

            profFeeGSONRequest.setShouldCache(false);
            Utilities.getRequestQueue(this).add(profFeeGSONRequest);
        } else {
            Utilities.internetConnectionError(SearchCounsellerActivity.this);
        }
    }

    private void setAdapter(List<SearchCounselBean.CounselResult> lawyer_detail_trans) {
        SearchCounselAdapter searchLawyerAdapter = new SearchCounselAdapter(SearchCounsellerActivity.this, lawyer_detail_trans);
        lawyer_rv.setAdapter(searchLawyerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lawyer_rv.setLayoutManager(linearLayoutManager);
        searchLawyerAdapter.notifyDataSetChanged();
    }

    String[] typeArray = new String[]{"For Conference", "For Hearing"};
    String[] timeArray = new String[]{"11 AM", "3 PM"};
    String selectedType = "", selectedTime = "";

    public void transferCase(final String counsel_id) {

        final View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.counselor_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();

        Spinner typeSpinner = deleteDialogView.findViewById(R.id.for_spinner);

        ArrayAdapter<String> categoryArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        typeArray);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        typeSpinner.setAdapter(categoryArrayAdapter);

        final Spinner timeSpinner = deleteDialogView.findViewById(R.id.time_spinner);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = (String) parent.getSelectedItem();

                if (selectedType.equals("For Hearing")) {
                    timeSpinner.setVisibility(View.VISIBLE);
                    selectedType = "hearing";
                } else {
                    timeSpinner.setVisibility(View.GONE);
                    selectedTime = "";
                    selectedType = "conference";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> timeArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.account_details_spinner_item,
                        timeArray);
        timeArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        timeSpinner.setAdapter(timeArrayAdapter);

        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = ((String) parent.getSelectedItem());

                if (selectedTime.equals("11 AM")) {
                    selectedTime = "11:00";
                } else {
                    selectedTime = "15:00";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button sendButton = deleteDialogView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transfer(counsel_id);
             //   deleteDialog.dismiss();
            }
        });

        deleteDialog.setView(deleteDialogView);

        deleteDialog.show();

    }

    private void transfer(String counsel_id) {
        if (Connectivity.isConnected(SearchCounsellerActivity.this)) {
            Map<String, String> profFeeMap = new HashMap<>();
            profFeeMap.put("action", "insert");
            profFeeMap.put("user_type", SharedPreferenceManager.getInstance(SearchCounsellerActivity.this).getString(Constants.USER_TYPE));
            profFeeMap.put("lawyer_id", SharedPreferenceManager.getInstance(SearchCounsellerActivity.this).getString(Constants.USER_ID));
            profFeeMap.put("filter[counsel_id]", counsel_id);
            profFeeMap.put("filter[cc_case_id]", getCaseId());
            //  profFeeMap.put("filter[cc_next_date]", getNextDate());
            profFeeMap.put("type_selection", selectedType);
            profFeeMap.put("filter[cc_hearing_time]", selectedTime);

            progressDialog.show();
            GSONRequest<RegistrationBean> profFeeGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.POST, API.BASE_URL + API.CASE_TRANSFER, RegistrationBean.class, profFeeMap,
                    new Response.Listener<RegistrationBean>() {
                        @Override
                        public void onResponse(RegistrationBean response) {
                            progressDialog.dismiss();
                            Utilities.showToast(SearchCounsellerActivity.this, response.getMessage());
                            Intent intent = new Intent(SearchCounsellerActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "multipart/form-data");
                    return headers;
                }
            };

            profFeeGSONRequest.setShouldCache(false);
            Utilities.getRequestQueue(this).add(profFeeGSONRequest);
        } else {
            Utilities.internetConnectionError(SearchCounsellerActivity.this);
        }
    }
}
