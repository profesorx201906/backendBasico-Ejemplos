package com.crud.grupo69.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.crud.grupo69.Entity.ERol;
import com.crud.grupo69.Entity.Role;

@Repository
public interface RolRepository extends CrudRepository<Role,String> {
    public Optional<Role> findByNombre(ERol nombre);
}
