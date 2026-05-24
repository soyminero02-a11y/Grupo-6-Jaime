package com.mgcss.track.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.track.domain.Cliente;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void al_crear_cliente_debe_devolver_201_y_json_correcto() throws Exception {
        ClienteController.ClienteRequestDTO request = new ClienteController.ClienteRequestDTO(
                1L, "Empresa Test", "test@empresa.com", "STANDARD"
        );

        doNothing().when(entityManager).persist(any(Cliente.class));

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Empresa Test"));
    }

   
}