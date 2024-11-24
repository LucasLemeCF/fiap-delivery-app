package br.com.fiap.delivery.infra.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Entity(name = ProductEntity.TABLE_NAME)
data class ProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_PRODUCT")
    var id: Long?,

    @Column(name = "NAM_PRODUCT")
    val name: String,

    @Column(name = "DES_PRODUCT")
    val description: String,

    @Column(name = "NUM_PRICE_PRODUCT")
    val value: BigDecimal = BigDecimal.ZERO,

    @ManyToOne
    @JoinColumn(name = "IDT_CATEGORY")
    val category: CategoryEntity,

    @Column(name = "FLG_ACTIVE")
    val isActive: Boolean = true,

) {

    companion object {
        const val TABLE_NAME = "PRODUCTS"
    }

}