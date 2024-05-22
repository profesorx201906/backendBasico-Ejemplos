package com.crud.grupo69.Entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Entity
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String idRole;

    @Enumerated(EnumType.STRING)
    private ERol nombre;

    public Role(ERol nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Role [idRole=" + idRole + ", nombre=" + nombre + "]";
    }

    
    
}
