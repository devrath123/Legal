package com.example.devrathrathee.legal.beans;

import java.util.List;

public class SearchLawyerBean {

    List<LawyerDetail> lawyer_detail_trans;

    public List<LawyerDetail> getLawyer_detail_trans() {
        return lawyer_detail_trans;
    }

    public void setLawyer_detail_trans(List<LawyerDetail> lawyer_detail_trans) {
        this.lawyer_detail_trans = lawyer_detail_trans;
    }

    public static class LawyerDetail {
        String send_lawyer_id, court_name, name, phone, email;

        public String getSend_lawyer_id() {
            return send_lawyer_id;
        }

        public void setSend_lawyer_id(String send_lawyer_id) {
            this.send_lawyer_id = send_lawyer_id;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
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
    }
}
