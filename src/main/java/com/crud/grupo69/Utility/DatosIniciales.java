package com.crud.grupo69.Utility;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.crud.grupo69.Entity.Cliente;
import com.crud.grupo69.Entity.ERol;
import com.crud.grupo69.Entity.Role;
import com.crud.grupo69.Repository.RolRepository;
import com.crud.grupo69.Security.Hash;
import com.crud.grupo69.Service.ClienteService;
import com.crud.grupo69.Service.RolService;

@Component
public class DatosIniciales implements CommandLineRunner {
  

    @Autowired
    RolService rolService;

    @Autowired
    ClienteService clienteService;
    @Autowired
    RolRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (rolService.findAll().size() == 0) {
            Role rolAdmin = new Role(ERol.ROLE_ADMIN);
            Role rolUser = new Role(ERol.ROLE_USER);
            Role rolCahser = new Role(ERol.ROLE_CASHER);
            rolService.save(rolAdmin);
            rolService.save(rolUser);
            rolService.save(rolCahser);
        }
        if (clienteService.findAll().size() == 0) {
            Role userRole = roleRepository.findByNombre(ERol.ROLE_ADMIN).get();
            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            Cliente cliente = new Cliente();
            cliente.setNombreCliente("admin");
            cliente.setApellidoCliente("admin");
            cliente.setUserName("admin");
            cliente.setPassword(Hash.sha1("123456"));
            cliente.setRoles(roles);
            clienteService.save(cliente);
        }

    }
    
}
