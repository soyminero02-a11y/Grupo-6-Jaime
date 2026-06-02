package com.mgcss.track.web;

import com.mgcss.track.web.dto.SolicitudRequestDTO;
import com.mgcss.track.web.dto.SolicitudResponseDTO;
import com.mgcss.track.application.SolicitudService;
import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.domain.Solicitud.Estado;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@Tag(name = "Solicitudes", description = "API para la gestión del ciclo de vida de las solicitudes de servicio")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @Operation(summary = "Crear nueva solicitud", description = "Registra una nueva incidencia en el sistema asociándola a un cliente existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Solicitud creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o faltantes")
    })
    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crear(@Valid @RequestBody SolicitudRequestDTO request) {

        Solicitud nueva = service.crearSolicitud(request.id(), request.clienteId(),request.descripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearToDTO(nueva));
    }

    @Operation(summary = "Consultar solicitud por ID", description = "Recupera los detalles completos de una solicitud específica utilizando su identificador único.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada y devuelta"),
        @ApiResponse(responseCode = "404", description = "No se encontró ninguna solicitud con el ID proporcionado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id) {
        return ResponseEntity.ok(mapearToDTO(service.buscarPorId(id)));
    }

    @Operation(summary = "Listar todas las solicitudes", description = "Obtiene un listado completo de todas las solicitudes registradas en el sistema.")
    @ApiResponse(responseCode = "200", description = "Listado recuperado exitosamente (puede estar vacío)")
    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listar() {
        List<SolicitudResponseDTO> lista = service.listarTodas().stream()
                .map(this::mapearToDTO).toList();
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Asignar un técnico", description = "Asigna un técnico activo a una solicitud abierta o en proceso.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Técnico asignado exitosamente"),
        @ApiResponse(responseCode = "400", description = "El técnico no está activo o la solicitud está cerrada"),
        @ApiResponse(responseCode = "404", description = "Solicitud o técnico no encontrados")
    })
    @PutMapping("/{id}/tecnico")
    public ResponseEntity<SolicitudResponseDTO> asignarTecnico(@PathVariable Long id, @RequestBody Long tecnicoId) {
        Solicitud actualizada = service.asignarTecnicoASolicitud(id, tecnicoId);
        return ResponseEntity.ok(mapearToDTO(actualizada));
    }

    @Operation(summary = "Cambiar estado", description = "Actualiza el estado de una solicitud (ej. pasar de EN_PROCESO a CERRADA).")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado modificado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Transición de estado no permitida por las reglas de negocio")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudResponseDTO> cambiarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        Solicitud actualizada = service.cambiarEstado(id, Estado.valueOf(nuevoEstado));
        return ResponseEntity.ok(mapearToDTO(actualizada));
    }

    @Operation(summary = "Reabrir solicitud", description = "Permite reabrir una solicitud que había sido cerrada previamente, pasándola a estado EN_PROCESO.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solicitud reabierta exitosamente"),
        @ApiResponse(responseCode = "400", description = "La solicitud no estaba cerrada previamente")
    })
    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<SolicitudResponseDTO> reabrir(@PathVariable Long id) {
        Solicitud reabierta = service.reabrirSolicitud(id);
        return ResponseEntity.ok(mapearToDTO(reabierta));
    }

    private SolicitudResponseDTO mapearToDTO(Solicitud solicitud) {
        String tecnico = (solicitud.getTecnicoAsignado() != null) ? solicitud.getTecnicoAsignado().getNombre() : "Sin asignar";
        return new SolicitudResponseDTO(
            solicitud.getId(),
            solicitud.getEstado().name(),
            tecnico,
            solicitud.getFechaCreacion(),
            solicitud.getHistorialEstados().stream().map(Estado::name).toList());
    }
}