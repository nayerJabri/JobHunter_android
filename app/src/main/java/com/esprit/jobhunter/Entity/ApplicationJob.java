package com.esprit.jobhunter.Entity;

public class ApplicationJob {

    private int id_applicant;
    private int id_job;
    private String creation_date;
    private String label;
    private String description;
    private String start_date;
    private String contract_type;
    private String career_req;
    private int id_company;
    private String name_company;
    private String picture_company;
    private Boolean is_bookmarked = false;
    private String type = "j";

    public ApplicationJob() {
    }

    public ApplicationJob(int id_user, int id_job, String creation_date, String label, String description, String start_date, String contract_type, String career_req, int id_company, String name_company, String picture_company) {
        this.id_applicant = id_user;
        this.id_job = id_job;
        this.creation_date = creation_date;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.contract_type = contract_type;
        this.career_req = career_req;
        this.id_company = id_company;
        this.name_company = name_company;
        this.picture_company = picture_company;
    }

    public int getId_applicant() {
        return id_applicant;
    }

    public void setId_applicant(int id_user) {
        this.id_applicant = id_user;
    }

    public int getId_job() {
        return id_job;
    }

    public void setId_job(int id_job) {
        this.id_job = id_job;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
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

    public int getId_company() {
        return id_company;
    }

    public void setId_company(int id_company) {
        this.id_company = id_company;
    }

    public String getName_company() {
        return name_company;
    }

    public void setName_company(String name_company) {
        this.name_company = name_company;
    }

    public String getPicture_company() {
        return picture_company;
    }

    public void setPicture_company(String picture_company) {
        this.picture_company = picture_company;
    }

    public Boolean getIs_bookmarked() {
        return is_bookmarked;
    }

    public void setIs_bookmarked(Boolean is_bookmarked) {
        this.is_bookmarked = is_bookmarked;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ApplicationJob{" +
                "id_user=" + id_applicant +
                ", id_job=" + id_job +
                ", creation_date='" + creation_date + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", start_date='" + start_date + '\'' +
                ", contract_type='" + contract_type + '\'' +
                ", career_req='" + career_req + '\'' +
                ", id_company=" + id_company +
                ", name_company='" + name_company + '\'' +
                ", picture_company='" + picture_company + '\'' +
                '}';
    }
}
