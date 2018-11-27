package com.example.devrathrathee.legal.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LegalQueryBean {

    List<ManageQueryBean> lawyer_matter_rec;

    public List<ManageQueryBean> getLawyer_matter_rec() {
        return lawyer_matter_rec;
    }

    public void setLawyer_matter_rec(List<ManageQueryBean> lawyer_matter_rec) {
        this.lawyer_matter_rec = lawyer_matter_rec;
    }

    public static class ManageQueryBean implements Parcelable{
        String date, cust_name, cust_phone, cust_email, quote_type, cust_query, quote_status, quote_id, lawyer_name;

        protected ManageQueryBean(Parcel in) {
            date = in.readString();
            cust_name = in.readString();
            cust_phone = in.readString();
            cust_email = in.readString();
            quote_type = in.readString();
            cust_query = in.readString();
            quote_status = in.readString();
            quote_id = in.readString();
            lawyer_name = in.readString();
        }

        public static final Creator<ManageQueryBean> CREATOR = new Creator<ManageQueryBean>() {
            @Override
            public ManageQueryBean createFromParcel(Parcel in) {
                return new ManageQueryBean(in);
            }

            @Override
            public ManageQueryBean[] newArray(int size) {
                return new ManageQueryBean[size];
            }
        };

        public String getLawyer_name() {
            return lawyer_name;
        }

        public void setLawyer_name(String lawyer_name) {
            this.lawyer_name = lawyer_name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCust_name() {
            return cust_name;
        }

        public void setCust_name(String cust_name) {
            this.cust_name = cust_name;
        }

        public String getCust_phone() {
            return cust_phone;
        }

        public void setCust_phone(String cust_phone) {
            this.cust_phone = cust_phone;
        }

        public String getCust_email() {
            return cust_email;
        }

        public void setCust_email(String cust_email) {
            this.cust_email = cust_email;
        }

        public String getQuote_type() {
            return quote_type;
        }

        public void setQuote_type(String quote_type) {
            this.quote_type = quote_type;
        }

        public String getCust_query() {
            return cust_query;
        }

        public void setCust_query(String cust_query) {
            this.cust_query = cust_query;
        }

        public String getQuote_status() {
            return quote_status;
        }

        public void setQuote_status(String quote_status) {
            this.quote_status = quote_status;
        }

        public String getQuote_id() {
            return quote_id;
        }

        public void setQuote_id(String quote_id) {
            this.quote_id = quote_id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(date);
            parcel.writeString(cust_name);
            parcel.writeString(cust_phone);
            parcel.writeString(cust_email);
            parcel.writeString(quote_type);
            parcel.writeString(cust_query);
            parcel.writeString(quote_status);
            parcel.writeString(quote_id);
            parcel.writeString(lawyer_name);
        }
    }
}
