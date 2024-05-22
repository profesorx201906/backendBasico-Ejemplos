package com.crud.grupo69.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crud.grupo69.Entity.Cuenta;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta,String> {
    
}
