package com.esprit.jobhunter.Entity;

public class Volunteer {

    private int id;
    private String organisation;
    private String start_date;
    private String end_date;
    private String role;
    private int still_going;
    private int cv_id;

    public Volunteer() {
    }

    public Volunteer(int id, String organisation, String start_date, String end_date, String role, int still_going, int cv_id) {
        this.id = id;
        this.organisation = organisation;
        this.start_date = start_date;
        this.end_date = end_date;
        this.role = role;
        this.still_going = still_going;
        this.cv_id = cv_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getStill_going() {
        return still_going;
    }

    public void setStill_going(int still_going) {
        this.still_going = still_going;
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
                "id=" + id +
                ", organisation='" + organisation + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", role='" + role + '\'' +
                ", still_going=" + still_going +
                ", cv_id=" + cv_id +
                '}';
    }
}
