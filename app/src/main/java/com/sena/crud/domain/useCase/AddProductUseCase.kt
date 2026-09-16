package com.sena.crud.domain.useCase

import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * CAPA DE DOMINIO: CASO DE USO (Add Product)
 * Un Caso de Uso representa una única acción que el usuario puede realizar.
 * 
 * ¿Cómo se une?
 * El ViewModel llama a este Caso de Uso, y el Caso de Uso le pide al Repositorio 
 * que realice la acción. Esto mantiene el ViewModel limpio de lógica compleja.
 */
class AddProductUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    /**
     * El operador 'invoke' permite llamar a esta clase como si fuera una función:
     * addProductUseCase(miProducto)
     */
    suspend operator fun invoke(product: ProductModel): ProductModel {
        return repository.addProduct(product)
    }
}
