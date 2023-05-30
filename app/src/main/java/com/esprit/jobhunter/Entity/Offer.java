package com.esprit.jobhunter.Entity;

public class Offer {

    private int id;
    private String label;
    private String cmp_pic;
    private String cmp_name;
    private String type;

    public Offer() {
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

    public String getCmp_pic() {
        return cmp_pic;
    }

    public void setCmp_pic(String cmp_pic) {
        this.cmp_pic = cmp_pic;
    }

    public String getCmp_name() {
        return cmp_name;
    }

    public void setCmp_name(String cmp_name) {
        this.cmp_name = cmp_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", cmp_pic='" + cmp_pic + '\'' +
                ", cmp_name='" + cmp_name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
