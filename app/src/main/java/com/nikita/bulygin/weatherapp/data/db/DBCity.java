package com.nikita.bulygin.weatherapp.data.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class DBCity {

    @NonNull
    @PrimaryKey
    private Integer id = 0;
    private String name;
    private String country;


    public DBCity(@NonNull Integer id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
