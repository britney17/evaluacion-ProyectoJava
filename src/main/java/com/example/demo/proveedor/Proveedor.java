package com.example.demo.proveedor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Entity                   
    @Table(name = "proveedores") 
    @Data                     
    @NoArgsConstructor        
    @AllArgsConstructor 

public class Proveedor {
   
       
    
    @Id                                                 // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    @NotBlank(message = "El nombre es obligatorio") // No puede venir vacio
    @Column(nullable = false)                       // NOT NULL
    private String nombre;


     @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false)
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Column(nullable = false)
    private String direccion;

    @Column(name = "activo")
    private Boolean activo = true;

    
}
