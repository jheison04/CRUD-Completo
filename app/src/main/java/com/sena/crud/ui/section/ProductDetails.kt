package com.sena.crud.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sena.crud.domain.model.ProductModel
import com.sena.crud.ui.component.ProductCard
import com.sena.crud.ui.component.ProductItem
import com.sena.crud.ui.state.ProductUIState

/**
 * Sección encargada de mostrar el estado de los datos (Cargando, Error, Lista o Detalle).
 */
@Composable
fun ProductDetails(
    uiState: ProductUIState,
    onRetry: () -> Unit,
    onDelete: (Int) -> Unit,
    onEdit: (ProductModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (uiState.products.isNotEmpty()) Arrangement.Top else Arrangement.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    modifier = Modifier.padding(bottom = 12.dp),
                    color = MaterialTheme.colorScheme.error
                )
                Button(onClick = onRetry) {
                    Text(text = "Reintentar")
                }
            }
            uiState.products.isNotEmpty() -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.products) { product ->
                        ProductItem(
                            product = product,
                            onDelete = onDelete,
                            onEdit = onEdit
                        )
                    }
                }
            }
            uiState.product != null -> {
                ProductCard(
                    product = uiState.product,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        }
    }
}
