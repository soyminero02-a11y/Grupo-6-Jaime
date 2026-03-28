package com.mgcss.track.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SolicitudTest {

    @Test
    void no_debe_permitir_cerrar_solicitud_si_no_esta_en_proceso() {
        // Setup inicial (le pasamos un ID 1L para que el constructor no se queje)
        Solicitud solicitud = new Solicitud(1L);

        // Intentamos cerrarla
        assertThrows(IllegalStateException.class, () -> {
            solicitud.cerrar();
        });
    }

    @Test
    void debe_permitir_cerrar_solicitud_si_esta_en_proceso() {
        // Arrange: Creamos una solicitud que YA está en proceso
        Solicitud solicitud = new Solicitud(1L, Solicitud.Estado.EN_PROCESO);
        
        // Act: La cerramos
        solicitud.cerrar();
        
        // Assert: Verificamos que la línea 32 funcionó y el estado cambió
        assertEquals(Solicitud.Estado.CERRADA, solicitud.getEstado());
    }

    @Test
    void al_crear_solicitud_los_datos_iniciales_son_correctos() {
        // Arrange
        Solicitud solicitud = new Solicitud(1L);
        
        // Act & Assert: Comprobamos los getters (¡esto pondrá las líneas rojas en verde!)
        assertEquals(1L, solicitud.getId());
        assertEquals(Solicitud.Estado.ABIERTA, solicitud.getEstado());
        assertNotNull(solicitud.getFechaCreacion());
    }


    @Test
    void debe_permitir_asignar_tecnico_si_esta_activo() {
        // Arrange: Crear solicitud y técnico activo [cite: 273]
        Solicitud solicitud = new Solicitud(1L);
        Tecnico tecnicoActivo = new Tecnico(100L, true);
        
        // Act: Asignación válida [cite: 276]
        solicitud.asignarTecnico(tecnicoActivo);
        
        // Assert: Verificamos que se asignó correctamente
        assertEquals(tecnicoActivo, solicitud.getTecnicoAsignado());
    }

    @Test
    void no_debe_permitir_asignar_tecnico_si_esta_inactivo() {
        // Arrange: Crear solicitud y técnico inactivo [cite: 274]
        Solicitud solicitud = new Solicitud(1L);
        Tecnico tecnicoInactivo = new Tecnico(200L, false);
        
        // Act & Assert: Asignación inválida falla [cite: 277]
        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.asignarTecnico(tecnicoInactivo);
        });
    }
    
}