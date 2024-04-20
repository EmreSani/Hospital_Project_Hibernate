package com.hospitalproject.entity.abstracts;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String isim;
    @Column(nullable = false)
    protected String soyIsim;

    public Person(String isim, String soyIsim) {
        this.isim = isim.substring(0, 1).toUpperCase() + isim.substring(1).toLowerCase().trim();
        this.soyIsim = soyIsim;
    }
    public Person() {

    }
    public void setIsim(String isim) {
        this.isim = isim.substring(0, 1).toUpperCase() + isim.substring(1).toLowerCase().trim();
    }

    public void setSoyIsim(String soyIsim) {
        this.soyIsim = soyIsim.substring(0, 1).toUpperCase() + soyIsim.substring(1).toLowerCase().trim();
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