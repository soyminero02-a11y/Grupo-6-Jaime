# Evidencias de Pruebas en Swagger UI

## Caso 1 – Crear solicitud
Request:
POST /api/solicitudes
{
  "id": 1,
  "nombre": "Empresa Test",
  "email": "test@empresa.com",
  "tipoCliente": "STANDARD"
}
Response esperado:
200 OK
{
  "id": 1,
  "nombre": "Empresa Test",
  "email": "test@empresa.com",
  "tipoCliente": "STANDARD"
}


## Caso 2 – Cerrar solicitud incorrectamente
Precondición:
Solicitud en estado ABIERTA
Acción:
"CERRADA"
Resultado esperado:
  "status": 500,
  "error": "Internal Server Error",