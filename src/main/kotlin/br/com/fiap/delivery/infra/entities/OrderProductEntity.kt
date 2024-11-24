package br.com.fiap.delivery.infra.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = OrderProductEntity.TABLE_NAME)
data class OrderProductEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_ORDER_PRODUCT")
    var id: Long?,

    @ManyToOne
    @JoinColumn(name = "IDT_ORDER")
    val order: OrderEntity,

    @ManyToOne
    @JoinColumn(name = "IDT_PRODUCT")
    val product: ProductEntity,

) {

    companion object {
        const val TABLE_NAME = "ORDER_PRODUCTS"
    }

}
