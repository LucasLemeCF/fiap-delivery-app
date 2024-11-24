package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.ProductDomain

interface ProductRepositoryPort {

    fun searchProduct(productName: String): ProductDomain
    fun create(productDomain: ProductDomain): ProductDomain
    fun searchAll(): List<ProductDomain>

}