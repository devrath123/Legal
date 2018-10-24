package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class TodayCasesFragment extends Fragment {


    ProgressDialog progressDialog;
    RecyclerView todayCasesRV;

    public TodayCasesFragment() {
    }

    public static TodayCasesFragment newInstance() {
        TodayCasesFragment fragment = new TodayCasesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today_cases, container, false);

        todayCasesRV = view.findViewById(R.id.today_cases_rv);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");

        getTodayCases();
        return view;
    }

    private void getTodayCases() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "select");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(getActivity()).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(getActivity()).getString(Constants.USER_ID));

        progressDialog.show();
        GSONRequest<CaseBean> casesTodayBeanGSONRequest = new GSONRequest<CaseBean>(Request.Method.POST, API.BASE_URL + API.CASES_TODAY, CaseBean.class, todayCasesMap,
                new Response.Listener<CaseBean>() {
                    @Override
                    public void onResponse(CaseBean response) {
                        progressDialog.dismiss();
                        if (response.getCases_today() != null) {
                            setAdapter(response.getCases_today());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(getActivity()).add(casesTodayBeanGSONRequest);
    }

    private void setAdapter(List<CaseBean.CasesToday> cases_today) {
        CasesAdapter casesAdapter = new CasesAdapter(getActivity(), cases_today);
        todayCasesRV.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        todayCasesRV.setLayoutManager(mLayoutManager);
    }

}
