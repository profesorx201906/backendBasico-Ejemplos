package com.crud.grupo69.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.grupo69.Entity.Cliente;
import com.crud.grupo69.Entity.Message;
import com.crud.grupo69.Entity.Role;
import com.crud.grupo69.Repository.ClienteRepository;
import com.crud.grupo69.Security.Hash;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepository.findAll();
    }

    public Message deleteById(String id) {
        try {
            clienteRepository.deleteById(id);
            return new Message(200, "Registro con id " + id + " eliminado");
        } catch (Exception e) {
            // TODO: handle exception
            return new Message(400, "No existe cliente con id " + id);
        }

    }

    public Message update(Cliente cliente) {
        Cliente clienteConsulta = clienteRepository.findById(cliente.getIdCliente()).get();
        if (!clienteConsulta.getIdCliente().equals("")) {
            clienteRepository.save(cliente);
            return new Message(200, "Registro actualizado");
        } else {
            return new Message(404, "Cliente no encontrado");
        }

    }

    public Cliente login(String user, String pwd) {
        return clienteRepository.login(user, pwd);
    }

    public Cliente findByUserName(String user) {
        return clienteRepository.findByUserName(user);
    }

    public Cliente findById(String id) {
        return clienteRepository.findById(id).get();
    }

    public Boolean validarCredenciales(String user, String key) {
        Cliente clienteC = clienteRepository.findByUserName(user);
        if (clienteC == null) {
            return false;
        } else {
            if (Hash.sha1(clienteC.getPassword() + Hash.sha1(user)).equals(key)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean validarUsuarioAdmin(String user, String key) {
        Cliente cliente = clienteRepository.findByUserName(user);

        if (!validarCredenciales(user, key)) {
            return false;
        }
        try {
            if (cliente.getNombreCliente() == null) {
                return false;
            }
            int cantidad = 0;

            for (Role role : cliente.getRoles()) {
                if (role.getNombre().toString().equals("ROLE_ADMIN")) {
                    cantidad++;
                }

            }
            if (cantidad == 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
