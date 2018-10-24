package com.example.devrathrathee.legal.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CaseBean {

    List<CasesToday> cases_tomorrow;
    List<CasesToday> cases_today;
    List<CasesToday> cases_weekly;

    public List<CasesToday> getCases_weekly() {
        return cases_weekly;
    }

    public void setCases_weekly(List<CasesToday> cases_weekly) {
        this.cases_weekly = cases_weekly;
    }

    public List<CasesToday> getCases_tomorrow() {
        return cases_tomorrow;
    }

    public void setCases_tomorrow(List<CasesToday> cases_tomorrow) {
        this.cases_tomorrow = cases_tomorrow;
    }

    public List<CasesToday> getCases_today() {
        return cases_today;
    }

    public void setCases_today(List<CasesToday> cases_today) {
        this.cases_today = cases_today;
    }

    public static class CasesToday implements Parcelable{
        String case_id,next_date,court_name,court_number,case_number,judge_name,party_a,party_b,stage,client_name,category,firm_name,client_phone;

        protected CasesToday(Parcel in) {
            case_id = in.readString();
            next_date = in.readString();
            court_name = in.readString();
            court_number = in.readString();
            case_number = in.readString();
            judge_name = in.readString();
            party_a = in.readString();
            party_b = in.readString();
            stage = in.readString();
            client_name = in.readString();
            category = in.readString();
            firm_name = in.readString();
            client_phone = in.readString();
        }

        public static final Creator<CasesToday> CREATOR = new Creator<CasesToday>() {
            @Override
            public CasesToday createFromParcel(Parcel in) {
                return new CasesToday(in);
            }

            @Override
            public CasesToday[] newArray(int size) {
                return new CasesToday[size];
            }
        };

        public String getCase_id() {
            return case_id;
        }

        public void setCase_id(String case_id) {
            this.case_id = case_id;
        }

        public String getNext_date() {
            return next_date;
        }

        public void setNext_date(String next_date) {
            this.next_date = next_date;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
        }

        public String getCourt_number() {
            return court_number;
        }

        public void setCourt_number(String court_number) {
            this.court_number = court_number;
        }

        public String getCase_number() {
            return case_number;
        }

        public void setCase_number(String case_number) {
            this.case_number = case_number;
        }

        public String getJudge_name() {
            return judge_name;
        }

        public void setJudge_name(String judge_name) {
            this.judge_name = judge_name;
        }

        public String getParty_a() {
            return party_a;
        }

        public void setParty_a(String party_a) {
            this.party_a = party_a;
        }

        public String getParty_b() {
            return party_b;
        }

        public void setParty_b(String party_b) {
            this.party_b = party_b;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getFirm_name() {
            return firm_name;
        }

        public void setFirm_name(String firm_name) {
            this.firm_name = firm_name;
        }

        public String getClient_phone() {
            return client_phone;
        }

        public void setClient_phone(String client_phone) {
            this.client_phone = client_phone;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(case_id);
            dest.writeString(next_date);
            dest.writeString(court_name);
            dest.writeString(court_number);
            dest.writeString(case_number);
            dest.writeString(judge_name);
            dest.writeString(party_a);
            dest.writeString(party_b);
            dest.writeString(stage);
            dest.writeString(client_name);
            dest.writeString(category);
            dest.writeString(firm_name);
            dest.writeString(client_phone);
        }
    }

}
