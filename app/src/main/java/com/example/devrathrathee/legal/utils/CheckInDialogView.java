package com.example.devrathrathee.legal.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.devrathrathee.legal.R;
import com.example.devrathrathee.legal.views.CalendarActivity;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class CheckInDialogView {

    private TextView currentMonth;
    private ImageView prevMonth, nextMonth;
    private GridView calendarView;
    private LinearLayout calendarLayout;
    private GridCellAdapter adapter;
    private Calendar calendar;
    private int month, year, current_month, current_year, current_day;
    private Context context;
    private DateSelectListener dateSelectListener;
    private ArrayList<String> monthsNameArrayList = new ArrayList<>();
    boolean isIndex;

    public CheckInDialogView(Context context, DateSelectListener dateSelectListener, Boolean isIndex) {
        this.dateSelectListener = dateSelectListener;
        this.context = context;
        this.isIndex = isIndex;
    }

    private View view;

    @SuppressLint("SetTextI18n")
    public View Create_CheckInView() {
        view = LayoutInflater.from(context).inflate(R.layout.my_calendar_view, null, false);
        calendarLayout = view.findViewById(R.id.calender_linearLayout);

        calendarLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));

        calendar = Calendar.getInstance();
        current_month = calendar.get(Calendar.MONTH) + 1;
        current_year = calendar.get(Calendar.YEAR);
        current_day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        month = current_month;
        calendar.set(year, month - 1, 1);

        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] dayNames = symbols.getShortWeekdays();
        ArrayList<String> days = new ArrayList<>();
        for (String s : dayNames) {
            if (!s.trim().isEmpty()) {
                days.add(s);
            }
        }

        String[] months_Names = symbols.getMonths();
        for (String s : months_Names) {
            if (!s.trim().isEmpty()) {
                monthsNameArrayList.add(s);
            }
        }

        displayDays(days);

        prevMonth = (ImageView) view.findViewById(R.id.prevMonth);
        prevMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_month == 12)
                    if (month == 1 && current_year + 1 == year)
                        prevMonth.setVisibility(View.INVISIBLE);
                if (current_month >= month - 1 && current_year >= year)
                    prevMonth.setVisibility(View.INVISIBLE);
                getPrevMonth();
            }
        });

        currentMonth = view.findViewById(R.id.currentMonth);

        //title for the current month
        currentMonth.setText(monthsNameArrayList.get(month-1) + " " + year);

        nextMonth = view.findViewById(R.id.nextMonth);
        nextMonth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                prevMonth.setVisibility(View.VISIBLE);
                getNextMonth();
            }
        });

        if (current_year >= year) {
            {
                if (current_month >= month) {
                    prevMonth.setVisibility(View.INVISIBLE);
                }
            }
        }

        calendarView = view.findViewById(R.id.calendarGridView);

        // Initialised
        adapter = new GridCellAdapter(context, month, year);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        return view;
    }

    private void getPrevMonth() {
        if (month <= 1) {
            month = 12;
            year--;
        } else {
            month--;
        }

        setGridCellAdapterToDate(month, year);
    }

    private void getNextMonth() {
        if (month > 11) {
            month = 1;
            year++;
        } else {
            month++;
        }

        setGridCellAdapterToDate(month, year);
    }

    private void displayDays(ArrayList<String> days) {

        ArrayList<TextView> daysArrayList = new ArrayList<>();
        daysArrayList.add((TextView) view.findViewById(R.id.day1));
        daysArrayList.add((TextView) view.findViewById(R.id.day2));
        daysArrayList.add((TextView) view.findViewById(R.id.day3));
        daysArrayList.add((TextView) view.findViewById(R.id.day4));
        daysArrayList.add((TextView) view.findViewById(R.id.day5));
        daysArrayList.add((TextView) view.findViewById(R.id.day6));
        daysArrayList.add((TextView) view.findViewById(R.id.day7));

        for (int i = 0; i < daysArrayList.size(); i++) {
            daysArrayList.get(i).setText(days.get(i));
        }
    }

    @SuppressLint("SetTextI18n")
    private void setGridCellAdapterToDate(int month, int year) {
        adapter = new GridCellAdapter(context, month, year);
        calendar.set(year, month - 1, current_day);
        currentMonth.setText(monthsNameArrayList.get(month - 1) + " " + new SimpleDateFormat("yyyy").format(calendar.getTime()));
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        ((CalendarActivity)context).getTodayCases();
    }

    public String getMonth(){
        return month + "";
    }

    public String getYear(){
        return year + "";
    }

    private List<String> list = new ArrayList<>();
    private final String[] weekdays = new String[]{"Sun", "Mon", "Tue",
            "Wed", "Thu", "Fri", "Sat"};
    private final String[] months = {"January", "February", "March",
            "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
            31, 30, 31};

    // Inner Class
    private class GridCellAdapter extends BaseAdapter implements OnClickListener {
        private static final String tag = "GridCellAdapter";
        private final Context _context;

        private static final int DAY_OFFSET = 1;
        private int daysInMonth;
        private int currentDayOfMonth;
        private TextView calendarDatGridCellTextView;

        // Days in Current Month
        private GridCellAdapter(Context context,
                                int month, int year) {
            super();
            this._context = context;

            list = new ArrayList<>();
            setCurrentDayOfMonth(current_day);


            // Print Month
            printMonth(month, year);
        }

        private String getMonthAsString(int i) {
            return months[i];
        }

        private String getWeekDayAsString(int i) {
            return weekdays[i];
        }

        private int getNumberOfDaysOfMonth(int i) {
            return daysOfMonth[i];
        }

        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        // prints month.
        private void printMonth(int months, int years) {
            int trailingSpaces, daysInPrevMonth, prevMonth, prevYear, nextMonth, nextYear;
            int currentMonth = months - 1;
            daysInMonth = getNumberOfDaysOfMonth(currentMonth);


            GregorianCalendar calendar = new GregorianCalendar();
            calendar.set(years, currentMonth, 1);


            if (currentMonth == 11) {
                prevMonth = currentMonth - 1;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 0;
                prevYear = years;
                nextYear = years + 1;

            } else if (currentMonth == 0) {
                prevMonth = 11;
                prevYear = years - 1;
                nextYear = years;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
                nextMonth = 1;

            } else {
                prevMonth = currentMonth - 1;
                nextMonth = currentMonth + 1;
                nextYear = years;
                prevYear = years;
                daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);

            }

            int currentWeekDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            trailingSpaces = currentWeekDay;


            if (calendar.isLeapYear(calendar.get(Calendar.YEAR)))
                if (months == 2)
                    ++daysInMonth;
                else if (months == 3)
                    ++daysInPrevMonth;

            // Trailing Month days
            if (trailingSpaces == 0)//I added for constant height
            {
                for (int i = 0; i < 7; i++) {

                    list.add(String
                            .valueOf((daysInPrevMonth - 7 + DAY_OFFSET)
                                    + i)
                            + "-GREY"
                            + "-"
                            + getMonthAsString(prevMonth)
                            + "-"
                            + prevYear);
                }
            } else {
                for (int i = 0; i < trailingSpaces; i++) {

                    list.add(String
                            .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                                    + i)
                            + "-GREY"
                            + "-"
                            + getMonthAsString(prevMonth)
                            + "-"
                            + prevYear);
                }
            }


            // Current Month Days
            for (int i = 1; i <= daysInMonth; i++) {

                if (i == getCurrentDayOfMonth() && current_month == months && current_year == years) {
                    list.add(String.valueOf(i) + "-BLUE" + "-"
                            + getMonthAsString(currentMonth) + "-" + years);
                } else if (i < getCurrentDayOfMonth() && current_month == months && current_year == years) {
                    list.add(String.valueOf(i) + "-GREY" + "-"
                            + getMonthAsString(currentMonth) + "-" + years);
                } else {
                    list.add(String.valueOf(i) + "-WHITE" + "-"
                            + getMonthAsString(currentMonth) + "-" + years);
                }
            }


            // Leading Month days
            // list.size() %7 i have replace for constant height
            int limit1 = 42 - list.size();
            for (int i = 0; i < limit1; i++) {

                list.add(String.valueOf(i + 1) + "-GREY" + "-"
                        + getMonthAsString(nextMonth) + "-" + nextYear);
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) _context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.screen_gridcell, parent, false);
            }

            // Get a reference to the Day gridCell
            calendarDatGridCellTextView = (TextView) row.findViewById(R.id.calendarDatGridCellTextView);
            calendarDatGridCellTextView.setOnClickListener(this);

            // ACCOUNT FOR SPACING

            String[] dayColor = list.get(position).split("-");
            String theDay = dayColor[0];
            String theMonth = dayColor[2];
            String theYear = dayColor[3];

            // Set the Day GridCell
            calendarDatGridCellTextView.setText(theDay);
            calendarDatGridCellTextView.setTag(theDay + "-" + theMonth + "-" + theYear);


            if (dayColor[1].equals("GREY")) {
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, R.color.light_gray_text_color));
                calendarDatGridCellTextView.setTag(R.id.calendarDatGridCellTextView, false);
            }
            if (dayColor[1].equals("WHITE")) {
              /*  if(isIndex)
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, color.black));
                else*/
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, R.color.black));
                calendarDatGridCellTextView.setTag(R.id.calendarDatGridCellTextView, true);
            }
            if (dayColor[1].equals("BLUE")) {
                calendarDatGridCellTextView.setTag(R.id.calendarDatGridCellTextView, true);
                calendarDatGridCellTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            }

            calendarDatGridCellTextView.setBackgroundResource(0);

            return row;
        }


        @Override
        public void onClick(View view) {
            if ((Boolean) view.getTag(R.id.calendarDatGridCellTextView)) {
              //  view.setBackgroundResource(R.drawable.singledate_bg);
                notifyDataSetChanged();
                if (dateSelectListener != null)
                    dateSelectListener.setCheckInDate(getSelectedDate((String) view.getTag()));

            }
        }

        private String getSelectedDate(String date)
        {
            String[] dateParts = date.split("-");
            int selectedMonth=0;
            for(int i=0;i<months.length;i++)
            {
                if(months[i].equals(dateParts[1])){
                    selectedMonth = i+1;
                    break;
                }
            }

            return dateParts[2] + "-" + selectedMonth +"-" + dateParts[0];
        }

        private int getCurrentDayOfMonth() {
            return currentDayOfMonth;
        }

        private void setCurrentDayOfMonth(int currentDayOfMonth) {
            this.currentDayOfMonth = currentDayOfMonth;
        }
    }
}
