package com.linktic.ms_productos.domain.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProductoDTO {
    @Column(name = "idproductos")
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Pattern(regexp = "^[\\p{L}0-9 .'-]+$", message = "El nombre contiene caracteres no permitidos")
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @NotNull(message = "El precio del producto es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @DecimalMax(value = "99999999.99", message = "El precio no puede exceder 99,999,999.99")
    @Digits(integer = 8, fraction = 2, message = "El precio debe tener máximo 8 dígitos enteros y 2 decimales")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    @Comment("precio del producto")
    private BigDecimal precio;
     @CreationTimestamp
    @Column(name = "fecha_poblamiento", nullable = false, updatable = false)
    private LocalDateTime fechaPoblamiento;
}