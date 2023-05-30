package com.esprit.jobhunter.Entity;

public class ApplicationInternship {

    private int id_applicant;
    private int id_job;
    private String creation_date;
    private String label;
    private String description;
    private String start_date;
    private String educ_req;
    private String duration;
    private int id_company;
    private String name_company;
    private String picture_company;
    private Boolean is_bookmarked = false;
    private String type = "i";

    public ApplicationInternship() {
    }

    public ApplicationInternship(int id_applicant, int id_job, String creation_date, String label, String description, String start_date, String educ_req, String duration, int id_company, String name_company, String picture_company) {
        this.id_applicant = id_applicant;
        this.id_job = id_job;
        this.creation_date = creation_date;
        this.label = label;
        this.description = description;
        this.start_date = start_date;
        this.educ_req = educ_req;
        this.duration = duration;
        this.id_company = id_company;
        this.name_company = name_company;
        this.picture_company = picture_company;
    }

    public int getId_applicant() {
        return id_applicant;
    }

    public void setId_applicant(int id_applicant) {
        this.id_applicant = id_applicant;
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
        return "ApplicationInternship{" +
                "id_applicant=" + id_applicant +
                ", id_job=" + id_job +
                ", creation_date='" + creation_date + '\'' +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", start_date='" + start_date + '\'' +
                ", educ_req='" + educ_req + '\'' +
                ", duration='" + duration + '\'' +
                ", id_company=" + id_company +
                ", name_company='" + name_company + '\'' +
                ", picture_company='" + picture_company + '\'' +
                '}';
    }
}
