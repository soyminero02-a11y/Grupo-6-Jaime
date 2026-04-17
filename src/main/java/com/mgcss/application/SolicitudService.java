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
}