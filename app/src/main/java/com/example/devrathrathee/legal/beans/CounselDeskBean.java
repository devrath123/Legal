package com.example.devrathrathee.legal.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CounselDeskBean {

    List<CounselBean> lawyer_counselor;

    public List<CounselBean> getLawyer_counselor() {
        return lawyer_counselor;
    }

    public void setLawyer_counselor(List<CounselBean> lawyer_counselor) {
        this.lawyer_counselor = lawyer_counselor;
    }

    public static class CounselBean implements Parcelable{

        String  counsel_name,judge_name,court_name,court_hearing_date,Parties,conference_place,conference_date,
                conference_time, status;

        protected CounselBean(Parcel in) {
            counsel_name = in.readString();
            judge_name = in.readString();
            court_name = in.readString();
            court_hearing_date = in.readString();
            Parties = in.readString();
            conference_place = in.readString();
            conference_date = in.readString();
            conference_time = in.readString();
            status = in.readString();
        }

        public static final Creator<CounselBean> CREATOR = new Creator<CounselBean>() {
            @Override
            public CounselBean createFromParcel(Parcel in) {
                return new CounselBean(in);
            }

            @Override
            public CounselBean[] newArray(int size) {
                return new CounselBean[size];
            }
        };

        public String getCounsel_name() {
            return counsel_name;
        }

        public void setCounsel_name(String counsel_name) {
            this.counsel_name = counsel_name;
        }

        public String getJudge_name() {
            return judge_name;
        }

        public void setJudge_name(String judge_name) {
            this.judge_name = judge_name;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
        }

        public String getHearing_date() {
            return court_hearing_date;
        }

        public void setHearing_date(String hearing_date) {
            this.court_hearing_date = hearing_date;
        }

        public String getParties() {
            return Parties;
        }

        public void setParties(String parties) {
            Parties = parties;
        }

        public String getConference_place() {
            return conference_place;
        }

        public void setConference_place(String conference_place) {
            this.conference_place = conference_place;
        }

        public String getConference_date() {
            return conference_date;
        }

        public void setConference_date(String conference_date) {
            this.conference_date = conference_date;
        }

        public String getConference_time() {
            return conference_time;
        }

        public void setConference_time(String conference_time) {
            this.conference_time = conference_time;
        }

        public String getConference_status() {
            return status;
        }

        public void setConference_status(String conference_status) {
            this.status = conference_status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(counsel_name);
            parcel.writeString(judge_name);
            parcel.writeString(court_name);
            parcel.writeString(court_hearing_date);
            parcel.writeString(Parties);
            parcel.writeString(conference_place);
            parcel.writeString(conference_date);
            parcel.writeString(conference_time);
            parcel.writeString(status);
        }
    }
}
