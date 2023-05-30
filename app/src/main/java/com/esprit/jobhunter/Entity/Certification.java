package com.esprit.jobhunter.Entity;

public class Certification {

    private int id;
    private String label;
    private String licence_num;
    private String cert_date;
    private String expire_date;
    private String cert_authority;
    private int if_expire;
    private int cv_id;

    public Certification() {
    }

    public Certification(int id, String label, String licence_num, String cert_date, String expire_date, String cert_authority, int if_expire, int cv_id) {
        this.id = id;
        this.label = label;
        this.licence_num = licence_num;
        this.cert_date = cert_date;
        this.expire_date = expire_date;
        this.cert_authority = cert_authority;
        this.if_expire = if_expire;
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

    public String getLicence_num() {
        return licence_num;
    }

    public void setLicence_num(String licence_num) {
        this.licence_num = licence_num;
    }

    public String getCert_date() {
        return cert_date;
    }

    public void setCert_date(String cert_date) {
        this.cert_date = cert_date;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getCert_authority() {
        return cert_authority;
    }

    public void setCert_authority(String cert_authority) {
        this.cert_authority = cert_authority;
    }

    public int getIf_expire() {
        return if_expire;
    }

    public void setIf_expire(int if_expire) {
        this.if_expire = if_expire;
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        this.cv_id = cv_id;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", licence_num='" + licence_num + '\'' +
                ", cert_date='" + cert_date + '\'' +
                ", expire_date='" + expire_date + '\'' +
                ", cert_authority='" + cert_authority + '\'' +
                ", if_expire=" + if_expire +
                ", cv_id=" + cv_id +
                '}';
    }
}
