package br.com.fiap.delivery.infra.repositories

import br.com.fiap.delivery.infra.entities.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<OrderEntity, Long> {
}