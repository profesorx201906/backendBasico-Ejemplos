package com.crud.grupo69.Dto;

import java.util.Date;

import lombok.Data;

@Data
public class CuentaDto {
    private String id;
    private Date fechaApertura;
    private double saldoCuenta;
    private ClienteDto cliente;
}
