package com.sena.crud.data.repository

import com.sena.crud.data.mapper.toDomain
import com.sena.crud.data.mapper.toDto
import com.sena.crud.data.remote.api.ProductApiService
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * CAPA DE DATOS: IMPLEMENTACIÓN DEL REPOSITORIO
 * Aquí es donde "unimos" la API (Retrofit) con el Dominio.
 */
class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService
): ProductRepository {

    override suspend fun getProductById(id: Int): ProductModel {
        return api.getProductById(id).toDomain()
    }

    override suspend fun getAllProducts(): List<ProductModel> {
        // Obtenemos la respuesta y mapeamos la lista si existe, sino devolvemos lista vacía
        val response = api.getAllProducts()
        return response.products?.map { it.toDomain() } ?: emptyList()
    }

    override suspend fun addProduct(product: ProductModel): ProductModel {
        return api.addProduct(product.toDto()).toDomain()
    }

    override suspend fun updateProduct(id: Int, product: ProductModel): ProductModel {
        return api.updateProduct(id, product.toDto()).toDomain()
    }

    override suspend fun deleteProduct(id: Int): Boolean {
        return try {
            api.deleteProduct(id)
            true
        } catch (e: Exception) {
            false
        }
    }
}
