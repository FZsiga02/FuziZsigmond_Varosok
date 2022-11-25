package hu.petrik.varosok;

import android.annotation.SuppressLint;

import com.google.gson.annotations.Expose;

public class City {
    @Expose
    private String name;
    @Expose
    private String country;
    @Expose
    private int popularity;

    public City(String name, String country, int popularity) {
        this.name = name;
        this.country = country;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("%s, %s, %d", this.name, this.country, this.popularity);
    }
}

