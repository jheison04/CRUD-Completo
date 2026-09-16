package com.sena.crud.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.domain.useCase.AddProductUseCase
import com.sena.crud.domain.useCase.DeleteProductUseCase
import com.sena.crud.domain.useCase.GetAllProductsUseCase
import com.sena.crud.domain.useCase.GetProductUseCase
import com.sena.crud.domain.useCase.UpdateProductUseCase
import com.sena.crud.ui.state.ProductUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel que conecta los casos de uso con la UI.
 */
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val addProductUseCase: AddProductUseCase,
    private val updateProductUseCase: UpdateProductUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()

    // --- Operaciones de Carga ---

    /**
     * Busca un producto por ID. 
     * Si se pasa un [id], se usa ese. Si no, usa el [searchId] del estado.
     */
    fun getProductById(id: Int? = null) {
        val finalId = id ?: _uiState.value.searchId.toIntOrNull() ?: return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = getProductUseCase(finalId)
                _uiState.update {
                    it.copy(isLoading = false, product = result, products = emptyList())
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val result = getAllProductsUseCase()
                _uiState.update {
                    it.copy(isLoading = false, products = result, product = null)
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    // --- Operaciones CRUD ---

    fun saveProduct() {
        val state = _uiState.value
        val productToSave = ProductModel(
            id = state.selectedProduct?.id ?: 0,
            title = state.formTitle,
            description = state.formDescription,
            category = state.formCategory,
            price = state.formPrice.toDoubleOrNull() ?: 0.0
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                if (state.selectedProduct == null) {
                    val newProduct = addProductUseCase(productToSave)
                    _uiState.update { it.copy(products = it.products + newProduct) }
                } else {
                    val updated = updateProductUseCase(state.selectedProduct.id, productToSave)
                    _uiState.update { s ->
                        s.copy(products = s.products.map { if (it.id == updated.id) updated else it })
                    }
                }
                closeSheet()
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error al guardar: ${e.message}") }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun deleteProduct(id: Int) {
        viewModelScope.launch {
            try {
                if (deleteProductUseCase(id)) {
                    _uiState.update { s ->
                        s.copy(
                            products = s.products.filter { it.id != id },
                            product = if (s.product?.id == id) null else s.product
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = "Error al borrar") }
            }
        }
    }

    // --- Gestión del Estado de UI ---

    fun onSearchIdChange(value: String) = _uiState.update { it.copy(searchId = value) }

    fun openSheet(product: ProductModel? = null) {
        _uiState.update {
            it.copy(
                isSheetOpen = true,
                selectedProduct = product,
                formTitle = product?.title ?: "",
                formDescription = product?.description ?: "",
                formCategory = product?.category ?: "",
                formPrice = product?.price?.toString() ?: ""
            )
        }
    }

    fun closeSheet() {
        _uiState.update { it.copy(isSheetOpen = false, selectedProduct = null) }
    }

    fun onTitleChange(value: String) = _uiState.update { it.copy(formTitle = value) }
    fun onDescriptionChange(value: String) = _uiState.update { it.copy(formDescription = value) }
    fun onCategoryChange(value: String) = _uiState.update { it.copy(formCategory = value) }
    fun onPriceChange(value: String) = _uiState.update { it.copy(formPrice = value) }
}
