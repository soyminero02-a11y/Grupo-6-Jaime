package com.mgcss.track.infrastructure;

import com.mgcss.track.domain.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSolicitudRepository extends JpaRepository<Solicitud, Long> {
}