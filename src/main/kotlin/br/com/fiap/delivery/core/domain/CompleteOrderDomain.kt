package br.com.fiap.delivery.core.domain

data class CompleteOrderDomain(
    val order: OrderDomain,
    val products: List<ProductDomain>
)
