"""
Pruebas de rendimiento y estrés para el sistema ecommerce
Utiliza Locust para simular carga en los microservicios
"""

from locust import HttpUser, task, between
import json

class EcommerceUser(HttpUser):
    """
    Usuario que simula comportamiento de un cliente de ecommerce
    """
    wait_time = between(1, 3)  # Espera entre 1 y 3 segundos entre tareas
    host = "http://localhost:8080"  # API Gateway
    
    def on_start(self):
        """
        Se ejecuta cuando el usuario inicia una sesión
        Simula login o inicialización
        """
        self.token = None
        
    @task(3)
    def browse_products(self):
        """
        Tarea: Navegar productos (alta frecuencia)
        """
        self.client.get("/app/api/products")
        
    @task(2)
    def view_product_details(self):
        """
        Tarea: Ver detalles de producto
        """
        product_id = 1  # Simular ver producto específico
        self.client.get(f"/app/api/products/{product_id}")
        
    @task(2)
    def browse_users(self):
        """
        Tarea: Obtener lista de usuarios
        """
        self.client.get("/app/api/users")
        
    @task(1)
    def create_user(self):
        """
        Tarea: Crear nuevo usuario
        """
        payload = {
            "userId": 999,
            "firstName": "Load",
            "lastName": "Test",
            "email": "load@test.com",
            "imageUrl": "https://example.com/test.jpg"
        }
        headers = {"Content-Type": "application/json"}
        self.client.post("/app/api/users", 
                         data=json.dumps(payload), 
                         headers=headers)
        
    @task(1)
    def create_order(self):
        """
        Tarea: Crear orden
        """
        payload = {
            "orderId": 999,
            "orderTrackingNumber": "TEST-001",
            "orderTotal": 100.0,
            "orderStatus": "PENDING"
        }
        headers = {"Content-Type": "application/json"}
        self.client.post("/order-service/api/orders",
                        data=json.dumps(payload),
                        headers=headers,
                        name="/order-service/api/orders")
        
    @task(1)
    def check_favourites(self):
        """
        Tarea: Ver lista de favoritos
        """
        self.client.get("/favourite-service/api/favourites",
                       name="/favourite-service/api/favourites")
        
    @task(1)
    def make_payment(self):
        """
        Tarea: Procesar pago
        """
        payload = {
            "paymentId": 999,
            "paymentMethod": "CREDIT_CARD",
            "paymentAmount": 100.0,
            "paymentStatus": "PENDING"
        }
        headers = {"Content-Type": "application/json"}
        self.client.post("/payment-service/api/payments",
                        data=json.dumps(payload),
                        headers=headers,
                        name="/payment-service/api/payments")

class ProductCatalogUser(HttpUser):
    """
    Usuario que solo navega el catálogo de productos
    Simula tráfico de solo lectura
    """
    wait_time = between(0.5, 2)
    host = "http://localhost:8080"
    weight = 2  # 2 usuarios de este tipo por cada EcommerceUser
    
    @task(5)
    def list_all_products(self):
        """Navegación frecuente de productos"""
        self.client.get("/app/api/products")
        
    @task(3)
    def get_product_categories(self):
        """Obtener categorías"""
        self.client.get("/app/api/categories",
                       name="/app/api/categories")
        
    @task(2)
    def search_products_by_category(self):
        """Buscar productos por categoría"""
        category_id = 1
        self.client.get(f"/app/api/products/category/{category_id}",
                       name="/app/api/products/category/[id]")

class OrderProcessingUser(HttpUser):
    """
    Usuario que procesa órdenes y pagos
    Simula flujo de compra completo
    """
    wait_time = between(2, 5)
    host = "http://localhost:8080"
    weight = 1
    
    @task
    def complete_purchase_flow(self):
        """
        Flujo completo de compra
        1. Ver productos
        2. Crear orden
        3. Procesar pago
        """
        # Ver productos
        self.client.get("/app/api/products")
        
        # Crear orden
        order_payload = {
            "orderId": 1000,
            "orderTrackingNumber": "FLOW-001",
            "orderTotal": 250.0,
            "orderStatus": "PENDING"
        }
        headers = {"Content-Type": "application/json"}
        
        self.client.post("/order-service/api/orders",
                        data=json.dumps(order_payload),
                        headers=headers,
                        name="/order-service/api/orders")
        
        # Procesar pago
        payment_payload = {
            "paymentId": 1000,
            "paymentMethod": "CREDIT_CARD",
            "paymentAmount": 250.0,
            "paymentStatus": "PENDING"
        }
        
        self.client.post("/payment-service/api/payments",
                        data=json.dumps(payment_payload),
                        headers=headers,
                        name="/payment-service/api/payments")

# Configuración de carga
class ApiLoadConfig:
    """
    Configuración de carga para pruebas de rendimiento
    """
    # Usuarios concurrentes por tipo
    ECOMMERCE_USERS = 50      # Usuarios normales
    PRODUCT_CATALOG_USERS = 30 # Solo navegación
    ORDER_PROCESSING_USERS = 10 # Procesamiento de órdenes
    
    # Tasa de spawn (usuarios por segundo)
    SPAWN_RATE = 5
    
    # Duración de la prueba (segundos)
    DURATION = 300  # 5 minutos
    
    # Puntos finales para monitorear
    ENDPOINTS = {
        "products": "/app/api/products",
        "users": "/app/api/users",
        "orders": "/order-service/api/orders",
        "payments": "/payment-service/api/payments",
        "favourites": "/favourite-service/api/favourites"
    }
    
    # Umbrales de rendimiento esperados
    PERFORMANCE_THRESHOLDS = {
        "p95_response_time": 500,  # 500ms
        "p99_response_time": 1000,  # 1s
        "error_rate": 0.01,  # 1%
        "rps": 100  # Requests por segundo
    }

