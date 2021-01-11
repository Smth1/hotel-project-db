package com.roma.db.model.dto;

import com.roma.db.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelClientDto {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private Role role;

    public HotelClientDto(String firstName, String lastName, String login, Role user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.role = user;
    }
    //private String password;
}
