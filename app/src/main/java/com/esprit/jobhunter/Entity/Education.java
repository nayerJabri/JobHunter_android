package com.esprit.jobhunter.Entity;

public class Education {

    private int id;
    private String inst_name;
    private String degree;
    private String domain;
    private String start_date;
    private String end_date;
    private String result;
    private String description;
    private int cv_id;

    public Education() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", inst_name='" + inst_name + '\'' +
                ", degree='" + degree + '\'' +
                ", domain='" + domain + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", result='" + result + '\'' +
                ", cv_id=" + cv_id +
                '}';
    }
}
