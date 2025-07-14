package com.linktic.ms_productos.infraestructure.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HealtCheck {
   
    @GetMapping("/")
    public String hc() {
        return "microservicio productos activo";
    }
  
}