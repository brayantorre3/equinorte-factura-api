# equinorte-factura-api

Backend de la prueba técnica para Equinorte.
Construido con **Spring Boot 3 + H2 Database**.

> Frontend: https://github.com/brayantorre3/equinorte-factura-app

---

## Tecnologías

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database (en memoria)
- Maven

---

## Requisitos previos

- Java 17+
- Apache Maven 3.9+

---

## Instalación y ejecución

```bash
git clone https://github.com/brayantorre3/equinorte-factura-api.git
cd equinorte-factura-api
mvn spring-boot:run
```

El servidor arranca en: `http://localhost:8080`

---

## Base de datos

H2 en memoria, se inicializa automáticamente al arrancar con datos de prueba desde `data.sql`.

**Consola H2:** `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: *(vacía)*

**Datos iniciales:**
- 1 factura: FAC-001 — Empresa ABC S.A.S — Subtotal $80.000
- 3 productos asociados

---

## Endpoints

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/facturas` | Lista todas las facturas |
| GET | `/api/facturas/{id}` | Detalle de una factura con sus productos |
| PUT | `/api/facturas/{id}/recalcular` | Recalcula la factura proporcionalmente |

### PUT /api/facturas/{id}/recalcular

**Request body:**
```json
{
  "nuevoSubtotal": 95000,
  "tipoUsuario": "A"
}
```

**Response:**
```json
{
  "id": 1,
  "numeroFactura": "FAC-001",
  "cliente": "Empresa ABC S.A.S",
  "subtotalProducto": 95000,
  "iva": 18050,
  "total": 113050,
  "detalles": [
    {
      "nombreProducto": "Producto 1",
      "valorUnitario": 35625,
      "subtotalProducto": 35625
    }
  ]
}
```

---

## Reglas de negocio

| Tipo de usuario | Incremento máximo permitido |
|---|---|
| Tipo A | + $20.000 sobre el subtotal original |
| Tipo B | + $50.000 sobre el subtotal original |

- Solo se permiten incrementos (no reducciones).
- El nuevo subtotal se distribuye proporcionalmente entre los productos.
- El IVA (19%) se recalcula automáticamente.

---

## Estructura del proyecto

```
src/main/java/com/equinorte/factura/
├── controller/
│   └── FacturaController.java
├── service/
│   └── FacturaService.java
├── repository/
│   └── FacturaRepository.java
└── model/
    ├── Factura.java
    └── DetalleFactura.java

src/main/resources/
├── application.properties
└── data.sql
```

---

## Docker

Para correr el proyecto completo (backend + frontend) con Docker, ambos repositorios deben estar clonados en la misma carpeta:

```
alguna-carpeta/
├── equinorte-factura-api/
└── equinorte-factura-app/
```

```bash
git clone https://github.com/brayantorre3/equinorte-factura-api.git
git clone https://github.com/brayantorre3/equinorte-factura-app.git
cd equinorte-factura-api
docker-compose up --build
```

- Frontend: http://localhost
- Backend: http://localhost:8080

---

## Autor

**Brayan Torres** — [@brayantorre3](https://github.com/brayantorre3)
