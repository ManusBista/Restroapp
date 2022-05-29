package com.example.restroapp.HomePage;

import java.io.Serializable;

public class ActiveOrder  {
    private String Id;
    private String OrderId;
    private String Total;
    private String subTotal;
    private String Discount;
    private String OrderedBy;
    private String Status ;
    private String Rate;
    private String OrderBy;
    private String mobileNumber;
    private String Time;
    private String OrderType;
    private boolean IsComplimentary;


    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getOrderedBy() {
        return OrderedBy;
    }

    public void setOrderedBy(String orderedBy) {
        OrderedBy = orderedBy;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(String orderBy) {
        OrderBy = orderBy;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String orderType) {
        OrderType = orderType;
    }

    public boolean getIsComplimentary() {
        return IsComplimentary;
    }

    public void setIsComplimentary(boolean isComplimentary) {
        IsComplimentary = isComplimentary;
    }

}
