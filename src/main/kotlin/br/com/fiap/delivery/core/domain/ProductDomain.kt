package br.com.fiap.delivery.core.domain

import java.math.BigDecimal

data class ProductDomain(

    var id: Long?,
    val name: String,
    val description: String,
    val value: BigDecimal = BigDecimal.ZERO,
    var category: CategoryDomain,
    val isActive: Boolean = true,

) {
    fun updateCategory(category: CategoryDomain) {
        this.category = category
    }
}
