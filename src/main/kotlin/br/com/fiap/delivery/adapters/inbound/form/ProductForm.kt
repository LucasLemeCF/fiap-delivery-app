package br.com.fiap.delivery.adapters.inbound.form

import java.math.BigDecimal

data class ProductForm(
    val name: String,
    val description: String,
    val value: BigDecimal = BigDecimal.ZERO,
    val category: CategoryForm,
)
