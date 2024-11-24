package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.ProductDomain

interface ProductRepositoryPort {

    fun findProduct(productName: String): ProductDomain

}