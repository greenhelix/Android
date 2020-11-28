package com.greenhelix.pear.orderStatus;

import java.util.ArrayList;

// 클라우드 DB 에서 주문정보를 가져오는 바구니! order와 큰차이 없다.
public class NowOrder {

    private String sender;
    private String recipient;
    private ArrayList<String> recipient_addr;
    private String pear_kind;
    private String pear_amount;
    private String pear_box;
    private String status; //배송상태

    public NowOrder(){}

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
