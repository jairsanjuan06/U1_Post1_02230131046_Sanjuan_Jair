# U1_Post1_02230131046_Sanjuan_Jair

### Paso 3 - Identificar violaciones SOLID

### 1. Violación del Principio de Responsabilidad Única (SRP)
**Problema:** La clase actúa como un "Objeto Dios", teniendo múltiples razones para cambiar. 
**Análisis de responsabilidades:** Actualmente, `OrderManager` se encarga de:
* Contener la lógica de negocio (cálculo de totales, descuentos e impuestos).
* Manejar la persistencia de datos (guardar en un archivo de texto).
* Gestionar la infraestructura de notificaciones (simulación de envío de correos).
* Manejar la presentación de datos (formateo del reporte en texto).
**Impacto:** Cualquier cambio en la forma de guardar datos, enviar correos o calcular descuentos obligará a modificar esta misma clase.

### 2. Violación del Principio de Abierto/Cerrado (OCP)
**Problema:** La clase no está abierta a la extensión ni cerrada a la modificación.
**Análisis de extensibilidad:** Agregar nueva funcionalidad es propenso a errores. Las reglas de negocio (descuento del 10% si el total es > 1000, 15% si es > 5000) y la tasa de impuestos (19%) están "quemadas" (*hardcoded*) mediante sentencias `if`. 
**Impacto:** Si se requiere agregar un nuevo nivel de descuento por temporada navideña o cambiar el IVA por regulaciones gubernamentales, es obligatorio modificar el código interno del método `createOrder` y `calculateTax`, violando el OCP.

### 3. Violación del Principio de Inversión de Dependencias (DIP)
**Problema:** El módulo de alto nivel (la gestión de órdenes) depende directamente de módulos de bajo nivel (detalles de infraestructura).
**Análisis de dependencias:** La clase depende directamente de la implementación concreta `java.io.FileWriter`. No existe una abstracción.
**Impacto:** Es imposible probar la lógica de negocio de forma aislada (pruebas unitarias) sin escribir físicamente en el disco. Para solucionarlo, se debería depender de una interfaz (ej. `OrderRepository`) inyectada a través del constructor.

### 4. Violación combinada de SRP y OCP en la Presentación (Reportes)
**Problema:** El método `generateReport` mezcla la iteración de los datos con el formateo específico de la presentación.
**Análisis:** La clase asume cómo debe visualizarse el reporte (usando `StringBuilder` con un formato específico `=== REPORTE...`).
**Impacto:** Si mañana el requerimiento cambia y se necesita generar el reporte en formato JSON, XML o enviarlo a una API REST, no será posible extender el comportamiento sin modificar e inflar aún más la clase `OrderManager`. La generación del reporte debería ser delegada a una clase o servicio externo dedicado a la presentación.