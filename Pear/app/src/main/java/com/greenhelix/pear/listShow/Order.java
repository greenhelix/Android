package com.greenhelix.pear.listShow;

import java.util.ArrayList;

//데이터를 담아주는 바구니를 형성하여 가져오는 클래스이다.
// 간단히 말하면, db에 있는 정보를 가져와야하는데 형태를 바꿔서 가져오는것보다
// 이런식으로 클래스에 따로 할당하여 데이터를 이 클래스에 거쳐서 가져와지게 하면
// 보안적인 측면에서도 좋다.
// 일종의 DAO같은 개념이다.

/*이 곳에서 파라미터 명을 정확히 같은것을 써서 넣어줘야한다. 변수 형태도 정확히 써주어야 어댑터로 제대로 이동된다.*/
public class Order {
    private static final String LOG_TAG = "ik";
    private String sender, recipient, pear_kind, pear_amount, pear_box, status;
    private ArrayList<String> recipient_addr;


    //이 메서드가 있어야 주어진 값들을 계속 불러온다.
    public Order(){}

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
