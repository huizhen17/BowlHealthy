package com.example.bowlhealthy;

public class MenuDetail {
    private int menuImg;
    private String menuName;
    private int menuDesc;
    private int ingredient;
    private String price;
    private String time;
    private String calories;

    public MenuDetail(int menuImg,String menuName, int ingredient, int menuDesc, String price, String time, String calories) {
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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
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

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
