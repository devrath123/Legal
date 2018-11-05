package com.example.devrathrathee.legal.beans;

import java.util.List;

public class PracticeAreaBean {

    List<PracticeAreaDropdown> practice_area_dropdown;

    public List<PracticeAreaDropdown> getPractice_area_dropdown() {
        return practice_area_dropdown;
    }

    public void setPractice_area_dropdown(List<PracticeAreaDropdown> practice_area_dropdown) {
        this.practice_area_dropdown = practice_area_dropdown;
    }

    public static class PracticeAreaDropdown{

        String pa_name;

        public String getPa_name() {
            return pa_name;
        }

        public void setPa_name(String pa_name) {
            this.pa_name = pa_name;
        }
    }
}
