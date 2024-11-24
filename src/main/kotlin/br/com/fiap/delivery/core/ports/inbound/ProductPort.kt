package br.com.fiap.delivery.core.ports.inbound

import br.com.fiap.delivery.core.domain.ProductDomain

interface ProductPort {

    fun getProduct(productName: String): ProductDomain

}