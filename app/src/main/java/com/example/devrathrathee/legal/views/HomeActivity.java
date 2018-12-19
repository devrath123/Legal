package com.example.devrathrathee.legal.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.adapters.CasesPagerAdapter;
import com.example.devrathrathee.legal.utils.Constants;
import com.example.devrathrathee.legal.utils.SharedPreferenceManager;
import com.example.devrathrathee.legal.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.cases_view_pager)
    ViewPager casesViewPager;

    NavigationView navigationView;
    CasesPagerAdapter casesPagerAdapter;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Legal");
        //  setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_name_text_view);
        userNameTextView.setText(SharedPreferenceManager.getInstance(HomeActivity.this).getString(Constants.USER_NAME));

        navigationView.getMenu().getItem(0).setActionView(R.layout.menu_layout);
        navigationView.getMenu().getItem(2).setActionView(R.layout.menu_layout);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getPageTitles().get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(getPageTitles().get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(getPageTitles().get(2)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        casesPagerAdapter = new CasesPagerAdapter(getSupportFragmentManager(), getPageTitles());
        casesViewPager.setAdapter(casesPagerAdapter);
        tabLayout.setupWithViewPager(casesViewPager);

        reduceMarginsInTabs(tabLayout, 30);

    }

    public static void reduceMarginsInTabs(TabLayout tabLayout, int marginOffset) {

        View tabStrip = tabLayout.getChildAt(0);
        if (tabStrip instanceof ViewGroup) {
            ViewGroup tabStripGroup = (ViewGroup) tabStrip;
            for (int i = 0; i < ((ViewGroup) tabStrip).getChildCount(); i++) {
                View tabView = tabStripGroup.getChildAt(i);
                if (tabView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).leftMargin = marginOffset;
                    ((ViewGroup.MarginLayoutParams) tabView.getLayoutParams()).rightMargin = marginOffset;
                }
            }

            tabLayout.requestLayout();
        }
    }

    private List<String> getPageTitles() {
        List<String> pageTitleList = new ArrayList<>();
        pageTitleList.add(Utilities.getTodayDate());
        pageTitleList.add(Utilities.getTomorrowDate());
        pageTitleList.add(Utilities.getWeeklyDates());

        return pageTitleList;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_cases:
                navigationView.getMenu().setGroupVisible(R.id.cases_options, true);
                navigationView.getMenu().setGroupVisible(R.id.main_option, false);
                break;

            case R.id.nav_upcoming_cases:
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.nav_all_cases:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, AllCasesActivity.class));
                break;

            case R.id.nav_calendar:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, CalendarActivity.class));
                break;

            case R.id.nav_professional_fees:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, ProfessionalFeeActivity.class));
                break;

            case R.id.nav_option_cases:
                navigationView.getMenu().setGroupVisible(R.id.cases_options, false);
                navigationView.getMenu().setGroupVisible(R.id.main_option, true);
                break;

            case R.id.nav_lawyer_support_desk:
                navigationView.getMenu().setGroupVisible(R.id.main_option, false);
                navigationView.getMenu().setGroupVisible(R.id.lawyer_support_options, true);
                break;

            case R.id.nav_option_lawyer_support_desk:
                navigationView.getMenu().setGroupVisible(R.id.main_option, true);
                navigationView.getMenu().setGroupVisible(R.id.lawyer_support_options, false);
                break;

            case R.id.nav_matter_received:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, MatterReceivedActivity.class));
                break;

            case R.id.nav_matter_sent:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, MatterSentActivity.class));
                break;

            case R.id.nav_password_reset:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, PasswordResetActivity.class));
                break;

            case R.id.nav_counsel_desk:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, CounselDeskActivity.class));
                break;

            case R.id.nav_post_jobs:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, JobsListActivity.class));
                break;

            case R.id.nav_find_interns:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, FindInternActivity.class));
                break;

            case R.id.nav_legal_queries:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, LegalQueriesActivity.class));
                break;

            case R.id.nav_account_details:
                drawer.closeDrawer(GravityCompat.START);
                startActivity(new Intent(HomeActivity.this, AccountDetailsActivity.class));
                break;

            case R.id.nav_logout:
                SharedPreferenceManager.getInstance(HomeActivity.this).putString(Constants.FIRM_ID, "");
                SharedPreferenceManager.getInstance(HomeActivity.this).putString(Constants.USER_TYPE, "");
                SharedPreferenceManager.getInstance(HomeActivity.this).putString(Constants.USER_NAME, "");
                SharedPreferenceManager.getInstance(HomeActivity.this).putString(Constants.USER_ID, "");
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                break;
        }

        return false;
    }
}
