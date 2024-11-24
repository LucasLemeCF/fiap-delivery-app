package br.com.fiap.delivery.infra.repositories

import br.com.fiap.delivery.infra.entities.OrderProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderProductRepository: JpaRepository<OrderProductEntity, Long> {
}