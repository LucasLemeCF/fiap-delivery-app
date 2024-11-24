package br.com.fiap.delivery.infra.repositories

import br.com.fiap.delivery.infra.entities.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<ProductEntity, Long> {

    fun findByName(productName: String): ProductEntity?

}