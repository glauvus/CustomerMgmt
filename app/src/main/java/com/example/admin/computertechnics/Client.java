package com.example.admin.computertechnics;

/**
 * Created by admin on 5/1/2017.
 */

public class Client {
    private String id, lastName, phone;
    private int finished;

    public Client(String id, String lastName, String phone, int finished) {
        this.id = id;
        this.lastName = lastName;
        this.phone = phone;
        this.finished = finished;
    }

    public boolean isFinished() {
        if (finished == 0)
            return false;
        else
            return true;
    }

    public void setFinished() {
        this.finished = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
