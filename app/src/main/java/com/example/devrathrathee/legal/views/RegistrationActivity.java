package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.name_et)
    EditText nameEt;

    @BindView(R.id.email_et)
    EditText emailEt;

    @BindView(R.id.mobile_number_et)
    EditText mobileEt;

    @BindView(R.id.password_et)
    EditText passwordEt;

    @BindView(R.id.confirm_password_et)
    EditText confirmPasswordEt;

    @BindView(R.id.user_type_spinner)
    Spinner userTypeSpinner;

    String[] userTypeArray = new String[]{"Select User Type", "Lawyer", "Law Firm", "Counsel", "Law Intern"};

    String selectedUserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item,
                        userTypeArray);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout
                .simple_list_item_single_choice);

        userTypeSpinner.setAdapter(spinnerArrayAdapter);

        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUserType = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private String getSelectedUserType(String userType) {

        if (userType.equals("Lawyer")) {
            return "lawyer";
        } else if (userType.equals("Law Firm")) {
            return "firm";
        } else if (userType.equals("Counsel")) {
            return "counsel";
        } else if (userType.equals("Law Intern")) {
            return "intern";
        } else {
            return "";
        }
    }

    public void onRegisterClick(View view) {

        if (nameEt.getText().toString().length() > 0 && mobileEt.getText().toString().length() > 0 &&
                emailEt.getText().toString().length() > 0 && passwordEt.getText().toString().length() > 0) {
           if (!getSelectedUserType(selectedUserType).equals("")){

               Map<String,String> registrationMap = new HashMap<>();
               registrationMap.put("usertype", getSelectedUserType(selectedUserType));
               registrationMap.put("name",nameEt.getText().toString());
               registrationMap.put("phone",mobileEt.getText().toString());
               registrationMap.put("email",emailEt.getText().toString());
               registrationMap.put("password",passwordEt.getText().toString());

               GSONRequest<RegistrationBean> registrationBeanGSONRequest = new GSONRequest<RegistrationBean>(Request.Method.POST, API.BASE_URL + API.REGISTRATION, RegistrationBean.class,
                       registrationMap, new Response.Listener<RegistrationBean>() {
                   @Override
                   public void onResponse(RegistrationBean response) {
                        if (response.getSuccess() == 1){
                            Utilities.showToast(RegistrationActivity.this,"Registration successful");
                            finish();
                        }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });

               registrationBeanGSONRequest.setShouldCache(false);
               Utilities.getRequestQueue(RegistrationActivity.this).add(registrationBeanGSONRequest);

           }else{
               Utilities.showToast(RegistrationActivity.this, "Select user type");
           }

        } else {
            Utilities.showToast(RegistrationActivity.this, "Enter all fields");
        }

    }
}
