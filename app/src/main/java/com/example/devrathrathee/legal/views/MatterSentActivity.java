package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
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
import com.example.devrathrathee.legal.adapters.MatterReceivedAdapter;
import com.example.devrathrathee.legal.adapters.MatterSentAdapter;
import com.example.devrathrathee.legal.beans.MatterReceivedBean;
import com.example.devrathrathee.legal.utils.API;
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.List;

public class MatterSentActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    RecyclerView matter_sent_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter_sent);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matter_sent_rv = findViewById(R.id.matter_sent_rv);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Matter Sent");
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Connectivity.isConnected(MatterSentActivity.this)) {
            getMatterSent();
        } else {
            Utilities.internetConnectionError(MatterSentActivity.this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getMatterSent() {

        String url = API.BASE_URL + API.MATTER_SENT + "?action=select&user_type=" + SharedPreferenceManager.getInstance(MatterSentActivity.this).getString(Constants.USER_TYPE) +
                "&lawyer_id=" + SharedPreferenceManager.getInstance(MatterSentActivity.this).getString(Constants.USER_ID);

        progressDialog.show();
        GSONRequest<MatterReceivedBean> casesTodayBeanGSONRequest = new GSONRequest<MatterReceivedBean>(Request.Method.GET, url, MatterReceivedBean.class, null,

                new Response.Listener<MatterReceivedBean>() {
                    @Override
                    public void onResponse(MatterReceivedBean response) {
                        progressDialog.dismiss();
                        if (response.getLawyer_matter_sent() != null && response.getLawyer_matter_sent().size() > 0) {
                            setMessageReceived(response.getLawyer_matter_sent());
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
        MatterSentAdapter matterReceivedAdapter = new MatterSentAdapter(MatterSentActivity.this, matterReceivedList);
        matter_sent_rv.setAdapter(matterReceivedAdapter);
        matterReceivedAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MatterSentActivity.this);
        matter_sent_rv.setLayoutManager(mLayoutManager);
    }
}
