package com.mgcss.track.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    private Cliente clienteTest = new Cliente(1L, "Empresa S.A.", "contacto@empresa.com", Cliente.TipoCliente.STANDARD);

    @Test
    void no_debe_permitir_cerrar_solicitud_si_no_esta_en_proceso() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba");
        assertThrows(IllegalStateException.class, solicitud::cerrar);
    }

    @Test
    void debe_permitir_cerrar_solicitud_si_esta_en_proceso() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba", Solicitud.Estado.EN_PROCESO);
        solicitud.cerrar();
        assertEquals(Solicitud.Estado.CERRADA, solicitud.getEstado());
    }

    @Test
    void al_crear_solicitud_los_datos_iniciales_son_correctos() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba");
        assertEquals(1L, solicitud.getId());
        assertEquals(Solicitud.Estado.ABIERTA, solicitud.getEstado());
        assertEquals("Incidencia de prueba", solicitud.getDescripcion());
        assertNotNull(solicitud.getFechaCreacion());
    }

    @Test
    void debe_permitir_asignar_tecnico_si_esta_activo() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba");
        Tecnico tecnicoActivo = new Tecnico(100L, true);
        solicitud.asignarTecnico(tecnicoActivo);
        assertEquals(tecnicoActivo, solicitud.getTecnicoAsignado());
    }

    @Test
    void no_debe_permitir_asignar_tecnico_si_esta_inactivo() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba");
        Tecnico tecnicoInactivo = new Tecnico(200L, false);
        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.asignarTecnico(tecnicoInactivo);
        });
    }

    @Test
    void debe_permitir_reabrir_solicitud_cerrada() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba", Solicitud.Estado.EN_PROCESO);
        solicitud.cerrar();
        solicitud.reabrir();
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
    }

    @Test
    void debe_guardar_el_historico_de_cambios_de_estado() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Incidencia de prueba", Solicitud.Estado.EN_PROCESO);

        solicitud.cerrar();
        solicitud.reabrir();
        
        assertEquals(3, solicitud.getHistorialEstados().size());
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getHistorialEstados().get(0));
        assertEquals(Solicitud.Estado.CERRADA, solicitud.getHistorialEstados().get(1));
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getHistorialEstados().get(2));
    }

    @Test
    void debe_recuperar_los_datos_basicos_y_de_cierre() {
        Solicitud solicitud = new Solicitud(1L, clienteTest, "Prueba de getters", Solicitud.Estado.EN_PROCESO);
        
        assertEquals("Prueba de getters", solicitud.getDescripcion());
        assertEquals(clienteTest, solicitud.getCliente());
        assertNull(solicitud.getFechaCierre()); 
        
        solicitud.cerrar();
        assertNotNull(solicitud.getFechaCierre()); 
    }
}