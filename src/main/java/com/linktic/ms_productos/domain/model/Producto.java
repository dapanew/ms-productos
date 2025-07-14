package com.linktic.ms_productos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private LocalDateTime fechaPoblamiento;
}
