package com.example.devrathrathee.legal.beans;

import java.util.List;

public class SearchCounselBean {

    List<CounselResult> counsel_result;

    public List<CounselResult> getCounsel_result() {
        return counsel_result;
    }

    public void setCounsel_result(List<CounselResult> counsel_result) {
        this.counsel_result = counsel_result;
    }

    public static class CounselResult{
        String counsel_id,counsel_name,counsel_designation,counsel_phone,counsel_email,
                counsel_address,counsel_areaofpractice,counsel_description;

        public String getCounsel_id() {
            return counsel_id;
        }

        public void setCounsel_id(String counsel_id) {
            this.counsel_id = counsel_id;
        }

        public String getCounsel_name() {
            return counsel_name;
        }

        public void setCounsel_name(String counsel_name) {
            this.counsel_name = counsel_name;
        }

        public String getCounsel_designation() {
            return counsel_designation;
        }

        public void setCounsel_designation(String counsel_designation) {
            this.counsel_designation = counsel_designation;
        }

        public String getCounsel_phone() {
            return counsel_phone;
        }

        public void setCounsel_phone(String counsel_phone) {
            this.counsel_phone = counsel_phone;
        }

        public String getCounsel_email() {
            return counsel_email;
        }

        public void setCounsel_email(String counsel_email) {
            this.counsel_email = counsel_email;
        }

        public String getCounsel_address() {
            return counsel_address;
        }

        public void setCounsel_address(String counsel_address) {
            this.counsel_address = counsel_address;
        }

        public String getCounsel_areaofpractice() {
            return counsel_areaofpractice;
        }

        public void setCounsel_areaofpractice(String counsel_areaofpractice) {
            this.counsel_areaofpractice = counsel_areaofpractice;
        }

        public String getCounsel_description() {
            return counsel_description;
        }

        public void setCounsel_description(String counsel_description) {
            this.counsel_description = counsel_description;
        }
    }
}
