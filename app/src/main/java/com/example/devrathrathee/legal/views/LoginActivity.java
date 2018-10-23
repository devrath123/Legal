package com.example.devrathrathee.legal.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.LoginBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.email_et)
    EditText emailET;

    @BindView(R.id.password_et)
    EditText passwordET;

    @BindView(R.id.login_btn)
    Button loginButton;

    @BindView(R.id.user_type_spinner)
    Spinner userTypeSpinner;

    RequestQueue requestQueue;

    String[] userTypeArray = new String[]{"Select User Type", "Lawyer/Law firm", "Counsel", "Law Intern"};

    String selectedUserType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);

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

        if (userType.equals("Lawyer/Law firm")) {
            return "lawyer";
        } else if (userType.equals("Counsel")) {
            return "counsel";
        } else if (userType.equals("Law Intern")) {
            return "intern";
        } else {
            return "";
        }
    }

    public void register(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }

    public void loginClick(View view) {

        if (emailET.getText().toString().length() > 0 && passwordET.getText().toString().length() > 0) {

            if (!getSelectedUserType(selectedUserType).equals("")) {
                Map<String, String> map = new HashMap<>();
                map.put("usertype", getSelectedUserType(selectedUserType));
                map.put("email", emailET.getText().toString());
                map.put("password", passwordET.getText().toString());

                final GSONRequest<LoginBean> leadTypeGsonRequest = new GSONRequest<LoginBean>(
                        Request.Method.POST,
                        API.BASE_URL + API.LOGIN,
                        LoginBean.class, map,
                        new com.android.volley.Response.Listener<LoginBean>() {
                            @Override
                            public void onResponse(LoginBean response) {
                                if (response.getUser_details() == null) {
                                    Utilities.showToast(LoginActivity.this, "Invalid credentials");
                                } else {
                                    SharedPreferenceManager.getInstance(LoginActivity.this).putString(Constants.USER_ID, response.getUser_details().get(0).getUser_id());
                                    if (response.getUser_details().get(0).getFirm_id() != null){
                                        SharedPreferenceManager.getInstance(LoginActivity.this).putString(Constants.FIRM_ID, response.getUser_details().get(0).getFirm_id());
                                        SharedPreferenceManager.getInstance(LoginActivity.this).putString(Constants.USER_TYPE, response.getUser_details().get(0).getType());
                                        SharedPreferenceManager.getInstance(LoginActivity.this).putString(Constants.USER_NAME, response.getUser_details().get(0).getUser_name());
                                    }
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                }
                            }
                        },
                        new com.android.volley.Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Utilities.serverError(LoginActivity.this);
                            }
                        });
                leadTypeGsonRequest.setShouldCache(false);
                Utilities.getRequestQueue(LoginActivity.this).add(leadTypeGsonRequest);
            } else {
                Utilities.showToast(LoginActivity.this, "Select UserType");
            }

        } else {
            Utilities.showToast(LoginActivity.this, "Enter both Email address & password");
        }
    }
}
