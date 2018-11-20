package com.example.devrathrathee.legal.beans;

import java.util.List;

public class MatterReceivedBean {

    List<MatterReceived> lawyer_matter_rec;
    List<MatterReceived> lawyer_matter_send;

    public List<MatterReceived> getLawyer_matter_sent() {
        return lawyer_matter_send;
    }

    public void setLawyer_matter_sent(List<MatterReceived> lawyer_matter_sent) {
        this.lawyer_matter_send = lawyer_matter_sent;
    }

    public List<MatterReceived> getLawyer_matter_rec() {
        return lawyer_matter_rec;
    }

    public void setLawyer_matter_rec(List<MatterReceived> lawyer_matter_rec) {
        this.lawyer_matter_rec = lawyer_matter_rec;
    }

    public static class MatterReceived {
        String lawyer_case_id, name, phone, email, court_name, court_number, judge_name, client_name,
                parties, stage, next_date, counsel_name;

        public String getLawyer_case_id() {
            return lawyer_case_id;
        }

        public void setLawyer_case_id(String lawyer_case_id) {
            this.lawyer_case_id = lawyer_case_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getJudge_name() {
            return judge_name;
        }

        public void setJudge_name(String judge_name) {
            this.judge_name = judge_name;
        }

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getParties() {
            return parties;
        }

        public void setParties(String parties) {
            this.parties = parties;
        }

        public String getStage() {
            return stage;
        }

        public void setStage(String stage) {
            this.stage = stage;
        }

        public String getNext_date() {
            return next_date;
        }

        public void setNext_date(String next_date) {
            this.next_date = next_date;
        }

        public String getCounsel_name() {
            return counsel_name;
        }

        public void setCounsel_name(String counsel_name) {
            this.counsel_name = counsel_name;
        }
    }
}
