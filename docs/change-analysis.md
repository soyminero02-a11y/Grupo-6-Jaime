Análisis de Impacto

Fase 2

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


Paso 3.4

(Hemos usado la opción: Lista interna de cambios de estado.)

Porque el estado de la solicitud es un enumerado simple, usar una lista interna  es la opción más directa y menos invasiva. Asi se evita crear nuevas tablas o entidades separadas que aumentarían innecesariamente la complejidad del modelo en esta etapa temprana del requisito.

Fase 4: Impacto en persistencia
En esta iteración, el 'historialEstados' se mantiene en memoria y no se persiste en la base de datos. 

Justificación (Mantenibilidad):Para no bloquear el despliegue del nuevo requisito funcional, optamos por no alterar el esquema de base de datos todavía. Esto reduce el riesgo de impacto y la deuda técnica inmediata, posponiendo la migración de la tabla para una iteración futura donde se defina si el histórico requiere una tabla propia o una colección elemental.


Fase 5: Revisión de Métricas (Post-Merge)

Tras fusionar el código en la rama principal, se han revisado las métricas en SonarCloud:
- Maintainability Rating: Se mantiene en A. No se han introducido Code Smells críticos.
- Complejidad Ciclomática: Ha habido un ligero incremento natural al añadir los métodos 'reabrir()' y 'getHistorialEstados()', pero la complejidad por método sigue siendo muy baja al no tener bucles ni condicionales complejos.
- Technical Debt: Se estima que se tardaría 2 minutos en solucionar el error
- Conclusión: El cambio se ha integrado de forma segura sin degradar la calidad del código, gracias a la metodología TDD y a la refactorización previa.