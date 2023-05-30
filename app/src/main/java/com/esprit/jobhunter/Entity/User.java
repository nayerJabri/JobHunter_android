package com.esprit.jobhunter.Entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable {

    private int id;
    private String name;
    private String last_name;
    private String birth_date;
    private String gender;
    private String email;
    private String adress;
    private String tel1;
    private String tel2;
    private String fax;
    private String nationality;
    private String description;
    private String  picture;
    private String type;
    private String password;
    private String cv_id;

    public User(int id, String name, String last_name, String birth_date, String gender, String email, String adress, String tel1, String tel2, String fax, String nationality, String description, String picture, String type, String password) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.gender = gender;
        this.email = email;
        this.adress = adress;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.fax = fax;
        this.nationality = nationality;
        this.description = description;
        this.picture = picture;
        this.type = type;
        this.password = password;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCv_id() {
        return cv_id;
    }

    public void setCv_id(String cv_id) {
        this.cv_id = cv_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birth_date='" + birth_date + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", adress='" + adress + '\'' +
                ", tel1='" + tel1 + '\'' +
                ", tel2='" + tel2 + '\'' +
                ", fax='" + fax + '\'' +
                ", nationality='" + nationality + '\'' +
                ", description='" + description + '\'' +
                ", picture='" + picture + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
