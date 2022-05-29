package com.example.restroapp.details;

import java.io.Serializable;

public class Order implements Serializable {

    private String ItemId;
    private String ItemName;
    private String Quantity;
    private String Total;
    private String OrderDetailsId;
    private String Status ;
    private String Rate;
    private String ImageUrl;
    private String Note;
    private String Time;
    private String Type;
    private boolean IsComplimentary;

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getOrderDetailsId() {
        return OrderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        OrderDetailsId = orderDetailsId;
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public boolean getIsComplimentary() {
        return IsComplimentary;
    }

    public void setIsComplimentary(boolean isComplimentary) {
        IsComplimentary = isComplimentary;
    }

}
