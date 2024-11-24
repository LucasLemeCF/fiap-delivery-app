package br.com.fiap.delivery.infra.mappers

import br.com.fiap.delivery.adapters.inbound.form.ProductForm
import br.com.fiap.delivery.core.domain.ProductDomain
import br.com.fiap.delivery.infra.entities.ProductEntity

object ProductMapper {

    fun toDomain(entity: ProductEntity): ProductDomain {
        return ProductDomain(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            value = entity.value,
            category = CategoryMapper.toDomain(entity.category),
            isActive = entity.isActive,
        )
    }

    fun toEntity(domain: ProductDomain): ProductEntity {
        return ProductEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            value = domain.value,
            category = CategoryMapper.toEntity(domain.category),
            isActive = domain.isActive,
        )
    }

    fun toDomain(form: ProductForm): ProductDomain {
        return ProductDomain(
            id = null,
            name = form.name,
            description = form.description,
            value = form.value,
            category = CategoryMapper.toDomain(form.category),
        )
    }

}