package br.com.fiap.delivery.infra.mappers

import br.com.fiap.delivery.core.domain.OrderProductDomain
import br.com.fiap.delivery.infra.entities.OrderProductEntity

object OrderProductMapper {

    fun toDomain(entity: OrderProductEntity): OrderProductDomain {
        return OrderProductDomain(
            id = entity.id,
            order = OrderMapper.toDomain(entity.order),
            product = ProductMapper.toDomain(entity.product),
        )
    }

    fun toEntity(domain: OrderProductDomain): OrderProductEntity {
        return OrderProductEntity(
            id = domain.id,
            order = OrderMapper.toEntity(domain.order),
            product = ProductMapper.toEntity(domain.product),
        )
    }

}