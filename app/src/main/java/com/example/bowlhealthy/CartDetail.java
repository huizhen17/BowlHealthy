package com.example.bowlhealthy;

public class CartDetail {
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
}
