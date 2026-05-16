package com.mgcss.track.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.track.application.SolicitudService;
import com.mgcss.track.domain.Cliente;
import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.web.dto.SolicitudRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @MockBean
    private SolicitudService solicitudService;

    @Autowired
    private ObjectMapper objectMapper; 

    private Cliente clienteDummy;
    private Solicitud solicitudDummy;

    @BeforeEach
    void setUp() {
        clienteDummy = new Cliente(100L, "Cliente Test", "test@test.com", Cliente.TipoCliente.STANDARD);
        solicitudDummy = new Solicitud(1L, clienteDummy, "Incidencia de prueba", Solicitud.Estado.ABIERTA);
    }

    @Test
    void al_crear_solicitud_debe_devolver_201_y_json_correcto() throws Exception {
        SolicitudRequestDTO request = new SolicitudRequestDTO(1L, 100L, "Incidencia de prueba");
        
        when(solicitudService.crearSolicitud(anyLong(), anyLong(), anyString())).thenReturn(solicitudDummy);

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ABIERTA"))
                .andExpect(jsonPath("$.nombreTecnico").value("Sin asignar"));
    }

    @Test
    void al_consultar_por_id_debe_devolver_200_y_json_correcto() throws Exception {
        when(solicitudService.buscarPorId(1L)).thenReturn(solicitudDummy);

        mockMvc.perform(get("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.estado").value("ABIERTA"));
    }

    @Test
    void al_listar_todas_debe_devolver_200_y_array_json() throws Exception {
        when(solicitudService.listarTodas()).thenReturn(List.of(solicitudDummy));

        mockMvc.perform(get("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void al_asignar_tecnico_debe_devolver_200() throws Exception {
        when(solicitudService.asignarTecnicoASolicitud(anyLong(), anyLong())).thenReturn(solicitudDummy);

        mockMvc.perform(put("/api/solicitudes/1/tecnico")
                .contentType(MediaType.APPLICATION_JSON)
                .content("100"))
                .andExpect(status().isOk());
    }

    @Test
    void al_cambiar_estado_debe_devolver_200() throws Exception {
        when(solicitudService.cambiarEstado(anyLong(), any(Solicitud.Estado.class))).thenReturn(solicitudDummy);

        mockMvc.perform(put("/api/solicitudes/1/estado")
                .contentType(MediaType.APPLICATION_JSON)
                .content("CERRADA"))
                .andExpect(status().isOk());
    }

    @Test
    void al_reabrir_debe_devolver_200() throws Exception {
        when(solicitudService.reabrirSolicitud(anyLong())).thenReturn(solicitudDummy);

        mockMvc.perform(patch("/api/solicitudes/1/reabrir")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

     @Test
    void al_crear_solicitud_con_datos_nulos_debe_devolver_400() throws Exception {
        SolicitudRequestDTO requestMalo = new SolicitudRequestDTO(null, null, "Sin IDs");

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestMalo)))
                .andExpect(status().isBadRequest());
    }
}