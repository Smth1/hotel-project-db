package com.roma.db.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CleaningReport {
    @Id
    private Integer id;
    private String description;
    private LocalDateTime creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name="maid_id")
    private HotelClient maid;

    @ManyToOne(optional = false)
    @JoinColumn(name="admin_id")
    private HotelClient admin;

    @ManyToMany
    @JoinTable(name = "cleaning_report_has_room",
            joinColumns = { @JoinColumn(name="fk_report")},
            inverseJoinColumns = {@JoinColumn(name="fk_room")}
            )
    List<Room> rooms;
}
