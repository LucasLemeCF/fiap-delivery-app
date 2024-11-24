package br.com.fiap.delivery.core.domain

data class OrderProductDomain(

    var id: Long?,
    val order: OrderDomain,
    val product: ProductDomain,

)