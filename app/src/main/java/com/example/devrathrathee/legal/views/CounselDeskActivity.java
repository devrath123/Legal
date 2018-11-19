package com.example.devrathrathee.legal.views;

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
import com.example.devrathrathee.legal.adapters.CounselDeskAdapter;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounselDeskActivity extends AppCompatActivity {

    RecyclerView counsel_desk_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsel_desk);

        counsel_desk_rv = findViewById(R.id.counsel_desk_rv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cases Diary");

        getCounselInfo();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getCounselInfo() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "select");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(CounselDeskActivity.this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(CounselDeskActivity.this).getString(Constants.USER_ID));

        // progressDialog.show();
        GSONRequest<CounselDeskBean> casesTodayBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.COUNSEL_DESK, CounselDeskBean.class, todayCasesMap,

                new Response.Listener<CounselDeskBean>() {
                    @Override
                    public void onResponse(CounselDeskBean response) {

                        if (response.getLawyer_counselor() != null) {
                            setAdapter(response.getLawyer_counselor());
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

    private void setAdapter(List<CounselDeskBean.CounselBean> cases_today) {
        CounselDeskAdapter casesAdapter = new CounselDeskAdapter(this, cases_today);
        counsel_desk_rv.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        counsel_desk_rv.setLayoutManager(mLayoutManager);
    }
}
