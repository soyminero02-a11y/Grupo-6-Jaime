package com.mgcss.track.application;

import com.mgcss.track.domain.Solicitud;
import com.mgcss.track.domain.Tecnico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

class SolicitudServiceTest {

    @Mock // 1. Creamos el mock del repositorio (simulamos la BD)
    private SolicitudRepository solicitudRepository;

    @InjectMocks // 2. Inyectamos el mock en nuestro servicio
    private SolicitudService solicitudService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void al_asignar_tecnico_valido_debe_guardar_solicitud_actualizada() {
        // Arrange: Preparamos los datos
        Long solicitudId = 1L;
        Solicitud solicitud = new Solicitud(solicitudId, Solicitud.Estado.ABIERTA);
        Tecnico tecnico = new Tecnico(100L, true);

        // Simulamos el comportamiento del repositorio falso
        when(solicitudRepository.findById(solicitudId)).thenReturn(Optional.of(solicitud));

        // Act: Ejecutamos el método del servicio
        solicitudService.asignarTecnicoASolicitud(solicitudId, tecnico);

        // Assert: Verificamos que el servicio mandó a guardar la solicitud (verify)
        verify(solicitudRepository).save(solicitud);
    }


@Test
    void si_la_solicitud_no_existe_debe_lanzar_excepcion() {
        // Arrange: Preparamos un ID que no existe y un técnico válido
        Long idInexistente = 999L;
        Tecnico tecnico = new Tecnico(100L, true);

        // Simulamos que la base de datos NO encuentra nada (devuelve un Optional vacío)
        when(solicitudRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // Act & Assert: Verificamos que lanza una excepción específica (IllegalArgumentException)
        assertThrows(IllegalArgumentException.class, () -> {
            solicitudService.asignarTecnicoASolicitud(idInexistente, tecnico);
        });
        
        // Verificamos que NUNCA se intentó guardar nada si falló la búsqueda
        verify(solicitudRepository, never()).save(any());
    }

}