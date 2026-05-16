package com.mgcss.track.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TecnicoTest {

    @Test
    void debe_crear_tecnico_completo() {
        Tecnico tecnico = new Tecnico(1L, "Ana", "Redes", true);
        
        assertEquals(1L, tecnico.getId());
        assertEquals("Ana", tecnico.getNombre());
        assertEquals("Redes", tecnico.getEspecialidad());
        assertTrue(tecnico.isActivo());
    }

    @Test
    void debe_crear_tecnico_basico() {
        Tecnico tecnico = new Tecnico(2L, false);
        
        assertEquals(2L, tecnico.getId());
        assertFalse(tecnico.isActivo());
    }

    @Test
    void debe_permitir_constructor_vacio_para_jpa() {
        Tecnico tecnico = new Tecnico();
        assertNull(tecnico.getId());
    }
}