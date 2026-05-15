package com.mgcss.track.web;

import com.mgcss.track.web.dto.SolicitudRequestDTO;
import com.mgcss.track.web.dto.SolicitudResponseDTO;
import com.mgcss.track.application.SolicitudService;
import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.domain.Solicitud.Estado;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    private final SolicitudService service;

    public SolicitudController(SolicitudService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crear(@Valid @RequestBody SolicitudRequestDTO request) {
        Solicitud nueva = service.crearSolicitud(request.id(), request.clienteId(), request.descripcion());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearToDTO(nueva));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultar(@PathVariable Long id) {
        return ResponseEntity.ok(mapearToDTO(service.buscarPorId(id)));
    }

    @GetMapping
    public ResponseEntity<List<SolicitudResponseDTO>> listar() {
        List<SolicitudResponseDTO> lista = service.listarTodas().stream()
                .map(this::mapearToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/{id}/tecnico")
    public ResponseEntity<SolicitudResponseDTO> asignarTecnico(@PathVariable Long id, @RequestBody Long tecnicoId) {
        Solicitud actualizada = service.asignarTecnicoASolicitud(id, tecnicoId);
        return ResponseEntity.ok(mapearToDTO(actualizada));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<SolicitudResponseDTO> cambiarEstado(@PathVariable Long id, @RequestBody String nuevoEstado) {
        Solicitud actualizada = service.cambiarEstado(id, Estado.valueOf(nuevoEstado));
        return ResponseEntity.ok(mapearToDTO(actualizada));
    }

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
            solicitud.getHistorialEstados().stream().map(Estado::name).collect(Collectors.toList())
        );
    }
}