package br.com.fiap.delivery.core.ports.inbound

import br.com.fiap.delivery.core.domain.ProductDomain

interface ProductPort {

    fun searchProduct(productName: String): ProductDomain
    fun create(productDomain: ProductDomain): ProductDomain
    fun searchAll(): List<ProductDomain>

}