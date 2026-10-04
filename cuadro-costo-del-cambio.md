# Cuadro del costo del cambio

**Equipo:** ____________________
**Integrantes:** ____________________
**Fecha:** ____________________

## Los números

| | Versión A, un solo paquete | Versión B, por capas |
|---|---|---|
| Minutos hasta la prueba en verde | | |
| Archivos tocados | | |
| Líneas agregadas y borradas | | |
| Pruebas que se rompieron | | |
| Lugares donde hubo que buscar | | |

## La conclusión, en tres líneas

1. Qué estructura nos costó menos y en qué dimensión exactamente:
2. Qué dimensión no mejoró, o incluso empeoró:
3. Qué estructura elegimos para nuestro propio sistema, y contra qué escenario de calidad:

## Cómo sacamos los números

```
git status --short
git diff --stat
```

Un número incómodo bien argumentado vale más que uno acomodado. Si la versión B les costó más, escríbanlo y expliquen por qué creen que pasó.
