package com.mgcss.track.infrastructure;

import com.mgcss.track.application.SolicitudRepository;
import com.mgcss.track.domain.Solicitud;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository 
public class SolicitudRepositoryAdapter implements SolicitudRepository {

    private final JpaSolicitudRepository jpaRepository;

    public SolicitudRepositoryAdapter(JpaSolicitudRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        return jpaRepository.save(solicitud);
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<Solicitud> findAll() {
        return jpaRepository.findAll();
    }
}