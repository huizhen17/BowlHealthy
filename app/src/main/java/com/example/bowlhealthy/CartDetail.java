package com.example.bowlhealthy;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class CartDetail implements Parcelable {
    private int cartImg;
    private String cartMenu;
    private String cartPrice;
    private String cartQty;

    public CartDetail(int cartImg,String cartMenu, String cartPrice, String cartQty) {
        this.cartImg = cartImg;
        this.cartMenu = cartMenu;
        this.cartPrice = cartPrice;
        this.cartQty = cartQty;
    }

    public CartDetail() {
        this(0,"","","1");
    }

    protected CartDetail(Parcel in) {
        cartImg = in.readInt();
        cartMenu = in.readString();
        cartPrice = in.readString();
        cartQty = in.readString();
    }

    public static final Creator<CartDetail> CREATOR = new Creator<CartDetail>() {
        @Override
        public CartDetail createFromParcel(Parcel in) {
            return new CartDetail(in);
        }

        @Override
        public CartDetail[] newArray(int size) {
            return new CartDetail[size];
        }
    };

    public int getCartImg() {
        return cartImg;
    }

    public void setCartImg(int cartImg) {
        this.cartImg = cartImg;
    }

    public String getCartMenu() {
        return cartMenu;
    }

    public void setCartMenu(String cartMenu) {
        this.cartMenu = cartMenu;
    }

    public String getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(String cartPrice) {
        this.cartPrice = cartPrice;
    }

    public String getCartQty() {
        return cartQty;
    }

    public void setCartQty(String cartQty) {
        this.cartQty = cartQty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(cartImg);
        parcel.writeString(cartMenu);
        parcel.writeString(cartPrice);
        parcel.writeString(cartQty);
    }
}
