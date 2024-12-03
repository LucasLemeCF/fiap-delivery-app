package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.ProductDomain
import br.com.fiap.delivery.core.domain.exceptions.NotFoundException
import br.com.fiap.delivery.core.ports.outbound.ProductRepositoryPort
import br.com.fiap.delivery.infra.mappers.ProductMapper
import br.com.fiap.delivery.infra.outbound.repositories.ProductRepository
import org.springframework.stereotype.Component

@Component
class ProductRepositoryAdapter(
    val productRepository: ProductRepository
): ProductRepositoryPort {

    override fun searchProduct(productName: String): ProductDomain {
        return productRepository.findByName(productName)?.let {
            ProductMapper.toDomain(it)
        } ?: throw NotFoundException("product $productName not found in system!")
    }

    override fun create(productDomain: ProductDomain): ProductDomain {
        return ProductMapper.toDomain(
            productRepository.save(
                ProductMapper.toEntity(productDomain)
            )
        )
    }

    override fun searchAll(): List<ProductDomain> {
        return productRepository.findAll().map {
            ProductMapper.toDomain(it)
        }
    }

}