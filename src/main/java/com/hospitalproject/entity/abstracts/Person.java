package com.hospitalproject.entity.abstracts;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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

    public Long getId() {
        return id;
    }
}