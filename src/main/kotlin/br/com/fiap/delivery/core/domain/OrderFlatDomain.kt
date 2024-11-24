package br.com.fiap.delivery.core.domain

data class OrderFlatDomain(
    val customer: String,
    val products: List<ProductFlatDomain>,
)

data class ProductFlatDomain(
    val productName: String,
    val quantity: Long,
)