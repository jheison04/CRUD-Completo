package com.sena.crud.ui.state

import com.sena.crud.domain.model.ProductModel

/**
 * Estado de la UI para la gestión de productos.
 */
data class ProductUIState(
    val isLoading: Boolean = false,
    val product: ProductModel? = null,
    val products: List<ProductModel> = emptyList(),
    val errorMessage: String? = null,
    
    // Campo para la búsqueda por ID
    val searchId: String = "",
    
    // Estado para el formulario (Crear/Editar)
    val isSheetOpen: Boolean = false,
    val selectedProduct: ProductModel? = null,
    val formTitle: String = "",
    val formDescription: String = "",
    val formCategory: String = "",
    val formPrice: String = ""
)
