package com.esprit.jobhunter.Entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Internship implements Serializable {
    public static final String TABLE_NAME = "internships";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_EDUC_REC = "educ_req";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_SKILLS = "skills";
    public static final String COLUMN_COMPANY_NAME = "company_name";
    public static final String COLUMN_COMPANY_PIC = "company_pic";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LABEL + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_START_DATE + " TEXT,"
                    + COLUMN_EDUC_REC + " TEXT,"
                    + COLUMN_DURATION + " TEXT,"
                    + COLUMN_USER_ID + " TEXT,"
                    + COLUMN_SKILLS + " TEXT,"
                    + COLUMN_COMPANY_NAME + " TEXT,"
                    + COLUMN_COMPANY_PIC + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    private int id;
    private String name;
    private String label;
    private String description;
    private String start_date;
    private String educ_req;
    private String duration;
    private String user_id;
    private String company_name;
    private String company_pic;
    private String skills;
    private String inserted_sq_date;

    public Internship() {
    }
    public Internship(int id, String name, String label, String description,String duration, String start_date, String educ_req, String user_id,String skills, String company_name, String company_pic, String inserted_sq_date) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.duration = duration;
        this.educ_req = educ_req;
        this.user_id = user_id;
        this.skills = skills;
        this.company_name = company_name;
        this.company_pic = company_pic;
        this.inserted_sq_date = inserted_sq_date;
    }
    public Internship(int id, String name, String label, String description, String start_date, String educ_req, String duration, String user_id,String skills, String company_name, String company_pic) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.educ_req = educ_req;
        this.duration = duration;
        this.user_id = user_id;
        this.skills = skills;
        this.company_name = company_name;
        this.company_pic = company_pic;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEduc_req() {
        return educ_req;
    }

    public void setEduc_req(String educ_req) {
        this.educ_req = educ_req;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_pic() {
        return company_pic;
    }

    public void setCompany_pic(String company_pic) {
        this.company_pic = company_pic;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getInserted_sq_date() {
        return inserted_sq_date;
    }

    public void setInserted_sq_date(String inserted_sq_date) {
        this.inserted_sq_date = inserted_sq_date;
    }

    @Override
    public String toString() {
        return "Internship{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", start_date='" + start_date + '\'' +
                ", educ_req='" + educ_req + '\'' +
                ", duration='" + duration + '\'' +
                ", user_id='" + user_id + '\'' +
                ", skills='" + skills + '\'' +
                ", company_name='" + company_name + '\'' +
                ", company_pic='" + company_pic + '\'' +
                '}';
    }
}
