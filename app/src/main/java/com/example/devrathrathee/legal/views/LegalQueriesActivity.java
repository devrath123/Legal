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
import com.example.devrathrathee.legal.adapters.CounselDeskAdapter;
import com.example.devrathrathee.legal.adapters.LegalQueryAdapter;
import com.example.devrathrathee.legal.beans.CounselDeskBean;
import com.example.devrathrathee.legal.beans.LegalQueryBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegalQueriesActivity extends AppCompatActivity {

    RecyclerView legal_queries_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_queries);


        legal_queries_rv = findViewById(R.id.legal_queries_rv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Manage Queries");

        getLegalQueries();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getLegalQueries() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "select");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(LegalQueriesActivity.this).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(LegalQueriesActivity.this).getString(Constants.USER_ID));

        // progressDialog.show();
        GSONRequest<LegalQueryBean> casesTodayBeanGSONRequest = new GSONRequest<>(Request.Method.POST, API.BASE_URL + API.LEGAL_QUERIES, LegalQueryBean.class, todayCasesMap,

                new Response.Listener<LegalQueryBean>() {
                    @Override
                    public void onResponse(LegalQueryBean response) {

                        if (response.getLawyer_matter_rec() != null) {
                            setAdapter(response.getLawyer_matter_rec());
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

    private void setAdapter(List<LegalQueryBean.ManageQueryBean> cases_today) {
        LegalQueryAdapter casesAdapter = new LegalQueryAdapter(this, cases_today);
        legal_queries_rv.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        legal_queries_rv.setLayoutManager(mLayoutManager);
    }
}
