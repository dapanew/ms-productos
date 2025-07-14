package com.linktic.ms_productos.infraestructure.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.linktic.ms_productos.application.services.ProductoRepository;
import com.linktic.ms_productos.application.services.ProductoUseCase;
import  com.linktic.ms_productos.application.services.ProductoService;

@Configuration
public class BeanConfiguration {
    
    @Bean
    public ProductoUseCase productoUseCase(ProductoRepository productoRepository) {
        return new ProductoService(productoRepository);
    }
}
