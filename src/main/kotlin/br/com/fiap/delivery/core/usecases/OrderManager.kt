package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.*
import br.com.fiap.delivery.core.domain.enums.OrderStatus
import br.com.fiap.delivery.core.ports.inbound.OrderPort
import br.com.fiap.delivery.core.ports.inbound.ProductPort
import br.com.fiap.delivery.core.ports.outbound.CheckoutPort
import br.com.fiap.delivery.core.ports.outbound.CustomerPort
import br.com.fiap.delivery.core.ports.outbound.OrderProductRepositoryPort
import br.com.fiap.delivery.core.ports.outbound.OrderRepositoryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class OrderManager(
    val checkoutPort: CheckoutPort,
    val customerPort: CustomerPort,
    val productPort: ProductPort,
    val orderRepositoryPort: OrderRepositoryPort,
    val orderProductRepositoryPort: OrderProductRepositoryPort,
): OrderPort {

    @Transactional
    override fun createOrder(orderFlatDomain: OrderFlatDomain): OrderDomain {
        val customer = orderFlatDomain.customer
        customerPort.check(customer)

        val products = checkAndGetProducts(orderFlatDomain.products)

        val orderDomain = orderRepositoryPort.createOrder(
            OrderDomain(
                id = null,
                customer = customer,
            )
        )

        calculateAndSaveItems(orderFlatDomain, products, orderDomain)

        orderDomain.updateStatus(OrderStatus.WAITING_PAYMENT)
        return orderRepositoryPort.createOrder(orderDomain)
    }

    override fun getOrderBy(id: Long): CompleteOrderDomain {
        val order = orderRepositoryPort.searchBy(id)
        val products = orderProductRepositoryPort.findAllBy(order).map {
            it.product
        }

        return CompleteOrderDomain(
            order = order,
            products = products
        )
    }

    private fun calculateAndSaveItems(
        orderFlatDomain: OrderFlatDomain,
        products: List<ProductDomain>,
        orderDomain: OrderDomain
    ) {
        val items: MutableList<OrderProductDomain> = mutableListOf()
        var totalValue: BigDecimal = BigDecimal.ZERO

        orderFlatDomain.products.forEach { product ->
            val productDomain = products.find { it.name == product.productName }

            for (i in 1..product.quantity) {
                val orderProductDomain = OrderProductDomain(
                    id = null,
                    order = orderDomain,
                    product = productDomain!!
                )

                items.add(orderProductDomain)
                totalValue += productDomain.value
            }
        }

        orderProductRepositoryPort.createAll(items)
        orderDomain.updatePrice(totalValue)
    }

    fun checkAndGetProducts(products: List<ProductFlatDomain>): List<ProductDomain> {
        val uniqueProducts = products.distinctBy { it.productName }.toSet()
        return uniqueProducts.map { productPort.searchProduct(it.productName) }
    }

}