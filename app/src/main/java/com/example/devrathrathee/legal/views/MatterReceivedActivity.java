package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.MatterReceivedAdapter;
import com.example.devrathrathee.legal.beans.CaseBean;
import com.example.devrathrathee.legal.beans.MatterReceivedBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MatterReceivedActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView matter_received_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter_received);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matter_received_rv = findViewById(R.id.matter_received_rv);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Matter Received");

        getMatterReceived();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getMatterReceived() {

        String url = API.BASE_URL + API.MATTER_RECEIVED + "?action=select&user_type=" + SharedPreferenceManager.getInstance(MatterReceivedActivity.this).getString(Constants.USER_TYPE) +
                "&lawyer_id=" + SharedPreferenceManager.getInstance(MatterReceivedActivity.this).getString(Constants.USER_ID);

        progressDialog.show();
        GSONRequest<MatterReceivedBean> casesTodayBeanGSONRequest = new GSONRequest<MatterReceivedBean>(Request.Method.GET, url, MatterReceivedBean.class, null,

                new Response.Listener<MatterReceivedBean>() {
                    @Override
                    public void onResponse(MatterReceivedBean response) {
                        progressDialog.dismiss();
                        if (response.getLawyer_matter_rec().size() > 0) {
                            setMessageReceived(response.getLawyer_matter_rec());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(this).add(casesTodayBeanGSONRequest);
    }

    private void setMessageReceived(List<MatterReceivedBean.MatterReceived> matterReceivedList) {
        MatterReceivedAdapter matterReceivedAdapter = new MatterReceivedAdapter(MatterReceivedActivity.this, matterReceivedList);
        matter_received_rv.setAdapter(matterReceivedAdapter);
        matterReceivedAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MatterReceivedActivity.this);
        matter_received_rv.setLayoutManager(mLayoutManager);
    }
}
