package br.com.fiap.delivery.infra.mappers

import br.com.fiap.delivery.infra.inbound.form.CategoryForm
import br.com.fiap.delivery.core.domain.CategoryDomain
import br.com.fiap.delivery.infra.entities.CategoryEntity

object CategoryMapper {

    fun toDomain(entity: CategoryEntity): CategoryDomain {
        return CategoryDomain(
            id = entity.id,
            name = entity.name,
        )
    }

    fun toEntity(domain: CategoryDomain): CategoryEntity {
        return CategoryEntity(
            id = domain.id,
            name = domain.name,
            products = domain._products.map { ProductMapper.toEntity(it) }
        )
    }

    fun toDomain(form: CategoryForm): CategoryDomain {
        return CategoryDomain(
            id = null,
            name = form.name
        )
    }

}