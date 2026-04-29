# Procesador Utilidades — FamilySpences

## Stack
- Spring Boot 3.x, Java 21
- PostgreSQL (AWS RDS compartida con `familyspencesapi`)
- RabbitMQ (localhost:5672, guest/guest)
- Puerto: **8090**

## Para correr
```bash
./gradlew bootRun
```
Requiere RabbitMQ corriendo localmente. La BD ya está en AWS.

## Estructura
```
config/messages/      # Configuración de exchanges/queues RabbitMQ
  notifications/      # Colas: create, delete, mark-read, mark-all-read
  categories/         # Colas: create, update, delete
messaging/            # Consumers (reciben mensajes) y Producers (envían)
service/              # Lógica de negocio
repository/           # Acceso a BD (JPA)
domain/               # Entidades JPA
```

## Funcionalidad notificaciones implementada

### Colas RabbitMQ (exchange: `familyspences.notifications.exchange`)
| Cola | Routing Key | Acción |
|---|---|---|
| `familyspences.notifications.queue` | `familyspences.notifications.key` | Crear notificación |
| `familyspences.notifications.delete.queue` | `familyspences.notifications.delete` | Eliminar por `notificationId` |
| `familyspences.notifications.mark.read.queue` | `familyspences.notifications.mark.read` | Marcar como leída por `notificationId` |
| `familyspences.notifications.mark.all.read.queue` | `familyspences.notifications.mark.all.read` | Marcar todas como leídas por `userId` |

### Mensajes esperados (JSON)
```json
// Crear notificación
{ "userId": "uuid", "message": "texto", "type": "INFO", "priority": "NORMAL" }

// Eliminar / marcar como leída
{ "notificationId": "uuid" }

// Marcar todas como leídas
{ "userId": "uuid" }
```

## Funcionalidad categorías
- Valida unicidad de nombre por familia antes de guardar/actualizar
- Crea notificación automática al crear/editar/eliminar categoría (si `CategoryDTO.userId` está presente)
- Nuevo método: `getHierarchyByFamilyId(UUID)` — devuelve categorías ordenadas por tipo

## Coordinación con otros equipos
- **`CategoryDTO`** recibe campo opcional `userId` — la API debe enviarlo en los eventos de categoría para que el procesador pueda crear la notificación correspondiente
- Los routing keys de RabbitMQ deben coincidir exactamente entre la API y este procesador
