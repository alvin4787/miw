package com.miw.gildedrose.controller.response;

public class OrderResponse {

    public OrderResponse() {
    }

    public OrderResponse(String orderUid) {
        this.orderUid = orderUid;
    }

    private String orderUid;

    public String getOrderUid() {
        return orderUid;
    }

    public void setOrderUid(String orderUid) {
        this.orderUid = orderUid;
    }
}
