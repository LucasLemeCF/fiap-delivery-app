package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.ProductDomain
import br.com.fiap.delivery.core.ports.inbound.ProductPort
import br.com.fiap.delivery.core.ports.outbound.ProductRepositoryPort
import org.springframework.stereotype.Service

@Service
class ProductManager(
    private val productRepositoryPort: ProductRepositoryPort,
): ProductPort {

    override fun getProduct(productName: String): ProductDomain {
        return productRepositoryPort.findProduct(productName)
    }

}