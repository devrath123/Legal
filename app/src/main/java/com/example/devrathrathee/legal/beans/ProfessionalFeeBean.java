package com.example.devrathrathee.legal.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ProfessionalFeeBean {

    List<PaymentDetail> client_payment_detail;

    public List<PaymentDetail> getClient_payment_detail() {
        return client_payment_detail;
    }

    public void setClient_payment_detail(List<PaymentDetail> client_payment_detail) {
        this.client_payment_detail = client_payment_detail;
    }

    public static class PaymentDetail implements Parcelable{
        String case_id, case_number, client_name, court_name, category, client_phone, paymt_status;

        protected PaymentDetail(Parcel in) {
            case_id = in.readString();
            case_number = in.readString();
            client_name = in.readString();
            court_name = in.readString();
            category = in.readString();
            client_phone = in.readString();
            paymt_status = in.readString();
        }

        public static final Creator<PaymentDetail> CREATOR = new Creator<PaymentDetail>() {
            @Override
            public PaymentDetail createFromParcel(Parcel in) {
                return new PaymentDetail(in);
            }

            @Override
            public PaymentDetail[] newArray(int size) {
                return new PaymentDetail[size];
            }
        };

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

        public String getClient_name() {
            return client_name;
        }

        public void setClient_name(String client_name) {
            this.client_name = client_name;
        }

        public String getCourt_name() {
            return court_name;
        }

        public void setCourt_name(String court_name) {
            this.court_name = court_name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getClient_phone() {
            return client_phone;
        }

        public void setClient_phone(String client_phone) {
            this.client_phone = client_phone;
        }

        public String getPaymt_status() {
            return paymt_status;
        }

        public void setPaymt_status(String paymt_status) {
            this.paymt_status = paymt_status;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(case_id);
            parcel.writeString(case_number);
            parcel.writeString(client_name);
            parcel.writeString(court_name);
            parcel.writeString(category);
            parcel.writeString(client_phone);
            parcel.writeString(paymt_status);
        }
    }
}
