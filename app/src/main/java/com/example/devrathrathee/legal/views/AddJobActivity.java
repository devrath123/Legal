package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.SpecializationAdapter;
import com.example.devrathrathee.legal.beans.JobBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.beans.SpecializationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddJobActivity extends AppCompatActivity {

    SpecializationAdapter myAdapter;
    EditText location_et, description_et;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        location_et = findViewById(R.id.location_et);
        description_et = findViewById(R.id.description_et);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add Job");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");


        final String[] specializationArray = {
                "Select Specialization", "Aviation Law", "Administrative Law", "Family Law", "Corporate Law",
                "Civil Law", "Criminal Law", "Cooperative Society", "Custom and Excise Law", "Land Revenue",
                "Cyber Law", "Security Law", "Banking Commercial Law", "Consumer Law", "Label and Service law",
                "Motor Accident Law", "Intellectual Propert Law"};

        Spinner spinner = (Spinner) findViewById(R.id.specialization_spinner);

        ArrayList<SpecializationBean> specializationBeanArrayList = new ArrayList<>();

        for (int i = 0; i < specializationArray.length; i++) {
            SpecializationBean bean = new SpecializationBean();
            bean.setTitle(specializationArray[i]);
            bean.setSelected(false);
            specializationBeanArrayList.add(bean);
        }

        myAdapter = new SpecializationAdapter(AddJobActivity.this, 0, specializationBeanArrayList);
        spinner.setAdapter(myAdapter);
    }

    private boolean checkValidation() {

        if (location_et.getText().toString().length() > 0) {
            if (description_et.getText().toString().length() > 0) {
                return true;
            } else {
                Utilities.showToast(AddJobActivity.this, "Enter Description");
                return false;
            }
        } else {
            Utilities.showToast(AddJobActivity.this, "Enter location");
            return false;
        }
    }

    public void addJob(View view) {

        if (Connectivity.isConnected(AddJobActivity.this)) {
            if (checkValidation()) {

                List<SpecializationBean> specializationBeans = new ArrayList<>();

                for (SpecializationBean bean : myAdapter.getListState()) {
                    if (bean.isSelected()) {
                        specializationBeans.add(bean);
                    }
                }

                String url = API.BASE_URL + API.POST_JOBS + "?action=insert&user_type=" + SharedPreferenceManager.getInstance(AddJobActivity.this).getString(Constants.USER_TYPE) +
                        "&lawyer_id=" + SharedPreferenceManager.getInstance(AddJobActivity.this).getString(Constants.USER_ID) + "&location=" + location_et.getText().toString() +
                        "&text_contain=" + description_et.getText().toString();

                int i = 0;
                for (SpecializationBean bean : specializationBeans) {
                    url = url + "&specialization[" + i + "]=" + bean.getTitle();
                    i++;
                }

                progressDialog.show();
                GSONRequest<RegistrationBean> profFeeGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.GET, url, RegistrationBean.class, null,

                        new Response.Listener<RegistrationBean>() {
                            @Override
                            public void onResponse(RegistrationBean response) {
                                progressDialog.dismiss();
                                Utilities.showToast(AddJobActivity.this, response.getMessage());
                                finish();
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

        } else {
            Utilities.internetConnectionError(AddJobActivity.this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
