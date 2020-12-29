package com.roma.db.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientLoginDto {
    private String login;
    private String password;
}
