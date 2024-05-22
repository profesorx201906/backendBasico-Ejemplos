package com.crud.grupo69.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.grupo69.Dto.CuentaDto;
import com.crud.grupo69.Entity.Cuenta;
import com.crud.grupo69.Entity.Message;
import com.crud.grupo69.Service.CuentaService;
import com.crud.grupo69.Utility.ConvertEntity;



@RestController
@RequestMapping("/api/v1/cuenta")
@CrossOrigin(origins = "*")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    ConvertEntity convertEntity = new ConvertEntity();
    
    @PostMapping("/create")
    public Cuenta save(@RequestBody Cuenta cuenta){
        
        return cuentaService.save(cuenta);
    }

    @PutMapping("/update")
    public Cuenta update(@RequestBody Cuenta cuenta){        
        return cuentaService.save(cuenta);
    }

    @GetMapping("/list")
    public List<CuentaDto> findAll(){

        List<Cuenta> Cuentas = cuentaService.findAll();
        List<CuentaDto> CuentasDto = new ArrayList<>();
        for (Cuenta Cuenta : Cuentas) {
            CuentaDto CuentaDto = new CuentaDto();
           CuentasDto.add((CuentaDto) convertEntity.convert(Cuenta, CuentaDto));
        }  
        return CuentasDto;
    }

    @GetMapping("/list/{id}")
    public CuentaDto findAllId(@PathVariable("id") String id){
        CuentaDto CuentaDto = new CuentaDto();
        Cuenta cuenta = cuentaService.findAllId(id);
        return (CuentaDto)convertEntity.convert(cuenta, CuentaDto);        
    }

    @DeleteMapping("/delete/{id}")
    public Message deleteById(@PathVariable("id") String id){
        return cuentaService.deleteById(id);
    }
}
