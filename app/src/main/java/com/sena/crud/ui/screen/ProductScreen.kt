package com.sena.crud.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sena.crud.ui.component.ProductForm
import com.sena.crud.ui.section.ProductDetails
import com.sena.crud.ui.viewModel.ProductViewModel

/**
 * Pantalla principal que orquesta el CRUD de productos con un panel de control superior.
 */
@Composable
fun ProductScreen(
    productId: Int,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // Carga inicial del producto por defecto
    LaunchedEffect(productId) {
        viewModel.getProductById(productId)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // --- 1. BARRA DE BÚSQUEDA ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = uiState.searchId,
                    onValueChange = { viewModel.onSearchIdChange(it) },
                    label = { Text("Buscar por ID") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { viewModel.getProductById() }) {
                    Text("Buscar")
                }
            }

            // --- 2. PANEL DE CONTROL (Acciones Globales) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.getAllProducts() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Listar Todos")
                }
                
                Button(
                    onClick = { viewModel.openSheet() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Agregar Nuevo")
                }

                OutlinedButton(
                    onClick = { 
                        if (uiState.products.isNotEmpty()) viewModel.getAllProducts()
                        else viewModel.getProductById(productId)
                    }
                ) {
                    Text("Refrescar")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // --- 3. CONTENIDO PRINCIPAL (Lista o Ficha Detallada) ---
            Box(modifier = Modifier.weight(1f)) {
                ProductDetails(
                    uiState = uiState,
                    onRetry = { 
                        if (uiState.products.isNotEmpty()) viewModel.getAllProducts() 
                        else viewModel.getProductById() 
                    },
                    onDelete = { id -> viewModel.deleteProduct(id) },
                    onEdit = { product -> viewModel.openSheet(product) }
                )
            }
        }

        // Formulario (BottomSheet) para Crear y Editar
        ProductForm(
            state = uiState,
            onTitleChange = viewModel::onTitleChange,
            onDescriptionChange = viewModel::onDescriptionChange,
            onCategoryChange = viewModel::onCategoryChange,
            onPriceChange = viewModel::onPriceChange,
            onSave = viewModel::saveProduct,
            onDismiss = viewModel::closeSheet
        )
    }
}
