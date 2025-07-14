package com.linktic.ms_productos.application.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s : '%s'", resourceName, fieldName, fieldValue));
    }
//crea un metodo para indicar que el recurso no fue encontrado
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue, Throwable cause) {
        super(String.format("%s ya existe en la bd %s : '%s'", resourceName, fieldName, fieldValue), cause);
    }
    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
