package com.roma.db.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer floatNumber;
    private Integer number;
    private Boolean isClean;

    @ManyToMany(mappedBy = "rooms")
    List<CleaningReport> reports;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFloatNumber() {
        return floatNumber;
    }

    public void setFloatNumber(Integer floatNumber) {
        this.floatNumber = floatNumber;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<CleaningReport> getReports() {
        return reports;
    }

    public void setReports(List<CleaningReport> reports) {
        this.reports = reports;
    }

    public Boolean getClean() {
        return isClean;
    }

    public void setClean(Boolean clean) {
        isClean = clean;
    }
}
