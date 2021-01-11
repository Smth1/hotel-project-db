package com.roma.db.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CleaningReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private LocalDateTime creationDate;
    private Boolean isClosed;

    @ManyToOne(optional = false)
    @JoinColumn(name="maid_id")
    private HotelClient maid;

    @ManyToMany
    @JoinTable(name = "cleaning_report_has_room",
            joinColumns = { @JoinColumn(name="fk_report")},
            inverseJoinColumns = {@JoinColumn(name="fk_room")}
            )
    List<Room> rooms;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public HotelClient getMaid() {
        return maid;
    }

    public void setMaid(HotelClient maid) {
        this.maid = maid;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
