package com.innprojects.gymapp.javaModels;

/**
 * Created by simransarin on 20/10/17.
 */
public class User {
    int registration_number;
    String name;
    String address;
    String phone_number;
    String date_of_birth;
    int age;
    String sex;
    int height;
    Boolean flag;
    String username;
    String email;
    String id;

    public User(Boolean flag) {
        this.flag = flag;
    }

    public User(int registration_number, String name, String address, String phone_number, Boolean flag, String username, String email, String id) {
        this.registration_number = registration_number;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.flag = flag;
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public User(int registration_number, String name, String address, String phone_number, String date_of_birth, int age, String sex, int height, Boolean flag, String username, String email, String id) {
        this.registration_number = registration_number;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.age = age;
        this.sex = sex;
        this.height = height;
        this.flag = flag;
        this.username = username;
        this.email = email;
        this.id = id;
    }

    public int getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(int registration_number) {
        this.registration_number = registration_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
