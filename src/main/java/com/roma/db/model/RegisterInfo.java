package com.roma.db.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class RegisterInfo {
    @Id
    private Integer id;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;

    @OneToOne(targetEntity = HotelClient.class, cascade = CascadeType.ALL)
    private HotelClient admin;

    @OneToOne(targetEntity = HotelClient.class, cascade = CascadeType.ALL)
    private HotelClient client;
}
