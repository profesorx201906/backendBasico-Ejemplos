package com.crud.grupo69.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crud.grupo69.Entity.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,String> {
    public Optional<Cliente> findById(String id);

    public Cliente findByUserName(String user);

    @Transactional(readOnly = true)
    @Query(value = "Select * from clientes where user_name=:user and password=:pwd ",nativeQuery = true)
    public Cliente login(@Param("user") String user,@Param("pwd") String pwd);

  
}
