package com.crud.grupo69.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.grupo69.Entity.Role;
import com.crud.grupo69.Service.RolService;

@RestController
@RequestMapping("/api/v1/rol")
public class RolController {

    @Autowired
    RolService rolService;

    @PostMapping("/create")
    public Role save(@RequestBody Role role){
        return rolService.save(role);
    }
    
}
