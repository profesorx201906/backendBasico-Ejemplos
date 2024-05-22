package com.crud.grupo69.Dto;

import java.util.List;

import com.crud.grupo69.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredencialesDto {
    private String user;
    private String key;
    private List<Role> roles;
}
