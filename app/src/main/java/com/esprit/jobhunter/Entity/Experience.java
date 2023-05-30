package com.esprit.jobhunter.Entity;

public class Experience {

    private int id;
    private String label;
    private String start_date;
    private String end_date;
    private String description;
    private int still_going;
    private String establishmentName;
    private int cv_id;

    public Experience() {
    }

    public Experience(int id, String label, String start_date, String end_date, String description, int still_going, String establishmentName, int cv_id) {
        this.id = id;
        this.label = label;
        this.start_date = start_date;
        this.end_date = end_date;
        this.description = description;
        this.still_going = still_going;
        this.establishmentName = establishmentName;
        this.cv_id = cv_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStill_going() {
        return still_going;
    }

    public void setStill_going(int still_going) {
        this.still_going = still_going;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    @Override
    public String toString() {
        return "Experience{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", description='" + description + '\'' +
                ", still_going=" + still_going +
                ", establishmentName='" + establishmentName + '\'' +
                ", cv_id=" + cv_id +
                '}';
    }
}
