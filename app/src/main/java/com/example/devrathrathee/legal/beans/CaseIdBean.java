package com.example.devrathrathee.legal.beans;

import java.util.List;

public class CaseIdBean {

    List<CaseDetail> case_details;

    public List<CaseDetail> getCase_details() {
        return case_details;
    }

    public void setCase_details(List<CaseDetail> case_details) {
        this.case_details = case_details;
    }

    public static class CaseDetail{

        String case_id,case_number;

        public String getCase_id() {
            return case_id;
        }

        public void setCase_id(String case_id) {
            this.case_id = case_id;
        }

        public String getCase_number() {
            return case_number;
        }

        public void setCase_number(String case_number) {
            this.case_number = case_number;
        }
    }
}
