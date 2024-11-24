package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.ProductDomain
import br.com.fiap.delivery.core.ports.inbound.CategoryPort
import br.com.fiap.delivery.core.ports.inbound.ProductPort
import br.com.fiap.delivery.core.ports.outbound.ProductRepositoryPort
import org.springframework.stereotype.Service

@Service
class ProductManager(
    private val productRepositoryPort: ProductRepositoryPort,
    private val categoryPort: CategoryPort
): ProductPort {

    override fun searchProduct(productName: String): ProductDomain {
        return productRepositoryPort.searchProduct(productName)
    }

    override fun create(productDomain: ProductDomain): ProductDomain {
        val category = categoryPort.search(productDomain.category.name)
        productDomain.updateCategory(category)

        return productRepositoryPort.create(productDomain)
    }

    override fun searchAll(): List<ProductDomain> {
        return productRepositoryPort.searchAll()
    }

}