# ms-productos  Microservicio Para la Gestion del Producto, Desarrollado en Spring Boot 

## Descripción
Esta solución consiste en dos microservicios independientes:
1. **Servicio Producto**: Gestiona productos con sus atributos básicos.
2. **Servicio Inventario**: Gestiona el inventario de productos, comunicándose con el servicio de producto.

## 1. Inicialización del Microservicio
- **Esqueleto creado con**: [Spring Initializr](https://start.spring.io/)
- **Versión de Java**: 17
- **Versión de Spring Boot**: 3.5.3

## 2. Configuración del Proyecto
### Gestor de Dependencias
- **Maven**

### Dependencias Principales
| Dependencia               | Descripción                                                                 |
|---------------------------|-----------------------------------------------------------------------------|
| **Spring Web**            | Para construir aplicaciones web y APIs REST.                                |
| **Lombok**                | Simplifica el código reduciendo boilerplate (getters, setters, constructores). |
| **Validation**            | Validación de objetos Java (DTOs/entidades) con anotaciones como `@NotNull`. |
| **Spring Boot DevTools**  | Herramientas de desarrollo (reinicio automático, configuración para dev).   |
| **SpringDoc**             | Genera documentación automática de APIs (OpenAPI 3 + Swagger UI).           |
| **Logstash**              | Envío de logs estructurados en JSON a sistemas como ELK.                    |
| **SpringDoc**             | Genera documentación automática de APIs (OpenAPI 3 + Swagger UI).           |

## 3. Herramientas de Desarrollo
- **IDE Principal**: [VS Code](https://code.visualstudio.com/download)

### Comandos Útiles
```bash
# Validar compilación
mvn clean compile

# Generar empaquetado (JAR)
mvn clean package
```
## 4. Desarrollado con Arquitectura Hexagonal

- **Dominio**:
  - `model`: Entidades de negocio
  - `dto`: Objetos de transferencia de datos
  
- **Aplicación**:
  - `exceptions`: Excepciones personalizadas
  - `mapper`: Convertidores entre entidades y DTOs (usar MapStruct: https://mapstruct.org/)
  - `service`: Interfaces de servicio e implementación
  - `ports`: Interfaces para repositorios y servicios externos
  
- **Infraestructura**:
  - `controller`: Controladores REST
  - `entity`: Entidades de persistencia
  - `repository`: Implementaciones de repositorios
  - `config`: Configuraciones
  - `adapter`: Adaptadores para servicios externos

## 5. OpenAPI
- ** Configuracion Springdoc: basica. 

- ** Probar API: Acceder a http://localhost:8081/swagger-ui/index.html


## 6. Pruebas con Postman
- ** Importar en Postman: https://www.postman.com/downloads/ para hacer Pruebas: en la carpeta postman se encuentra el archivo MS Productos Pruebas.postman_collection que tiene la descripcion,
 y  pruebas  de ejemplo para el  consumo del microservicio.
 
## 7. Containerización Docker

### Dockerfile

```dockerfile
FROM openjdk:17-jdk

WORKDIR /app
COPY target/*.jar micro.jar

CMD java $JAVA_OPTS -jar /app/micro.jar
```

### docker-compose.yml

```yaml
version: '3.8'

services:
  mysql-db:
    image: mysql:8.0
    container_name: mysql-db
    environment:
      MYSQL_ROOT_PASSWORD: admind
      MYSQL_DATABASE: linktic_db
    volumes:
      - mysql_data:/var/lib/mysql
    ports:
      - "3306:3306"
    networks:
      - reto
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost"]
      interval: 5s
      timeout: 10s
      retries: 5

  ms-productos:
    image: dapanew/ms-productos:1
    container_name: ms-productos-01
    environment:
      - SPRING_APPLICATION_NAME=ms-productos
      - SERVER_PORT=8081
      - DATABASE_URL=jdbc:mysql://mysql-db:3306/linktic_db
      - DATABASE_USERNAME=root
      - DATABASE_PASSWORD=admind
      - SPRING_JPA_DATABASE-PLATFORM=org.hibernate.dialect.MySQLDialect
      - SPRING_JPA_SHOW-SQL=true
      - SPRINGDOC_API-DOCS_PATH=/api-docs
      - SPRINGDOC_SWAGGER-UI_PATH=/swagger-ui.html
      - LOGGING_LEVEL_ORG_SPRINGFRAMEWORK=INFO
      - LOGGING_LEVEL_COM_LITE=WARN
      - LOGGING_LOGSTASH_ENABLED=true
      - LOGSTASH_SERVER=logstash:5000
      - JAVA_OPTS=-Xms200m -Xmx256m
    ports:
      - "9091:8081"
    depends_on:
      mysql-db:
        condition: service_healthy
    networks:
      - reto
      - elk_elk
    restart: always

networks:
  reto:
    external: true
  elk_elk:
    external: true

volumes:
  mysql_data:
```
##  8. Diagrama de  Arquitectura DRAWIO: 
- ** En  en la carpeta arquitectura se encuentra el archivo aqruitectura_microservicios.drawio, que tiene la arquitectura propuesta para los microservicios con diferentes Hojas que explican la arquitectura del desarrollo, este se debe importar  con DRAWIO:  https://www.drawio.com/.
- Relacion de diagramas de arquitectura
| Nombre                     | Descripción                                                                 |
|--------------------------- |-----------------------------------------------------------------------------|
| **Arquitectura General**   | Describe la arq de los 2 microservicios en su construcion.                  |
| **Arq-Feing**              | Describe la comunicacion de los 2 microservicios                            |
| **Arq_Profile**            |   Uso de recursos   memoria y CPU.                                          |
| **Arq_Docker_local**       | Se describe la estrategia para la generacion de las imagenes Docker         |
| **Arq_imp_CI-CD-PIPELINE** | Estrategia de generacion de Pipeline despliegue en nube                     |

 ### Comandos para construir y ejecutar genericos. en el proyecto esta el archivo README.md con la informacion.

```bash
# Construir imagen
docker build -t microservicio-demo:1.0 .

# Levantar con docker-compose
docker-compose --env-file .env up -d

# Verificar contenedores
docker ps

# Acceder a swagger
# http://localhost:8081/swagger-ui.html
```
### Nota: Para efectos de facilitar las pruebas se dejo quemado en el archivo de properties, el valor de las variables. 
- ** Por buena practicas las imágenes deberían quedar agnósticas lo que nos permitiria desplegar en diferentes nubes
- 
- **Herramientas de profile recomendadas**:
  - Java Mission Control: https://www.oracle.com/java/technologies/jdk-mission-control.html
  - VisualVM: https://visualvm.github.io/
  - Jprofiler: https://www.ej-technologies.com/jprofiler
