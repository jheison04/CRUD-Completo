package com.sena.crud.data.remote.api

import com.sena.crud.data.remote.dto.req.product.Product
import com.sena.crud.data.remote.dto.req.product.ProductListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * CAPA DE DATOS: API SERVICE (Retrofit)
 * Esta interfaz define cómo nos comunicamos con el servidor externo (DummyJSON).
 * Cada función representa una operación HTTP (GET, POST, PUT, DELETE).
 */
interface ProductApiService {

    /**
     * READ (Leer): Obtiene un producto por su ID único.
     * Endpoint: GET https://dummyjson.com/products/{id}
     */
    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int
    ): Product

    /**
     * READ (Leer): Obtiene todos los productos.
     * Retorna un objeto [ProductListResponse] que contiene la lista de productos.
     */
    @GET("products")
    suspend fun getAllProducts(): ProductListResponse

    /**
     * CREATE (Crear): Envía un nuevo producto al servidor.
     * @param product El objeto [Product] con los datos a crear.
     * Nota: DummyJSON simula la creación y devuelve el objeto creado con un nuevo ID.
     */
    @POST("products/add")
    suspend fun addProduct(
        @Body product: Product
    ): Product

    /**
     * UPDATE (Actualizar): Modifica los datos de un producto existente.
     * @param id ID del producto a modificar.
     * @param product Datos actualizados.
     */
    @PUT("products/{id}")
    suspend fun updateProduct(
        @Path("id") id: Int,
        @Body product: Product
    ): Product

    /**
     * DELETE (Borrar): Elimina un producto del servidor.
     * @param id ID del producto a eliminar.
     */
    @DELETE("products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: Int
    ): Product
}
