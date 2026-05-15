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

    @Test
    void debe_buscar_por_id_y_encontrarlo() {
        Cliente cliente = new Cliente(1L, "Test", "test@test.com", Cliente.TipoCliente.STANDARD);
        Solicitud sol = new Solicitud(1L, cliente, "Desc");
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(sol));
        
        assertNotNull(solicitudService.buscarPorId(1L));
    }

    @Test
    void debe_listar_todas() {
        when(solicitudRepository.findAll()).thenReturn(java.util.List.of(new Solicitud(1L, null, "Desc")));
        assertFalse(solicitudService.listarTodas().isEmpty());
    }

    @Test
    void debe_cambiar_estado_a_cerrada() {
        Cliente cliente = new Cliente(1L, "Test", "test@test.com", Cliente.TipoCliente.STANDARD);
        
        Solicitud sol = new Solicitud(1L, cliente, "Desc", Solicitud.Estado.EN_PROCESO);
        
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(sol));
        when(solicitudRepository.save(any())).thenReturn(sol);

        Solicitud actualizada = solicitudService.cambiarEstado(1L, Solicitud.Estado.CERRADA);
        assertEquals(Solicitud.Estado.CERRADA, actualizada.getEstado());
    }

    @Test
    void debe_reabrir_solicitud() {
        Cliente cliente = new Cliente(1L, "Test", "test@test.com", Cliente.TipoCliente.STANDARD);
        
        Solicitud sol = new Solicitud(1L, cliente, "Desc", Solicitud.Estado.EN_PROCESO);
        sol.cerrar(); 
        
        when(solicitudRepository.findById(1L)).thenReturn(Optional.of(sol));
        when(solicitudRepository.save(any())).thenReturn(sol);

        Solicitud reabierta = solicitudService.reabrirSolicitud(1L);
        assertEquals(Solicitud.Estado.EN_PROCESO, reabierta.getEstado());
    }
}