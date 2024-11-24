package br.com.fiap.delivery.infra.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany


@Entity(name = CategoryEntity.TABLE_NAME)
data class CategoryEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_CATEGORY")
    var id: Long?,

    @Column(name = "NAM_CATEGORY")
    val name: String,

    @OneToMany(mappedBy = TABLE_NAME, fetch = FetchType.LAZY)
    val products: List<ProductEntity>,

) {

    companion object {
        const val TABLE_NAME = "CATEGORIES"
    }

}