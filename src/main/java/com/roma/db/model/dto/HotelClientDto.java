package com.roma.db.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelClientDto {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}
