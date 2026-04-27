Análisis de Refactorización
(Hemos elegido de los cuatro tipos el de: Clase con demasiadas responsabilidades)

1. Problema identificado
En la clase 'Solicitud.java', el método 'asignarTecnico(Tecnico tecnico)' contiene un condicional que lanza una excepción si el técnico no está activo. Esto es un "Code Smell" que podría crecer en complejidad si se añaden más validaciones en el futuro.

2. Métrica asociada
Maintainability Rating y Complejidad Ciclomática.

3. Riesgo potencial si no se corrige
Si en el futuro hay que comprobar más cosas, el método de asignación se volverá gigante, difícil de testear y no cumplira el principio de Responsabilidad Única.
