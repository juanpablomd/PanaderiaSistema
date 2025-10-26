# 🥖 Sistema de Gestión para Panadería

**Proyecto académico desarrollado en Java (Apache NetBeans)** orientado a la gestión integral de una panadería.  
Incluye control de productos, ventas, empleados, proveedores y categorías, con manejo de roles, auditoría, facturación en PDF y conexión a APIs externas.  

---

## 📋 Características principales

### 🧁 Módulo de Productos
- Alta, baja y modificación de productos.  
- Control de stock mediante **código de barras (Barcode)**.  
- Generación automática de códigos.  
- Teclas rápidas para mejorar la productividad (F1, F2, etc.).  

### 🧾 Módulo de Ventas
- Registro de ventas con detalle de productos.  
- Generación de **tickets en formato PDF**.  
- Control automático de stock al realizar una venta.  
- Visualización de reportes y gráficos.  

### 👥 Módulo de Usuarios
- Sistema de roles:
  - **Administrador:** acceso completo, puede agregar empleados, marcar sueldos pagados y acceder a auditorías.  
  - **Empleado:** acceso limitado a funciones de venta y consulta.  
- Gestión de empleados y proveedores.  

### 📅 Módulo de Categorías
- Creación y administración de categorías para organizar los productos.  

### 🖥️ Módulo del Sistema
- **Auditoría completa** de inicio de sesión y acciones del usuario.  
- Acceso a manual de uso integrado.  
- Integración con APIs:
  - **Clima actual en Córdoba.**
  - **Feriados nacionales en Argentina (API pública).**

---

## ⚙️ Tecnologías utilizadas

| Tipo | Tecnología |
|------|-------------|
| Lenguaje principal | Java |
| Entorno de desarrollo | Apache NetBeans |
| Base de datos | MySQL / SQLite (según versión del proyecto) |
| Librerías externas | Barcode, iTextPDF |
| APIs consumidas | API de clima / API de feriados nacionales |
| Patrón utilizado | DAO (Data Access Object) |
| Interfaz | Java Swing |
| Control de versiones | Git / GitHub |

---

## 🧠 Conceptos aplicados
- CRUD completo en múltiples módulos.  
- Manejo de sesiones y roles.  
- Auditoría de eventos.  
- Control de stock con código de barras.  
- Integración con servicios externos (APIs REST).  
- Generación de reportes PDF.  
- Separación por capas (DAO, lógica, interfaz).  

---

## 🚀 Cómo ejecutar el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuusuario/sistema-panaderia.git
Abrir el proyecto con Apache NetBeans.
Configurar la conexión a la base de datos (archivo Conexion.java).
Ejecutar el proyecto desde NetBeans.

<img width="1778" height="873" alt="Captura de pantalla 2025-08-12 113721" src="https://github.com/user-attachments/assets/ff8bfa2b-d1e0-4aa6-8e99-c33e67c45036" />
