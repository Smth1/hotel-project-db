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
    private Role role;
    //private String password;
}
