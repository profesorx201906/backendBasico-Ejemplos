package com.crud.grupo69.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.grupo69.Dto.ClienteDto;
import com.crud.grupo69.Dto.CrearClienteDto;
import com.crud.grupo69.Dto.CredencialesDto;
import com.crud.grupo69.Entity.Cliente;
import com.crud.grupo69.Entity.ERol;
import com.crud.grupo69.Entity.Message;
import com.crud.grupo69.Entity.Role;
import com.crud.grupo69.Exception.Exception.InvalidDataException;
import com.crud.grupo69.Repository.RolRepository;
import com.crud.grupo69.Security.Hash;
import com.crud.grupo69.Service.ClienteService;
import com.crud.grupo69.Service.RolService;
import com.crud.grupo69.Utility.ConvertEntity;

@RestController
@RequestMapping("/api/v1/cliente")
@CrossOrigin("*")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    ConvertEntity convertEntity = new ConvertEntity();

    @Autowired
    RolService rolService;

    @PostMapping("/create")
    public ResponseEntity<Message> save(@Valid @RequestBody CrearClienteDto cliente, BindingResult result,
            @RequestHeader String user,
            @RequestHeader String key) {

        if (result.hasErrors()) {
            throw new InvalidDataException("Error de datos", result);
        }

        if (clienteService.validarUsuarioAdmin(user, key) == false) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Set<String> strRoles = cliente.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role rol = rolService.findByNombre(ERol.ROLE_USER).get();
            roles.add(rol);
        } else {

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role rolAdmin = rolService.findByNombre(ERol.ROLE_ADMIN).get();
                        roles.add(rolAdmin);
                        break;
                    case "user":
                        Role rolUser = rolService.findByNombre(ERol.ROLE_USER).get();
                        roles.add(rolUser);
                        break;
                    case "cash":
                        Role rolCash = rolService.findByNombre(ERol.ROLE_CASHER).get();
                        roles.add(rolCash);
                        break;
                }
            });

        }

        
        Cliente clienteSave = (Cliente) convertEntity.convert(cliente, new Cliente());
       
        clienteSave.setPassword(Hash.sha1(cliente.getPassword()));
       
        clienteSave.setRoles(roles);
          if (!clienteService.validarCredenciales(user, key)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            if (cliente.getNombreCliente() == null) {
                return new ResponseEntity<Message>(new Message(400, "El campo nombre es obligatorio"),
                        HttpStatus.BAD_REQUEST);
            }
            
            clienteService.save(clienteSave);
            return new ResponseEntity<Message>(new Message(201, "Registro Creado de forma  satisfactoria"),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(400, "Los datos ingresados no    son correctos"),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update")
    public ResponseEntity<Message> update(@RequestBody CrearClienteDto cliente, @RequestHeader String user,
    @RequestHeader String key) {
        if (clienteService.validarCredenciales(user, key) == false) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Set<String> strRoles = cliente.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role rol = rolService.findByNombre(ERol.ROLE_USER).get();
            roles.add(rol);
        } else {

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role rolAdmin = rolService.findByNombre(ERol.ROLE_ADMIN).get();
                        roles.add(rolAdmin);
                        break;
                    case "user":
                        Role rolUser = rolService.findByNombre(ERol.ROLE_USER).get();
                        roles.add(rolUser);
                        break;
                    case "cash":
                        Role rolCash = rolService.findByNombre(ERol.ROLE_CASHER).get();
                        roles.add(rolCash);
                        break;
                }
            });

        }

        Cliente clienteSave = (Cliente)convertEntity.convert(cliente, new Cliente());
        clienteSave.setPassword(Hash.sha1(cliente.getPassword()));
        clienteSave.setRoles(roles);
        try {
            if (cliente.getNombreCliente() == null) {
                return new ResponseEntity<Message>(new Message(400, "El campo nombre es obligatorio"),
                        HttpStatus.BAD_REQUEST);
            }
            Message message = clienteService.update(clienteSave);
            return new ResponseEntity<Message>(new Message(message.getStatus(), message.getMessage()),
                    HttpStatus.valueOf(message.getStatus()));
        } catch (Exception e) {
            return new ResponseEntity<Message>(new Message(400, "Los datos ingresados no son correctos"),
                    HttpStatus.BAD_REQUEST);
        }
      

    }

    @GetMapping("/encriptar/{dato}")
    public String encriptar(@PathVariable String dato) {
        return Hash.sha1(dato);
    }

    @GetMapping
    public ResponseEntity<String> saludar() {
        return new ResponseEntity<>("Hola mundo", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable String id) {
        Message message = clienteService.deleteById(id);
        return new ResponseEntity<>(message, HttpStatus.resolve(message.getStatus()));
    }

    @GetMapping("/list/{id}")
    public ClienteDto findById(@PathVariable String id,@RequestHeader String user, @RequestHeader String key){
            
        ClienteDto clienteDto = new ClienteDto();
        return (ClienteDto) convertEntity.convert(clienteService.findById(id), clienteDto);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClienteDto>> findAll(@RequestHeader String user, @RequestHeader String key) {

        if (!clienteService.validarUsuarioAdmin(user, key)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<Cliente> clientes = clienteService.findAll();
        List<ClienteDto> clientesDto = new ArrayList<>();
        for (Cliente cliente : clientes) {
            ClienteDto clienteDto = new ClienteDto();
            clientesDto.add((ClienteDto) convertEntity.convert(cliente, clienteDto));
        }

        return new ResponseEntity<>(clientesDto, HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestHeader String user, @RequestHeader String pwd) {
        System.out.println(Hash.sha1("user")+"--"+user);
        Cliente cliente = clienteService.login(user, Hash.sha1(pwd));
        if (cliente != null) {
            System.out.println(cliente.getRoles());
            ClienteDto clienteDto = ((ClienteDto) convertEntity.convert(cliente,new ClienteDto()));
            return new ResponseEntity<>(new CredencialesDto(cliente.getUserName(),
                    Hash.sha1(Hash.sha1(pwd) + Hash.sha1(cliente.getUserName())),clienteDto.getRoles()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Message(401, "Error de credenciales"), HttpStatus.UNAUTHORIZED);
        }

    }

}
