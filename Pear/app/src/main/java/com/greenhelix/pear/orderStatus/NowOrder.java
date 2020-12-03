package com.greenhelix.pear.orderStatus;

import java.util.ArrayList;

// 클라우드 DB 에서 주문정보를 가져오는 바구니! order와 큰차이 없다.
public class NowOrder {
    private String sender, recipient, pear_kind, pear_amount, pear_box, status, recipient_tel, sender_tel, user;
    private ArrayList<String> recipient_addr;

    public NowOrder(){} //비어있는 constructor가 있어야함

    public NowOrder(String sender, String recipient, String pear_kind, String pear_amount, String pear_box, String status, ArrayList<String> recipient_addr) {
        this.sender = sender;
        this.recipient = recipient;
        this.pear_kind = pear_kind;
        this.pear_amount = pear_amount;
        this.pear_box = pear_box;
        this.status = status;
        this.recipient_addr = recipient_addr;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRecipient_tel() {
        return recipient_tel;
    }

    public void setRecipient_tel(String recipient_tel) {
        this.recipient_tel = recipient_tel;
    }

    public String getSender_tel() {
        return sender_tel;
    }

    public void setSender_tel(String sender_tel) {
        this.sender_tel = sender_tel;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public ArrayList<String> getRecipient_addr() {
        return recipient_addr;
    }

    public String getPear_kind() {
        return pear_kind;
    }

    public String getPear_amount() {
        return pear_amount;
    }

    public String getPear_box() {
        return pear_box;
    }

    public String getStatus() {
        return status;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public void setPear_kind(String pear_kind) {
        this.pear_kind = pear_kind;
    }

    public void setPear_amount(String pear_amount) {
        this.pear_amount = pear_amount;
    }

    public void setPear_box(String pear_box) {
        this.pear_box = pear_box;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRecipient_addr(ArrayList<String> recipient_addr) {
        this.recipient_addr = recipient_addr;
    }
}
