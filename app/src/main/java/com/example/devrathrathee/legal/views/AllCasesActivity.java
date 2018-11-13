package com.example.devrathrathee.legal.views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.CasesAdapter;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class AllCasesActivity extends AppCompatActivity {

    RecyclerView allCasesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cases);

        allCasesRV = findViewById(R.id.all_cases_rv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("All Cases");

        getTodayCases();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTodayCases() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "select");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(AllCasesActivity.this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(AllCasesActivity.this).getString(Constants.USER_ID));

        // progressDialog.show();
        GSONRequest<CaseBean> casesTodayBeanGSONRequest = new GSONRequest<CaseBean>(Request.Method.POST, API.BASE_URL + API.CASES_ALL, CaseBean.class, todayCasesMap,

                new Response.Listener<CaseBean>() {
                    @Override
                    public void onResponse(CaseBean response) {
                        //   progressDialog.dismiss();
                        if (response.getCase_all() != null) {
                            setAdapter(response.getCase_all());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   progressDialog.dismiss();
            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }

    private void setAdapter(List<CaseBean.CasesToday> cases_today) {
        CasesAdapter casesAdapter = new CasesAdapter(this, cases_today);
        allCasesRV.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        allCasesRV.setLayoutManager(mLayoutManager);
    }
}
