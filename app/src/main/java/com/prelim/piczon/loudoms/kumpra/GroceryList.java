package com.prelim.piczon.loudoms.kumpra;

/**
 * Created by LouDoms on 7/1/2015.
 */
public class GroceryList{

    private long id;
    private String item;
    private String quantity;

    public GroceryList(long id, String item, String quantity){
        this.id = id;
        this.item = item;
        this.quantity = quantity;
    }

    public GroceryList(String item, String quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return getItem() + " = " + getQuantity();
    }
}
