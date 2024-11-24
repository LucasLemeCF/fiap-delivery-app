package br.com.fiap.delivery.core.ports.inbound

import br.com.fiap.delivery.core.domain.CategoryDomain

interface CategoryPort {

    fun create(categoryDomain: CategoryDomain): CategoryDomain
    fun search(category: String): CategoryDomain
    fun searchAll(): List<CategoryDomain>

}