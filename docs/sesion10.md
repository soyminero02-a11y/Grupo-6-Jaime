Documentación de Decisión Técnica (Paso 2.3): Aislamiento de la Capa Web y Mapeo de DTOs

Decisión Tomada:
Se ha decidido implementar el patrón DTO utilizando records de Java. El mapeo entre las entidades de dominio y estos DTOs se ha implementado de forma manual mediante un método privado dentro del propio SolicitudController.

Justificación Técnica:

Protección del Modelo de Dominio: Los DTO garantizan que los cambios en el modelo interno de la base de datos o en la lógica de negocio no rompan el contrato de la API externa que consumen los clientes.

Prevención de Vulnerabilidades: Al usar un SolicitudRequestDTO específico para la creación, nos aseguramos de que el usuario no pueda meter campos no deseados directamente en nuestro trabajo.

Mapeo Manual vs Librerías Externas: Se ha optado por el mapeo manual en lugar de incorporar librerías externas de mapeo. Dado el tamaño actual del proyecto, el mapeo manual evita sobrecargar el pom.xml con nuevas dependencias, mejora el tiempo de compilación y otorga un control absoluto sobre transformaciones específicas.

Formato Inmutable: Usamos los records de Java para que los DTOs garanticen que los datos en tránsito sean inmutables y seguros en entornos concurrentes, además de reducir drásticamente el código repetitivo.