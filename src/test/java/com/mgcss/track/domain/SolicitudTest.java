package com.mgcss.track.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void no_debe_permitir_cerrar_solicitud_si_no_esta_en_proceso() {
      
        Solicitud solicitud = new Solicitud(1L);
        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar();
        });
    }

    @Test
    void debe_permitir_cerrar_solicitud_si_esta_en_proceso() {
       
        Solicitud solicitud = new Solicitud(1L, Solicitud.Estado.EN_PROCESO);
        solicitud.cerrar();
        assertEquals(Solicitud.Estado.CERRADA, solicitud.getEstado());
    }

    @Test
    void al_crear_solicitud_los_datos_iniciales_son_correctos() {
       
        Solicitud solicitud = new Solicitud(1L);
        assertEquals(1L, solicitud.getId());
        assertEquals(Solicitud.Estado.ABIERTA, solicitud.getEstado());
        assertNotNull(solicitud.getFechaCreacion());
    }

    @Test
    void debe_permitir_asignar_tecnico_si_esta_activo() {

        Solicitud solicitud = new Solicitud(1L);
        Tecnico tecnicoActivo = new Tecnico(100L, true);
        solicitud.asignarTecnico(tecnicoActivo);
        assertEquals(tecnicoActivo, solicitud.getTecnicoAsignado());
    }

    @Test
    void no_debe_permitir_asignar_tecnico_si_esta_inactivo() {

        Solicitud solicitud = new Solicitud(1L);
        Tecnico tecnicoInactivo = new Tecnico(200L, false);
        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.asignarTecnico(tecnicoInactivo);
        });
    }

    @Test
    void debe_permitir_reabrir_solicitud_cerrada() {
      
        Solicitud solicitud = new Solicitud(1L, Solicitud.Estado.EN_PROCESO);
        solicitud.cerrar();
        solicitud.reabrir();
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getEstado());
    }

    @Test
    void debe_guardar_el_historico_de_cambios_de_estado() {
        
        Solicitud solicitud = new Solicitud(1L, Solicitud.Estado.EN_PROCESO);
        
        solicitud.cerrar();
        solicitud.reabrir();
        
        assertEquals(3, solicitud.getHistorialEstados().size());
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getHistorialEstados().get(0));
        assertEquals(Solicitud.Estado.CERRADA, solicitud.getHistorialEstados().get(1));
        assertEquals(Solicitud.Estado.EN_PROCESO, solicitud.getHistorialEstados().get(2));
    }
    
}