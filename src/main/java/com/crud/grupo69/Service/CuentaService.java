package com.crud.grupo69.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.grupo69.Entity.Cuenta;
import com.crud.grupo69.Entity.Message;
import com.crud.grupo69.Repository.CuentaRepository;

@Service
public class CuentaService {
    @Autowired
    CuentaRepository cuentaRepository;

    public Cuenta save(Cuenta Cuenta) {
        return cuentaRepository.save(Cuenta);
    }

    public List<Cuenta> findAll() {
        return (List<Cuenta>) cuentaRepository.findAll();
    }

    public Cuenta findAllId(String id) {
        return cuentaRepository.findById(id).get();
    }

    public Message deleteById(String id) {
        try {
            cuentaRepository.deleteById(id);
            return new Message(200, "Registro con id " + id + " eliminado");
        } catch (Exception e) {
            // TODO: handle exception
            return new Message(400, "No existe Cuenta con id " + id);
        }

    }

}
