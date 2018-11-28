package com.example.devrathrathee.legal.views;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.utils.CheckInDialogView;
import com.example.devrathrathee.legal.utils.DateSelectListener;

public class CalendarActivity extends AppCompatActivity implements DateSelectListener{

    ProgressDialog progressDialog;
    RelativeLayout calenderRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Calendar");

        calenderRelativeLayout = findViewById(R.id.calenderRelativeLayout);
        CheckInDialogView checkInDialogView = new CheckInDialogView(this, this, true);
        calenderRelativeLayout.addView(checkInDialogView.Create_CheckInView());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setCheckInDate(String checkInDate) {

    }
}
