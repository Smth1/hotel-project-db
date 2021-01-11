package com.roma.db.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class RegisterInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private LocalDateTime creationDate;
    private LocalDateTime closingDate;
    private Boolean isConfirmed;
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    @OneToOne(targetEntity = HotelClient.class, cascade = CascadeType.ALL)
    private HotelClient admin;

    @OneToOne(targetEntity = HotelClient.class, cascade = CascadeType.ALL)
    private HotelClient client;

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

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public HotelClient getAdmin() {
        return admin;
    }

    public void setAdmin(HotelClient admin) {
        this.admin = admin;
    }

    public HotelClient getClient() {
        return client;
    }

    public void setClient(HotelClient client) {
        this.client = client;
    }
}
