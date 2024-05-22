package com.crud.grupo69.Dto;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CrearClienteDto {
    private String idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String userName;
    private String password;
    private Set<String> roles;
}
