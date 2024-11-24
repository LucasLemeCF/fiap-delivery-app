package br.com.fiap.delivery.infra.repositories

import br.com.fiap.delivery.infra.entities.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoryRepository: JpaRepository<CategoryEntity, Long> {

    fun findByName(name: String): Optional<CategoryEntity>

}