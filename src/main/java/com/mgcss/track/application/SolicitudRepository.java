package com.mgcss.track.application;

import com.mgcss.track.domain.Solicitud;
import java.util.Optional;

public interface SolicitudRepository {
    Solicitud save(Solicitud solicitud);
    Optional<Solicitud> findById(Long id);
}