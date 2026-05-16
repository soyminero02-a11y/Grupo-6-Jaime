package com.mgcss.track.infrastructure;

import com.mgcss.track.domain.Cliente;
import com.mgcss.track.domain.Solicitud;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest 
@Tag("integration")
class JpaSolicitudRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JpaSolicitudRepository repository; 

    @Test
    void debe_guardar_y_recuperar_una_solicitud() {

        Cliente clienteDummy = new Cliente(1L, "Repo Test", "repo@test.com", Cliente.TipoCliente.STANDARD);
        entityManager.persist(clienteDummy);
        
        Solicitud nuevaSolicitud = new Solicitud(1L, clienteDummy, "Prueba desde Repositorio");

        Solicitud solicitudGuardada = repository.save(nuevaSolicitud);
        
        Optional<Solicitud> solicitudRecuperada = repository.findById(solicitudGuardada.getId());

        assertTrue(solicitudRecuperada.isPresent());
        assertEquals(Solicitud.Estado.ABIERTA, solicitudRecuperada.get().getEstado());
        assertEquals(1L, solicitudRecuperada.get().getId());
        assertEquals("Prueba desde Repositorio", solicitudRecuperada.get().getDescripcion());
    }
}