# Plan de Reorganización: Panel de Control CRUD

Este plan reorganizará la interfaz para que todas las funciones principales (Editar, Eliminar, Actualizar/Refrescar, Agregar y Listar) estén agrupadas y accesibles mediante botones de texto claros, eliminando la dispersión de los botones flotantes.

## User Review Required

> [!IMPORTANT]
> - Moveremos los botones de "Nuevo" y "Listar Todo" desde los botones flotantes (FAB) a una sección fija en la parte superior.
> - Renombraremos "Actualizar" a "Editar" y "Borrar" a "Eliminar" para usar el lenguaje exacto solicitado.

## Cambios Propuestos

### 1. Interfaz de Usuario (UI) - Estructura Principal

#### [MODIFY] [ProductScreen.kt](file:///C:/Users/oscar/OneDrive/Desktop/CRUD_ANDROID/app/src/main/java/com/sena/crud/ui/screen/ProductScreen.kt)
- Eliminar el `floatingActionButton` del `Scaffold`.
- Crear un **Panel de Acciones** debajo de la barra de búsqueda que contenga:
    - Botón **"Listar Todos los ID"**.
    - Botón **"Nuevo Producto"**.
    - Botón **"Refrescar Pantalla"**.
- Asegurar que este panel esté siempre visible mientras se navega por la lista.

### 2. Componentes de Item y Tarjeta

#### [MODIFY] [ProductItem.kt](file:///C:/Users/oscar/OneDrive/Desktop/CRUD_ANDROID/app/src/main/java/com/sena/crud/ui/component/ProductItem.kt)
- Cambiar el texto "Borrar" por **"Eliminar"**.
- Cambiar el texto "Actualizar" por **"Editar"**.

#### [MODIFY] [ProductCard.kt](file:///C:/Users/oscar/OneDrive/Desktop/CRUD_ANDROID/app/src/main/java/com/sena/crud/ui/component/ProductCard.kt)
- Cambiar el texto "Borrar" por **"Eliminar"**.
- Cambiar el texto "Actualizar" por **"Editar"**.

## Guía de Organización
La nueva estructura será:
1.  **Buscador**: Campo de texto para ID + Botón Buscar.
2.  **Panel de Control**: Fila de botones con las acciones globales (Listar, Nuevo, Refrescar).
3.  **Visor de Contenido**: Lista de productos o Ficha de detalle, cada uno con sus botones de Editar y Eliminar.

## Plan de Verificación

### Verificación Manual
1.  **Visibilidad:** Confirmar que todos los botones de acción global aparecen en la parte superior sin necesidad de iconos flotantes.
2.  **Consistencia:** Verificar que las etiquetas digan "Editar" y "Eliminar" en todos los componentes.
3.  **Flujo:** Comprobar que "Listar Todos" carga la lista e "ID: X" es visible en cada fila.
