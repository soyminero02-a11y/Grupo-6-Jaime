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
        
        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitud));

        // CORRECCIÓN: Le pasamos el ID (100L) en lugar del objeto
        solicitudService.asignarTecnicoASolicitud(solicitudId, 100L);

        verify(solicitudRepository).save(solicitud);
    }

    @Test
    void si_la_solicitud_no_existe_debe_lanzar_excepcion() {
        Long idInexistente = 999L;

        when(solicitudRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            // CORRECCIÓN: Le pasamos el ID (100L) en lugar del objeto
            solicitudService.asignarTecnicoASolicitud(idInexistente, 100L);
        });
        
        verify(solicitudRepository, never()).save(any());
    }

    @Test
    void debe_crear_nueva_solicitud() {
        Long solicitudId = 10L;
        Long clienteId = 5L;
        String descripcion = "Nueva incidencia";
        
        Cliente clienteDummy = new Cliente(clienteId, "Cliente API", "api@test.com", Cliente.TipoCliente.STANDARD);
        Solicitud solicitudGuardada = new Solicitud(solicitudId, clienteDummy, descripcion);
        
        when(solicitudRepository.save(any(Solicitud.class))).thenReturn(solicitudGuardada);

        Solicitud resultado = solicitudService.crearSolicitud(solicitudId, clienteId, descripcion);

        assertNotNull(resultado);
        assertEquals(descripcion, resultado.getDescripcion());
        verify(solicitudRepository).save(any(Solicitud.class));
    }
}