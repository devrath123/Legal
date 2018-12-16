package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.example.devrathrathee.legal.utils.Connectivity;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.GSONRequest;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyCasesFragment extends Fragment {

    RecyclerView recyclerView;

    public WeeklyCasesFragment() {
    }

    public static WeeklyCasesFragment newInstance() {
        WeeklyCasesFragment fragment = new WeeklyCasesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_cases, container, false);

        recyclerView = view.findViewById(R.id.weekly_cases_rv);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.add_case_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddCaseActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Connectivity.isConnected(getActivity())) {
            getWeeklyCases();
        }else{
            Utilities.internetConnectionError(getActivity());
        }
    }

    private void getWeeklyCases() {
        Map<String, String> todayCasesMap = new HashMap<>();
        todayCasesMap.put("action", "select");
        todayCasesMap.put("user_type", SharedPreferenceManager.getInstance(getActivity()).getString(Constants.USER_TYPE));
        todayCasesMap.put("lawyer_id", SharedPreferenceManager.getInstance(getActivity()).getString(Constants.USER_ID));

        GSONRequest<CaseBean> casesTodayBeanGSONRequest = new GSONRequest<CaseBean>(Request.Method.POST, API.BASE_URL + API.CASES_WEEKLY, CaseBean.class, todayCasesMap,
                new Response.Listener<CaseBean>() {
                    @Override
                    public void onResponse(CaseBean response) {
                        if (response.getCases_weekly() != null) {
                            setAdapter(response.getCases_weekly());
                        }else{
                            setAdapter(new ArrayList<CaseBean.CasesToday>());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        casesTodayBeanGSONRequest.setShouldCache(false);
        Utilities.getRequestQueue(getActivity()).add(casesTodayBeanGSONRequest);
    }

    private void setAdapter(List<CaseBean.CasesToday> cases_today) {
        CasesAdapter casesAdapter = new CasesAdapter(getActivity(), cases_today);
        recyclerView.setAdapter(casesAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        casesAdapter.notifyDataSetChanged();
    }
}
