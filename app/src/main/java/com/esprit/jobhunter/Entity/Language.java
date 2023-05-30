package com.esprit.jobhunter.Entity;

public class Language {

    private int id;
    private String label;
    private String level;
    private int cv_id;

    public Language() {
    }

    public Language(int id, String label, String level, int cv_id) {
        this.id = id;
        this.label = label;
        this.level = level;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", level='" + level + '\'' +
                ", cv_id=" + cv_id +
                '}';
    }
}
