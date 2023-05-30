package com.esprit.jobhunter.Entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Job implements Serializable {

    public static final String TABLE_NAME = "jobs";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_CONTRACT_TYPE = "contract_type";
    public static final String COLUMN_CAREER_REQ = "career_req";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_SKILLS = "skills";
    public static final String COLUMN_COMPANY_NAME = "company_name";
    public static final String COLUMN_COMPANY_PIC = "company_pic";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_LABEL + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_START_DATE + " TEXT,"
                    + COLUMN_CONTRACT_TYPE + " TEXT,"
                    + COLUMN_CAREER_REQ + " TEXT,"
                    + COLUMN_USER_ID + " TEXT,"
                    + COLUMN_SKILLS + " TEXT,"
                    + COLUMN_COMPANY_NAME + " TEXT,"
                    + COLUMN_COMPANY_PIC + " TEXT,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    private int id;
    private String name;
    private String label;
    private String description;
    private String start_date;
    private String contract_type;
    private String career_req;
    private String user_id;
    private String company_name;
    private String company_pic;
    private String type = "j";
    private String inserted_sq_date;
    private String skills;

    public Job() {
    }

    public Job(int id, String name, String label, String description, String start_date, String contract_type, String career_req, String user_id, String skills, String company_name, String company_pic, String inserted_sq_date) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.contract_type = contract_type;
        this.career_req = career_req;
        this.user_id = user_id;
        this.skills = skills;
        this.company_name = company_name;
        this.company_pic = company_pic;
        this.inserted_sq_date = inserted_sq_date;
    }

    public Job(int id, String name, String label, String description, String start_date, String contract_type, String career_req, String user_id,String skills, String company_name, String company_pic) {
        this.id = id;
        this.name = name;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.contract_type = contract_type;
        this.career_req = career_req;
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

    public String getContract_type() {
        return contract_type;
    }

    public void setContract_type(String contract_type) {
        this.contract_type = contract_type;
    }

    public String getCareer_req() {
        return career_req;
    }

    public void setCareer_req(String career_req) {
        this.career_req = career_req;
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

    public String getType() {
        return type;
    }

    public String getInserted_sq_date() {
        return inserted_sq_date;
    }

    public void setInserted_sq_date(String inserted_sq_date) {
        this.inserted_sq_date = inserted_sq_date;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", start_date='" + start_date + '\'' +
                ", contract_type='" + contract_type + '\'' +
                ", career_req='" + career_req + '\'' +
                ", user_id='" + user_id + '\'' +
                ", skills='" + skills + '\'' +
                ", company_name='" + company_name + '\'' +
                ", company_pic='" + company_pic + '\'' +
                '}';
    }
}
