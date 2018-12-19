package com.example.devrathrathee.legal.views;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.FIndInternAdapter;
import com.example.devrathrathee.legal.adapters.SearchLawyerAdapter;
import com.example.devrathrathee.legal.beans.FindInternBean;
import com.example.devrathrathee.legal.beans.PracticeAreaBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.beans.SearchLawyerBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindInternActivity extends AppCompatActivity {

    EditText location_et;
    Spinner specialization_spinner;
    Button search_intern_btn;
    RecyclerView counseller_rv;
    ProgressDialog progressDialog;
    List<String> practiseList = new ArrayList<>();
    String selectedPractiseArea="";
    ArrayAdapter<String> practiseAreaArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_intern);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        location_et = findViewById(R.id.location_et);
        specialization_spinner = findViewById(R.id.specialization_spinner);
        counseller_rv = findViewById(R.id.counseller_rv);
        search_intern_btn = findViewById(R.id.search_intern_btn);

        search_intern_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findIntern();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Find Interns");

        practiceArea();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void practiceArea() {
        Map<String, String> practiceMap = new HashMap<>();
        practiceMap.put("practice_area_dropdown", "");
        progressDialog.show();
        GSONRequest<PracticeAreaBean> practiceBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.ACCOUNT_DETAILS, PracticeAreaBean.class, practiceMap,

                new Response.Listener<PracticeAreaBean>() {
                    @Override
                    public void onResponse(PracticeAreaBean response) {
                        progressDialog.dismiss();
                        if (response.getPractice_area_dropdown().size() > 0) {
                            practiseList.clear();

                            practiseList.add("Select Specialization");
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
                progressDialog.dismiss();
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

        specialization_spinner.setAdapter(practiseAreaArrayAdapter);

        specialization_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPractiseArea = (String) parent.getSelectedItem();

                if (selectedPractiseArea.equals("Select Specialization")) {
                    selectedPractiseArea = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void findIntern() {
        Map<String, String> practiceMap = new HashMap<>();
        practiceMap.put("action", "search");
        practiceMap.put("user_type", SharedPreferenceManager.getInstance(FindInternActivity.this).getString(Constants.USER_TYPE));
        practiceMap.put("lawyer_id", SharedPreferenceManager.getInstance(FindInternActivity.this).getString(Constants.USER_ID));
        practiceMap.put("intern_interest", selectedPractiseArea);
        practiceMap.put("intern_location_wise", location_et.getText().toString());

        progressDialog.show();

        GSONRequest<FindInternBean> practiceBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.FIND_INTERN, FindInternBean.class, practiceMap,

                new Response.Listener<FindInternBean>() {
                    @Override
                    public void onResponse(FindInternBean response) {
                        progressDialog.dismiss();
                        if (response.getSearch_intern() != null && response.getSearch_intern().size() > 0) {
                            setAdapter(response.getSearch_intern());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        practiceBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(practiceBeanGSONRequest);
    }


    private void setAdapter(List<FindInternBean.SearchIntern> lawyer_detail_trans) {
        FIndInternAdapter searchLawyerAdapter = new FIndInternAdapter(FindInternActivity.this, lawyer_detail_trans);
        counseller_rv.setAdapter(searchLawyerAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        counseller_rv.setLayoutManager(linearLayoutManager);
        searchLawyerAdapter.notifyDataSetChanged();
    }

    public void viewPdf(FindInternBean.SearchIntern findBean) {

    }

    public void sendMail(final FindInternBean.SearchIntern findBean) {

        final View deleteDialogView = LayoutInflater.from(this).inflate(R.layout.intern_message_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();

        final EditText message = deleteDialogView.findViewById(R.id.message_et);

        Button sendButton = deleteDialogView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog.dismiss();
                mail(message.getText().toString(), findBean);
            }
        });

        deleteDialog.setView(deleteDialogView);

        deleteDialog.show();

    }

    public void mail(String message, FindInternBean.SearchIntern searchIntern) {
        Map<String, String> practiceMap = new HashMap<>();
        practiceMap.put("action", "send");
        practiceMap.put("user_type", SharedPreferenceManager.getInstance(FindInternActivity.this).getString(Constants.USER_TYPE));
        practiceMap.put("lawyer_id", SharedPreferenceManager.getInstance(FindInternActivity.this).getString(Constants.USER_ID));
        practiceMap.put("filter[intern_id]", searchIntern.getId());
        practiceMap.put("filter[lawyer_msg]", message);
        practiceMap.put("intern_email", searchIntern.getEmail());

        progressDialog.show();
        GSONRequest<RegistrationBean> practiceBeanGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.POST, API.BASE_URL + API.FIND_INTERN, RegistrationBean.class, practiceMap,

                new Response.Listener<RegistrationBean>() {
                    @Override
                    public void onResponse(RegistrationBean response) {
                        progressDialog.dismiss();
                        Utilities.showToast(FindInternActivity.this, response.getMessage());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "form-data");
                return headers;
            }
        };

        practiceBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(practiceBeanGSONRequest);
    }
}
