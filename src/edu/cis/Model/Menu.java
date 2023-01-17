package edu.cis.Model;

import java.awt.*;
import java.util.ArrayList;

public class Menu {
    ArrayList<MenuItem> eatriumItems;

    public Menu(ArrayList<MenuItem> eatriumItems, String adminId) {
        this.eatriumItems = eatriumItems;
        this.adminId = adminId;
    }

    public ArrayList<MenuItem> getEatriumItems() {
        return eatriumItems;
    }

    public void setEatriumItems(ArrayList<MenuItem> eatriumItems) {
        this.eatriumItems = eatriumItems;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "eatriumItems=" + eatriumItems +
                ", adminId='" + adminId + '\'' +
                '}';
    }


    String adminId;
}
