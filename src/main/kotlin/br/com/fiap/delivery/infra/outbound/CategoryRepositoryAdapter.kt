package br.com.fiap.delivery.infra.outbound

import br.com.fiap.delivery.core.domain.CategoryDomain
import br.com.fiap.delivery.core.domain.exceptions.NotFoundException
import br.com.fiap.delivery.core.ports.outbound.CategoryRepositoryPort
import br.com.fiap.delivery.infra.mappers.CategoryMapper
import br.com.fiap.delivery.infra.outbound.repositories.CategoryRepository
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryAdapter(
    val categoryRepository: CategoryRepository
): CategoryRepositoryPort {

    override fun create(categoryDomain: CategoryDomain): CategoryDomain {
        return CategoryMapper.toDomain(
            categoryRepository.save(
                CategoryMapper.toEntity(categoryDomain)
            )
        )
    }

    override fun search(category: String): CategoryDomain {
        return categoryRepository.findByName(category).map {
            CategoryMapper.toDomain(it)
        }.orElseThrow { throw NotFoundException("category=$category not exists in system!") }
    }

    override fun searchAll(): List<CategoryDomain> {
        return categoryRepository.findAll().map {
            CategoryMapper.toDomain(it)
        }
    }

}