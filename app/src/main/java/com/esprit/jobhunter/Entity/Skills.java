package com.esprit.jobhunter.Entity;

public class Skills {

    private int id;
    private String label;
    private int cv_id;

    public Skills() {
    }

    public Skills(int id, String label, int cv_id) {
        this.id = id;
        this.label = label;
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

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    @Override
    public String toString() {
        return "Skills{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", cv_id=" + cv_id +
                '}';
    }
}
