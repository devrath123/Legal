package com.example.devrathrathee.legal.beans;

import java.util.List;

public class JobBean {

    List<PostedJobList> posted_jobs_list;

    public List<PostedJobList> getPosted_jobs_list() {
        return posted_jobs_list;
    }

    public void setPosted_jobs_list(List<PostedJobList> posted_jobs_list) {
        this.posted_jobs_list = posted_jobs_list;
    }

    public static class PostedJobList{

        String  id,location,specialization,description,posted_date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSpecialization() {
            return specialization;
        }

        public void setSpecialization(String specialization) {
            this.specialization = specialization;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPosted_date() {
            return posted_date;
        }

        public void setPosted_date(String posted_date) {
            this.posted_date = posted_date;
        }
    }
}
