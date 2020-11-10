package com.greenhelix.pear.orderStatus;

import java.util.ArrayList;

public class NowOrder {

    private String sender;
    private String recipient;
    private ArrayList<String> recipient_addr;
    private String pear_kind;
    private String pear_amount;
    private String pear_box;
    private String status;

    public NowOrder(){

    }
    public NowOrder(String sender, String recipient, ArrayList<String> recipient_addr,
                    String pear_kind, String pear_amount, String pear_box, String status){
        this.sender = sender;
        this.recipient = recipient;
        this.recipient_addr = recipient_addr;
        this.pear_kind =pear_kind;
        this.pear_amount =pear_amount;
        this.pear_box =pear_box;
        this.status = status;
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
}
