package com.hospitalproject.entity;

public abstract class Person {
    protected String isim;
    protected String soyIsim;

    public Person(String isim, String soyIsim) {
        this.isim = isim;
        this.soyIsim = soyIsim;
    }
    public Person() {

    }
    public void setIsim(String isim) {
        this.isim = isim;
    }

    public void setSoyIsim(String soyIsim) {
        this.soyIsim = soyIsim;
    }

    public String getIsim() {
        return isim;
    }

    public String getSoyIsim() {
        return soyIsim;
    }
}