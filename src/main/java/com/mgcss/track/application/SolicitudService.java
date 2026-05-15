package com.mgcss.track.application;

import com.mgcss.track.domain.Cliente;
import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.domain.Tecnico;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;

    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    public Solicitud crearSolicitud(Long id, Long clienteId, String descripcion) {
        Cliente cliente = new Cliente(clienteId, "Cliente API", "api@test.com", Cliente.TipoCliente.STANDARD);
        Solicitud solicitud = new Solicitud(id, cliente, descripcion);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud buscarPorId(Long id) {
        return solicitudRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
    }

    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    public Solicitud asignarTecnicoASolicitud(Long id, Long tecnicoId) {
        Solicitud solicitud = buscarPorId(id);
        Tecnico tecnico = new Tecnico(tecnicoId, "Técnico API", "General", true);
        solicitud.asignarTecnico(tecnico);
        return solicitudRepository.save(solicitud);
    }

    public Solicitud cambiarEstado(Long id, Solicitud.Estado nuevoEstado) {
        Solicitud solicitud = buscarPorId(id);
        if (nuevoEstado == Solicitud.Estado.CERRADA) {
            solicitud.cerrar();
        }
        return solicitudRepository.save(solicitud);
    }

    public Solicitud reabrirSolicitud(Long id) {
        Solicitud solicitud = buscarPorId(id);
        solicitud.reabrir();
        return solicitudRepository.save(solicitud);
    }
}