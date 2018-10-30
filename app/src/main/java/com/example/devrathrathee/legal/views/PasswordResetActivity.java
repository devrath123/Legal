package com.example.devrathrathee.legal.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.RegistrationBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

public class PasswordResetActivity extends AppCompatActivity {

    EditText passwordET, newPasswordET, confirmPasswordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Change Password");

        passwordET = findViewById(R.id.old_password_et);
        newPasswordET = findViewById(R.id.new_password_et);
        confirmPasswordET = findViewById(R.id.conform_new_password_et);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void resetPassword(View view){

        if (passwordET.getText().toString().trim().length() > 0 && newPasswordET.getText().toString().trim().length() > 0 && confirmPasswordET.getText().toString().trim().length() > 0){

            if (newPasswordET.getText().toString().equals(confirmPasswordET.getText().toString())){
                resetPassword();
            }else{
                Utilities.showToast(PasswordResetActivity.this, "New Password and Confirm Password should be same");
            }

        }else{
            Utilities.showToast(PasswordResetActivity.this, "Enter all passwords");
        }

    }


    private void resetPassword() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("allow_change_pass", "yes");
        todayCasesMap.put("old_password", passwordET.getText().toString());
        todayCasesMap.put("new_password", newPasswordET.getText().toString());
        todayCasesMap.put("c_new_password", confirmPasswordET.getText().toString());
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(PasswordResetActivity.this).getString(Constants.USER_ID));

        GSONRequest<RegistrationBean> casesTodayBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.CHANGE_PASSWORD, RegistrationBean.class, todayCasesMap,

                new Response.Listener<RegistrationBean>() {
                    @Override
                    public void onResponse(RegistrationBean response) {
                        if (response.getSuccess() == 1) {
                            Utilities.showToast(PasswordResetActivity.this, "Password updated.");
                            finish();
                        }else{
                            Utilities.showToast(PasswordResetActivity.this, "Incorrect Password .");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }
}
