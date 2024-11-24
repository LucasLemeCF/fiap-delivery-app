package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.CategoryDomain
import br.com.fiap.delivery.core.ports.inbound.CategoryPort
import br.com.fiap.delivery.core.ports.outbound.CategoryRepositoryPort
import org.springframework.stereotype.Service

@Service
class CategoryManager(
    val categoryRepositoryPort: CategoryRepositoryPort
): CategoryPort {
    override fun create(categoryDomain: CategoryDomain): CategoryDomain {
        return categoryRepositoryPort.create(categoryDomain)
    }

    override fun search(category: String): CategoryDomain {
        return categoryRepositoryPort.search(category)
    }

    override fun searchAll(): List<CategoryDomain> {
        return categoryRepositoryPort.searchAll()
    }
}