package com.mgcss.track.web;

import com.mgcss.track.domain.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para la gestión básica de clientes")
public class ClienteController {

    private final EntityManager entityManager;

    public ClienteController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Operation(summary = "Crear nuevo cliente", description = "Registra un cliente en la base de datos para poder asociarle solicitudes.")
    @PostMapping
    @Transactional 
    public ResponseEntity<Cliente> crear(@RequestBody ClienteRequestDTO request) {
        Cliente nuevo = new Cliente(
            request.id(), 
            request.nombre(), 
            request.email(), 
            Cliente.TipoCliente.valueOf(request.tipoCliente())
        );
        
        entityManager.persist(nuevo);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    public record ClienteRequestDTO(Long id, String nombre, String email, String tipoCliente) {}
}