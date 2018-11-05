package com.example.devrathrathee.legal.beans;

import java.util.List;

public class AccountDetailBean {

    List<LawyerAccountDetail> lawyer_account_details;

    public List<LawyerAccountDetail> getLawyer_account_details() {
        return lawyer_account_details;
    }

    public void setLawyer_account_details(List<LawyerAccountDetail> lawyer_account_details) {
        this.lawyer_account_details = lawyer_account_details;
    }

    public static class LawyerAccountDetail {

        String lawyer_id, firm_id, name, firm_logo, email, phone, website, languages, about_us,
                address, address2, address3, practice_areas, practice_courts, availability, type;

        public String getLawyer_id() {
            return lawyer_id;
        }

        public void setLawyer_id(String lawyer_id) {
            this.lawyer_id = lawyer_id;
        }

        public String getFirm_id() {
            return firm_id;
        }

        public void setFirm_id(String firm_id) {
            this.firm_id = firm_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirm_logo() {
            return firm_logo;
        }

        public void setFirm_logo(String firm_logo) {
            this.firm_logo = firm_logo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getLanguages() {
            return languages;
        }

        public void setLanguages(String languages) {
            this.languages = languages;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getAddress3() {
            return address3;
        }

        public void setAddress3(String address3) {
            this.address3 = address3;
        }

        public String getPractice_areas() {
            return practice_areas;
        }

        public void setPractice_areas(String practice_areas) {
            this.practice_areas = practice_areas;
        }

        public String getPractice_courts() {
            return practice_courts;
        }

        public void setPractice_courts(String practice_courts) {
            this.practice_courts = practice_courts;
        }

        public String getAvailability() {
            return availability;
        }

        public void setAvailability(String availability) {
            this.availability = availability;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
