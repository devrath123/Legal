package com.example.devrathrathee.legal.beans;

import java.util.List;

public class FindInternBean {

    List<SearchIntern> search_intern;

    public List<SearchIntern> getSearch_intern() {
        return search_intern;
    }

    public void setSearch_intern(List<SearchIntern> search_intern) {
        this.search_intern = search_intern;
    }

    public static class SearchIntern{

        String id,name,apply_as,address,phone,email,language,description,enrollment_number,enrollment_date,resume;

        public String getResume() {
            return resume;
        }

        public void setResume(String resume) {
            this.resume = resume;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApply_as() {
            return apply_as;
        }

        public void setApply_as(String apply_as) {
            this.apply_as = apply_as;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEnrollment_number() {
            return enrollment_number;
        }

        public void setEnrollment_number(String enrollment_number) {
            this.enrollment_number = enrollment_number;
        }

        public String getEnrollment_date() {
            return enrollment_date;
        }

        public void setEnrollment_date(String enrollment_date) {
            this.enrollment_date = enrollment_date;
        }
    }
}
