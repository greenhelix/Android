package com.greenhelix.pear.orderStatus;

import java.util.ArrayList;

// 클라우드 DB 에서 주문정보를 가져오는 바구니! order와 큰차이 없다.
public class NowOrder {
    private String sender, recipient, pear_kind, pear_amount, pear_box, status;
    private ArrayList<String> recipient_addr;
    private Boolean expandable;


    public NowOrder(){} //비어있는 constructor가 있어야함

    public Boolean getExpandable() {
        return expandable;
    }

    public void setExpandable(Boolean expandable) {
        this.expandable = expandable;
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
