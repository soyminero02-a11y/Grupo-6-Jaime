package com.mgcss.track.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

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




    
}