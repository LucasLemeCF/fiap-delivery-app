package br.com.fiap.delivery.core.domain

data class CategoryDomain(

    var id: Long?,
    val name: String,

) {

    val _products: List<ProductDomain>  = emptyList()

}