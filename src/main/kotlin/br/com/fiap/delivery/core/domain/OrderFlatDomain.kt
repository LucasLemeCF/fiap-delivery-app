package br.com.fiap.delivery.core.domain

data class OrderFlatDomain(
    val customer: CustomerDomain?,
    val products: List<ProductFlatDomain>,
)

data class ProductFlatDomain(
    val productName: String,
    val quantity: Long,
)

data class CustomerDomain(
    val name: String?,
    val cpf: String,
    val email: String?,
)