package com.greenhelix.pear.orderStatus;

import java.util.ArrayList;

// 클라우드 DB 에서 주문정보를 가져오는 바구니! order와 큰차이 없다.
public class NowOrder {
    private String sender, recipient, status;
    private ArrayList<String> recipient_addr;

    public NowOrder(){} //비어있는 constructor가 있어야함

    // 사용이 안되고 있는데, 있을 필요가 있나?
//    public NowOrder(String sender, String recipient, String status, ArrayList<String> recipient_addr) {
//        this.sender = sender;
//        this.recipient = recipient;
//        this.status = status;
//        this.recipient_addr = recipient_addr;
//    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public ArrayList<String> getRecipient_addr() {
        return recipient_addr;
    }

    public String getStatus() {
        return status;
    }

}
