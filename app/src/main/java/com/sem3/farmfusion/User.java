package com.sem3.farmfusion;

public class User {
    String Name;
    String Address;
    String Phone_Number;
    String Password;
    String Email;

    public User(String name, String address, String phone_Number, String email, String password) {
        Name = name;
        Address = address;
        Phone_Number = phone_Number;
        Password = password;
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone_Number() {
        return Phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        Phone_Number = phone_Number;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
