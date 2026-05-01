Análisis de Impacto

1. ¿Qué métodos del dominio se ven afectados?
Se verá afectado el comportamiento general de la clase 'Solicitud'. Se deberá crear un nuevo método 'reabrir()' y modificar la lógica interna para registrar en una lista cada vez que el 'estado' cambie.

2. ¿Qué reglas actuales cambian?
Hasta ahora, el estado 'CERRADA' era terminal. La nueva regla permite que una solicitud 'CERRADA' transite nuevamente al estado 'EN_PROCESO'. Además, el estado ya no es solo un valor estático en el tiempo, sino que tiene un histórico secuencial.

3. ¿Qué tests deberían romperse?
De momento, los tests existentes asumen que solo se puede cerrar si está en proceso. Aunque no había un test explícito de reapertura, si añadiéramos lógica estricta a las transiciones, fallarían. Deberemos crear un test en rojo específico para la reapertura y otro para verificar el registro del histórico.

4. ¿Qué parte del modelo debe extenderse?
La entidad 'Solicitud' debe incluir una nueva estructura de datos. Para mantener la simplicidad y seguir las opciones de diseño, optaremos por una lista interna de cambios de estado para almacenar el histórico.

5. ¿Qué impacto tiene en persistencia?
Si el histórico se persiste en la base de datos H2, necesitaremos añadir un mapeo JPA especial a la lista del histórico dentro de la entidad 'Solicitud' y verificar que se guarda correctamente mediante un test de integración.