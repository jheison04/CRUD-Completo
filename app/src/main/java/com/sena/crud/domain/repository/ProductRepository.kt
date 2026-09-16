package com.sena.crud.domain.repository

import com.sena.crud.domain.model.ProductModel

/**
 * CAPA DE DOMINIO: CONTRATO DEL REPOSITORIO
 * Esta interfaz define QUÉ acciones podemos hacer con los productos, 
 * pero no explica CÓMO se hacen (eso lo decide la capa de Data).
 * Esto permite que la lógica de negocio (UseCases) no dependa de si los datos 
 * vienen de Internet, de una base de datos local o de memoria.
 */
interface ProductRepository {
    
    /** Obtiene un producto individual */
    suspend fun getProductById(id: Int): ProductModel
    
    /** Obtiene la lista de todos los productos */
    suspend fun getAllProducts(): List<ProductModel>
    
    /** Agrega un nuevo producto y devuelve el resultado */
    suspend fun addProduct(product: ProductModel): ProductModel
    
    /** Actualiza un producto y devuelve el resultado actualizado */
    suspend fun updateProduct(id: Int, product: ProductModel): ProductModel
    
    /** Borra un producto y devuelve true si se borró correctamente */
    suspend fun deleteProduct(id: Int): Boolean
}
