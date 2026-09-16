package com.sena.crud.data.remote.dto.req.product

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Respuesta envoltorio para la lista de productos de la API DummyJSON.
 */
@JsonClass(generateAdapter = true)
data class ProductListResponse(
    @param:Json(name = "products")
    val products: List<Product>? = null,
    
    @param:Json(name = "total")
    val total: Int? = null,
    
    @param:Json(name = "skip")
    val skip: Int? = null,
    
    @param:Json(name = "limit")
    val limit: Int? = null
)
