package com.mgcss.track.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void debe_crear_cliente_y_recuperar_sus_datos() {
        Cliente cliente = new Cliente(1L, "Juan", "juan@test.com", Cliente.TipoCliente.PREMIUM);
        
        assertEquals(1L, cliente.getId());
        assertEquals("Juan", cliente.getNombre());
        assertEquals("juan@test.com", cliente.getEmail());
        assertEquals(Cliente.TipoCliente.PREMIUM, cliente.getTipoCliente());
    }

    @Test
    void debe_permitir_constructor_vacio_para_jpa() {
        Cliente cliente = new Cliente();
        assertNull(cliente.getId());
    }

    @Test
    void test_enum_tipo_cliente() {
        assertEquals(2, Cliente.TipoCliente.values().length);
        assertEquals(Cliente.TipoCliente.STANDARD, Cliente.TipoCliente.valueOf("STANDARD"));
    }
}