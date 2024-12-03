package br.com.fiap.delivery.core.usecases

import br.com.fiap.delivery.core.domain.*
import br.com.fiap.delivery.core.domain.enums.OrderStatus
import br.com.fiap.delivery.core.domain.exceptions.InvalidException
import br.com.fiap.delivery.core.ports.inbound.OrderPort
import br.com.fiap.delivery.core.ports.inbound.ProductPort
import br.com.fiap.delivery.core.ports.outbound.CheckoutPort
import br.com.fiap.delivery.core.ports.outbound.CustomerPort
import br.com.fiap.delivery.core.ports.outbound.OrderProductRepositoryPort
import br.com.fiap.delivery.core.ports.outbound.OrderRepositoryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class OrderManager(
    val customerPort: CustomerPort,
    val productPort: ProductPort,
    val orderRepositoryPort: OrderRepositoryPort,
    val orderProductRepositoryPort: OrderProductRepositoryPort,
    val checkoutPort: CheckoutPort,
): OrderPort {

    @Transactional
    override fun createOrder(orderFlatDomain: OrderFlatDomain): OrderDomain {
        val customer = orderFlatDomain.customer?.let {
            customerPort.check(it)
        }

        val products = checkAndGetProducts(orderFlatDomain.products)

        val orderDomain = orderRepositoryPort.createOrder(
            OrderDomain(
                id = null,
                customer = customer?.cpf ?: UUID.randomUUID().toString(),
            )
        )

        calculateAndSaveItems(orderFlatDomain, products, orderDomain)

        val savedOrder = orderRepositoryPort.createOrder(orderDomain)

        val paymentCode = checkoutPort.checkout(savedOrder.price)
        savedOrder.insertPaymentCode(paymentCode)

        return orderRepositoryPort.update(savedOrder)
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

    override fun searchOrder(id: Long): OrderDomain {
        return orderRepositoryPort.searchBy(id)
    }

    override fun searchOrderBy(paymentCode: String): OrderDomain {
        return orderRepositoryPort.searchBy(paymentCode)
    }

    override fun updateOrder(orderDomain: OrderDomain): OrderDomain {
        return orderRepositoryPort.update(orderDomain)
    }

    override fun finalizeOrder(id: Long): OrderDomain {
        val orderDomain = orderRepositoryPort.searchBy(id)

        if(orderDomain.status.canFinalize().not()) {
            throw InvalidException("Can not finalize the order. Actual status=${orderDomain.status.name}")
        }

        orderDomain.updateStatus(OrderStatus.FINISHED)

        return orderRepositoryPort.update(orderDomain)
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