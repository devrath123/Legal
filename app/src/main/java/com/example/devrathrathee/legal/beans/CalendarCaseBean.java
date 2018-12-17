package com.example.devrathrathee.legal.beans;

import java.util.List;

public class CalendarCaseBean {

    List<LawyerCalendar> lawyer_calender;

    public List<LawyerCalendar> getLawyer_calender() {
        return lawyer_calender;
    }

    public void setLawyer_calender(List<LawyerCalendar> lawyer_calender) {
        this.lawyer_calender = lawyer_calender;
    }

    public static class LawyerCalendar{
        String  id,title,date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
