package com.mgcss.track.infrastructure;

import com.mgcss.track.domain.Solicitud;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest 
@Tag("integration")
class JpaSolicitudRepositoryTest {

    @Autowired
    private JpaSolicitudRepository repository; 

    @Test
    void debe_guardar_y_recuperar_una_solicitud() {
        
        Solicitud nuevaSolicitud = new Solicitud(1L);

       
        Solicitud solicitudGuardada = repository.save(nuevaSolicitud);
        
        
        Optional<Solicitud> solicitudRecuperada = repository.findById(solicitudGuardada.getId());

       
        assertTrue(solicitudRecuperada.isPresent());
        assertEquals(Solicitud.Estado.ABIERTA, solicitudRecuperada.get().getEstado());
        assertEquals(1L, solicitudRecuperada.get().getId());
    }
}