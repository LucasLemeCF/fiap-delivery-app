package br.com.fiap.delivery.core.ports.outbound

import br.com.fiap.delivery.core.domain.CategoryDomain

interface CategoryRepositoryPort {

    fun create(categoryDomain: CategoryDomain): CategoryDomain
    fun search(category: String): CategoryDomain
    fun searchAll(): List<CategoryDomain>

}