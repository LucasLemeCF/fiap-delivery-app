package br.com.fiap.delivery.infra.mappers

import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.infra.entities.OrderEntity

object OrderMapper {

    fun toEntity(domain: OrderDomain): OrderEntity {
        return OrderEntity(
            id = domain.id,
            customer = domain.customer,
            creationAt = domain.creationAt,
            value = domain.price,
            status = domain.status,
        )
    }

    fun toDomain(entity: OrderEntity): OrderDomain {
        return OrderDomain(
            id = entity.id,
            customer = entity.customer,
            creationAt = entity.creationAt,
            price = entity.value,
            status = entity.status,
        )
    }

}