package com.example.bowlhealthy;

import android.os.Parcel;

public class ReceiptDetail extends CartDetail{
    private String repName;
    private String repPhone;
    private String repDate;
    private String repTime;
    private String repSubtotal;
    private String repTotal;

    public ReceiptDetail(int cartImg, String cartMenu, String cartPrice, String cartQty, String repName, String repPhone, String repDate, String repTime, String repSubtotal, String repTotal) {
        super(cartImg, cartMenu, cartPrice, cartQty);
        this.repName = repName;
        this.repPhone = repPhone;
        this.repDate = repDate;
        this.repTime = repTime;
        this.repSubtotal = repSubtotal;
        this.repTotal = repTotal;
    }

    public ReceiptDetail(String repName, String repPhone, String repDate, String repTime, String repSubtotal, String repTotal) {
        this.repName = repName;
        this.repPhone = repPhone;
        this.repDate = repDate;
        this.repTime = repTime;
        this.repSubtotal = repSubtotal;
        this.repTotal = repTotal;
    }

    public ReceiptDetail(Parcel in, String repName, String repPhone, String repDate, String repTime, String repSubtotal, String repTotal) {
        super(in);
        this.repName = repName;
        this.repPhone = repPhone;
        this.repDate = repDate;
        this.repTime = repTime;
        this.repSubtotal = repSubtotal;
        this.repTotal = repTotal;
    }

    public String getRepName() {
        return repName;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getRepPhone() {
        return repPhone;
    }

    public void setRepPhone(String repPhone) {
        this.repPhone = repPhone;
    }

    public String getRepDate() {
        return repDate;
    }

    public void setRepDate(String repDate) {
        this.repDate = repDate;
    }

    public String getRepTime() {
        return repTime;
    }

    public void setRepTime(String repTime) {
        this.repTime = repTime;
    }

    public String getRepSubtotal() {
        return repSubtotal;
    }

    public void setRepSubtotal(String repSubtotal) {
        this.repSubtotal = repSubtotal;
    }

    public String getRepTotal() {
        return repTotal;
    }

    public void setRepTotal(String repTotal) {
        this.repTotal = repTotal;
    }
}
