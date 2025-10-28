# Pruebas de Rendimiento y Estrés

Este directorio contiene las pruebas de rendimiento y estrés para el sistema ecommerce.

## Requisitos

- Locust instalado
- Todos los servicios ecommerce corriendo
- Python 3.7+

## Instalación

```bash
pip install locust
```

## Estructura de Pruebas

### Tipos de Usuarios Simulados

1. **EcommerceUser** (50 usuarios)
   - Navegar productos (alta frecuencia)
   - Ver detalles de producto
   - Crear usuarios
   - Crear órdenes
   - Ver favoritos
   - Procesar pagos

2. **ProductCatalogUser** (30 usuarios)
   - Solo navegación de productos
   - Ver categorías
   - Buscar por categoría

3. **OrderProcessingUser** (10 usuarios)
   - Flujo completo de compra
   - Procesar órdenes y pagos

## Ejecutar Pruebas

### Modo Interactivo (con interfaz web)

```bash
locust -f locustfile.py --host http://localhost:8080
```

Acceder a: http://localhost:8089

### Modo Headless (sin interfaz)

```bash
# Linux/Mac
./run_load_test.sh

# Windows
run_load_test.cmd
```

### Personalizar Parámetros

```bash
locust -f locustfile.py \
       --headless \
       --users 100 \
       --spawn-rate 10 \
       --run-time 600 \
       --host http://localhost:8080
```

## Métricas Evaluadas

- **Tiempo de respuesta** (p95, p99)
- **Throughput** (requests por segundo)
- **Tasa de errores**
- **Tiempo de fallo** (si aplica)

## Umbrales de Rendimiento

- P95 response time: 500ms
- P99 response time: 1000ms
- Error rate: < 1%
- RPS: > 100

## Análisis de Resultados

Los resultados se pueden exportar en varios formatos:

```bash
# HTML
locust --html report.html

# CSV
locust --csv results
```

## Pruebas de Estrés

Para pruebas de estrés, incrementar usuarios:

```bash
locust -f locustfile.py \
       --users 1000 \
       --spawn-rate 50 \
       --host http://localhost:8080
```







