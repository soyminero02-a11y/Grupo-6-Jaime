package com.mgcss.track.application;

import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.domain.Tecnico;

public class SolicitudService {

    private final SolicitudRepository solicitudRepository;


    public SolicitudService(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

   public void asignarTecnicoASolicitud(Long solicitudId, Tecnico tecnico) {
        
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada con ID: " + solicitudId));
        
        solicitud.asignarTecnico(tecnico);
        solicitudRepository.save(solicitud);
    }

    public Solicitud crearSolicitud(Long solicitudId, Long clienteId, String descripcion) {

        com.mgcss.track.domain.Cliente cliente = new com.mgcss.track.domain.Cliente(
            clienteId, 
            "Cliente API", 
            "api@test.com", 
            com.mgcss.track.domain.Cliente.TipoCliente.STANDARD
        );
        
        Solicitud solicitud = new Solicitud(solicitudId, cliente, descripcion);
        
        return solicitudRepository.save(solicitud);
    }
}