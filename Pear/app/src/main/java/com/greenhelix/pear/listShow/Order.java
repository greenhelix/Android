package com.greenhelix.pear.listShow;

//데이터를 담아주는 바구니를 형성하여 가져오는 클래스이다.
// 간단히 말하면, db에 있는 정보를 가져와야하는데 형태를 바꿔서 가져오는것보다
// 이런식으로 클래스에 따로 할당하여 데이터를 이 클래스에 거쳐서 가져와지게 하면
// 보안적인 측면에서도 좋다.
// 일종의 DAO같은 개념이다.
public class Order {
    private String orderNum;
    private String senderName;
    private String recipientName;
    private String address;
    private String pearKind;
    private String pearAmount;
    private String pearBox;
    public Order(){

    }
    public Order(String orderNum, String senderName, String recipientName, String address,
                 String pearKind, String pearAmount, String pearBox){

        this.orderNum = orderNum;
        this.senderName = senderName;
        this.recipientName = recipientName;
        this.address =address;
        this.pearKind =pearKind;
        this.pearAmount =pearAmount;
        this.pearBox =pearBox;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getAddress() {
        return address;
    }

    public String getPearKind() {
        return pearKind;
    }

    public String getPearAmount() {
        return pearAmount;
    }

    public String getPearBox() {
        return pearBox;
    }
}
