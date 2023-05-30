package com.esprit.jobhunter.Entity;

public class User_Job {

    private int id_job;
    private int id_user;
    private String creation_date;

    public User_Job() {
    }

    public User_Job(int id_job, int id_user, String creation_date) {
        this.id_job = id_job;
        this.id_user = id_user;
        this.creation_date = creation_date;
    }

    public int getId_job() {
        return id_job;
    }

    public void setId_job(int id_job) {
        this.id_job = id_job;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
