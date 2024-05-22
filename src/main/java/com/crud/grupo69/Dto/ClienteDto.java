package com.crud.grupo69.Dto;

import java.util.List;

import com.crud.grupo69.Entity.Role;

import lombok.Data;

@Data

public class ClienteDto {
    private String idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String userName;
    private List<Role> roles;
    
}
