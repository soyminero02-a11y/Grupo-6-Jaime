package com.mgcss.track.application;

import com.mgcss.track.domain.Solicitud;
import java.util.Optional;
import java.util.List;

public interface SolicitudRepository {
    Solicitud save(Solicitud solicitud);
    Optional<Solicitud> findById(Long id);
    List<Solicitud> findAll();
}