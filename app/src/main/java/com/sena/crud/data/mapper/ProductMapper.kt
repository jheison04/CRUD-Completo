package com.sena.crud.data.mapper

import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.domain.model.ProductModel

/**
 * Mapeador que convierte los datos crudos de la API (DTO) a nuestro modelo de Dominio.
 * Proporciona valores por defecto en caso de que algún campo venga nulo desde el servidor.
 */
fun Product.toDomain(): ProductModel {
    return ProductModel(
        id = id ?: 0,
        title = title ?: "Sin título",
        description = description ?: "Sin descripción",
        category = category ?: "Sin categoría",
        price = price ?: 0.0
    )
}

/**
 * Convierte un modelo de dominio a DTO para enviar a la API.
 */
fun ProductModel.toDto(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        category = category,
        price = price,
        availabilityStatus = "In Stock",
        brand = "Generic",
        stock = 10
    )
}
