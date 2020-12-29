package com.roma.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Room {
    @Id
    private Integer id;
    private Integer floatNumber;
    private Integer number;

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
}
