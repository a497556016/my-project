package com.heshaowei.myproj.bean.request;

public class Order {
    private String direction = Direction.ASC;
    private String[] properties;

    public Order(){}


    public static Order of(String direction, String... properties) {
        Order s = new Order();
        s.setDirection(direction);
        s.setProperties(properties);
        return s;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public interface Direction {
       String DESC = "desc";
       String ASC = "asc";
    }
}
