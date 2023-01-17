package edu.cis.Model;

import java.util.ArrayList;

public class CISUser {

        String userID;
        String name;
        String yearLevel;
        ArrayList<Order> order;
        Double money;

        public CISUser(String userID,String name , String yearLevel, Double money) {
            this.userID = userID;
            this.name = name;
            this.yearLevel = yearLevel;
            this.order = new ArrayList<>();
            this.money = money;
        }

        public Double getMoney() {
            return money;
        }

        public void setMoney(Double money) {
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Order> getOrder() {
            return order;
        }

        public void setOrder(ArrayList<Order> order) {
            this.order = order;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getYearLevel() {
            return yearLevel;
        }

        public void setYearLevel(String yearLevel) {
            this.yearLevel = yearLevel;
        }

    public String toCart()
    {
        return "Cart{"+ order+ " }";
    }

    @Override
    public String toString() {
        return "CISUser{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", yearLevel='" + yearLevel + '\'' +
                ", orders=" + order+
                ", money=" + money +
                '}';
    }
}
