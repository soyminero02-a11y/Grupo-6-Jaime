Análisis de Refactorización

Paso 2

(Hemos elegido de los cuatro tipos el de: Clase con demasiadas responsabilidades)

1. Problema identificado
En la clase 'Solicitud.java', el método 'asignarTecnico(Tecnico tecnico)' contiene un condicional que lanza una excepción si el técnico no está activo. Esto es un "Code Smell" que podría crecer en complejidad si se añaden más validaciones en el futuro.

2. Métrica asociada
Maintainability Rating y Complejidad Ciclomática.

3. Riesgo potencial si no se corrige
Si en el futuro hay que comprobar más cosas, el método de asignación se volverá gigante, difícil de testear y no cumplira el principio de Responsabilidad Única.


Paso 5

1. Qué métrica mejoró:
Se mantiene el Maintainability Rating en A, pero se ha contenido la Complejidad Ciclomática del método principal pasando de tener 2 issues a solo una después del cambio. También hemos tenido un ligero aumento del coverage que ha pasado de un 93,6 a un 93,9. El resto del trabajo se ha mantenido todo en A.

2. Qué técnica de refactor se aplicó:
"Extract Method".

3. Qué beneficio aporta: 
El código ahora se lee como lenguaje natural y las futuras validaciones se podrán añadir en el método privado sin ensuciar la lógica principal.