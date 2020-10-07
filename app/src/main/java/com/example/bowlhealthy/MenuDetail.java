package com.example.bowlhealthy;

public class MenuDetail {
    private int menuImg;
    private int menuName;
    private int menuDesc;
    private int ingredient;
    private String price;
    private String time;
    private float calories;

    public MenuDetail(int menuImg,int menuName,int ingredient, String price, String time) {
        this.menuImg = menuImg;
        this.menuName = menuName;
        this.ingredient = ingredient;
        this.price = price;
        this.time = time;
    }

    public MenuDetail(int menuImg,int menuName, int menuDesc, int ingredient, String price, String time, float calories) {
        this.menuImg = menuImg;
        this.menuName = menuName;
        this.menuDesc = menuDesc;
        this.ingredient = ingredient;
        this.price = price;
        this.time = time;
        this.calories = calories;
    }

    public int getMenuImg() {
        return menuImg;
    }

    public void setMenuImg(int menuImg) {
        this.menuImg = menuImg;
    }

    public int getMenuName() {
        return menuName;
    }

    public void setMenuName(int menuName) {
        this.menuName = menuName;
    }

    public int getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(int menuDesc) {
        this.menuDesc = menuDesc;
    }

    public int getIngredient() {
        return ingredient;
    }

    public void setIngredient(int ingredient) {
        this.ingredient = ingredient;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }
}
