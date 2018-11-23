package com.example.devrathrathee.legal.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfessionalFeeBean {

    List<PaymentDetail> client_payment_detail;

    public List<PaymentDetail> getClient_payment_detail() {
        return client_payment_detail;
    }

    public void setClient_payment_detail(List<PaymentDetail> client_payment_detail) {
        this.client_payment_detail = client_payment_detail;
    }

    public static class PaymentDetail implements Parcelable {
        String case_id, case_number, client_name, court_name, category, client_phone, paymt_status;
        List<PaymentId> payment_id = Collections.emptyList();
        List<PaymentDate> payment_date = Collections.emptyList();
        List<PaymentMode> payment_mode = Collections.emptyList();
        List<PaymentChequeNumber> payment_check_number = Collections.emptyList();
        List<PaymentAmount> payment_amt = Collections.emptyList();
        List<PaymentRemark> payment_remarks = Collections.emptyList();

        protected PaymentDetail(Parcel in) {
            case_id = in.readString();
            case_number = in.readString();
            client_name = in.readString();
            court_name = in.readString();
            category = in.readString();
            client_phone = in.readString();
            paymt_status = in.readString();
            payment_id = in.createTypedArrayList(PaymentId.CREATOR);
            payment_date = in.createTypedArrayList(PaymentDate.CREATOR);
            payment_mode = in.createTypedArrayList(PaymentMode.CREATOR);
            payment_check_number = in.createTypedArrayList(PaymentChequeNumber.CREATOR);
            payment_amt = in.createTypedArrayList(PaymentAmount.CREATOR);
            payment_remarks = in.createTypedArrayList(PaymentRemark.CREATOR);
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(case_id);
            dest.writeString(case_number);
            dest.writeString(client_name);
            dest.writeString(court_name);
            dest.writeString(category);
            dest.writeString(client_phone);
            dest.writeString(paymt_status);
            dest.writeTypedList(payment_id);
            dest.writeTypedList(payment_date);
            dest.writeTypedList(payment_mode);
            dest.writeTypedList(payment_check_number);
            dest.writeTypedList(payment_amt);
            dest.writeTypedList(payment_remarks);
        }


        public static class PaymentId implements Parcelable{
             String id;

            protected PaymentId(Parcel in) {
                id = in.readString();
            }

            public static final Creator<PaymentId> CREATOR = new Creator<PaymentId>() {
                @Override
                public PaymentId createFromParcel(Parcel in) {
                    return new PaymentId(in);
                }

                @Override
                public PaymentId[] newArray(int size) {
                    return new PaymentId[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
            }
        }

        public static class PaymentDate implements Parcelable{
            String date;

            protected PaymentDate(Parcel in) {
                date = in.readString();
            }

            public static final Creator<PaymentDate> CREATOR = new Creator<PaymentDate>() {
                @Override
                public PaymentDate createFromParcel(Parcel in) {
                    return new PaymentDate(in);
                }

                @Override
                public PaymentDate[] newArray(int size) {
                    return new PaymentDate[size];
                }
            };

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(date);
            }
        }

        public static class PaymentMode implements Parcelable{
            String mode;

            protected PaymentMode(Parcel in) {
                mode = in.readString();
            }

            public static final Creator<PaymentMode> CREATOR = new Creator<PaymentMode>() {
                @Override
                public PaymentMode createFromParcel(Parcel in) {
                    return new PaymentMode(in);
                }

                @Override
                public PaymentMode[] newArray(int size) {
                    return new PaymentMode[size];
                }
            };

            public String getMode() {
                return mode;
            }

            public void setMode(String mode) {
                this.mode = mode;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mode);
            }
        }

        public static class PaymentChequeNumber implements Parcelable{
            String c_number;

            protected PaymentChequeNumber(Parcel in) {
                c_number = in.readString();
            }

            public static final Creator<PaymentChequeNumber> CREATOR = new Creator<PaymentChequeNumber>() {
                @Override
                public PaymentChequeNumber createFromParcel(Parcel in) {
                    return new PaymentChequeNumber(in);
                }

                @Override
                public PaymentChequeNumber[] newArray(int size) {
                    return new PaymentChequeNumber[size];
                }
            };

            public String getC_number() {
                return c_number;
            }

            public void setC_number(String c_number) {
                this.c_number = c_number;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(c_number);
            }
        }

        public static class PaymentAmount implements Parcelable{
            String amount;

            protected PaymentAmount(Parcel in) {
                amount = in.readString();
            }

            public static final Creator<PaymentAmount> CREATOR = new Creator<PaymentAmount>() {
                @Override
                public PaymentAmount createFromParcel(Parcel in) {
                    return new PaymentAmount(in);
                }

                @Override
                public PaymentAmount[] newArray(int size) {
                    return new PaymentAmount[size];
                }
            };

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(amount);
            }
        }

        public static class PaymentRemark implements Parcelable{
            String remark;

            protected PaymentRemark(Parcel in) {
                remark = in.readString();
            }

            public static final Creator<PaymentRemark> CREATOR = new Creator<PaymentRemark>() {
                @Override
                public PaymentRemark createFromParcel(Parcel in) {
                    return new PaymentRemark(in);
                }

                @Override
                public PaymentRemark[] newArray(int size) {
                    return new PaymentRemark[size];
                }
            };

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(remark);
            }
        }

        public List<PaymentId> getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(ArrayList<PaymentId> payment_id) {
            this.payment_id = payment_id;
        }

        public List<PaymentDate> getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(List<PaymentDate> payment_date) {
            this.payment_date = payment_date;
        }

        public List<PaymentMode> getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(List<PaymentMode> payment_mode) {
            this.payment_mode = payment_mode;
        }

        public List<PaymentChequeNumber> getPayment_check_number() {
            return payment_check_number;
        }

        public void setPayment_check_number(List<PaymentChequeNumber> payment_check_number) {
            this.payment_check_number = payment_check_number;
        }

        public List<PaymentAmount> getPayment_amt() {
            return payment_amt;
        }

        public void setPayment_amt(List<PaymentAmount> payment_amt) {
            this.payment_amt = payment_amt;
        }

        public List<PaymentRemark> getPayment_remarks() {
            return payment_remarks;
        }

        public void setPayment_remarks(List<PaymentRemark> payment_remarks) {
            this.payment_remarks = payment_remarks;
        }

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

    }
}
