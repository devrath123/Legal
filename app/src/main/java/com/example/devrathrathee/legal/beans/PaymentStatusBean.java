package com.example.devrathrathee.legal.beans;

import java.util.List;

public class PaymentStatusBean {

    List<ClientStatus> client_status;

    public List<ClientStatus> getClient_status() {
        return client_status;
    }

    public void setClient_status(List<ClientStatus> client_status) {
        this.client_status = client_status;
    }

    public static class ClientStatus{
        String case_id,paymt_status;

        public String getCase_id() {
            return case_id;
        }

        public void setCase_id(String case_id) {
            this.case_id = case_id;
        }

        public String getPaymt_status() {
            return paymt_status;
        }

        public void setPaymt_status(String paymt_status) {
            this.paymt_status = paymt_status;
        }
    }
}
