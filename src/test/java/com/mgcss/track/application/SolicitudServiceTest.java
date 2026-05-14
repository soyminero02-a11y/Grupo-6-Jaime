package com.mgcss.track.application;

import com.mgcss.track.domain.Cliente;
import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.domain.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SolicitudServiceTest {

    @Mock 
    private SolicitudRepository solicitudRepository;

    @InjectMocks
    private SolicitudService solicitudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void al_asignar_tecnico_valido_debe_guardar_solicitud_actualizada() {
        Long solicitudId = 1L;
        Cliente clienteTest = new Cliente(1L, "Cliente Service", "service@test.com", Cliente.TipoCliente.STANDARD);
        
      
        Solicitud solicitud = new Solicitud(solicitudId, clienteTest, "Prueba de servicio", Solicitud.Estado.ABIERTA);
        
        Tecnico tecnico = new Tecnico(100L, true);

        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitud));

        solicitudService.asignarTecnicoASolicitud(solicitudId, tecnico);

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void si_la_solicitud_no_existe_debe_lanzar_excepcion() {
        Long idInexistente = 999L;
        Tecnico tecnico = new Tecnico(100L, true);

        when(solicitudRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            solicitudService.asignarTecnicoASolicitud(idInexistente, tecnico);
        });
        
        verify(solicitudRepository, never()).save(any());
    }
}