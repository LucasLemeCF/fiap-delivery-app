package br.com.fiap.delivery.infra.inbound

import br.com.fiap.delivery.core.domain.CompleteOrderDomain
import br.com.fiap.delivery.core.domain.OrderDomain
import br.com.fiap.delivery.core.domain.OrderFlatDomain
import br.com.fiap.delivery.core.ports.inbound.OrderPort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderResource(
    val orderPort: OrderPort,
) {

    @PostMapping
    fun createOrder(
        @RequestBody orderFlatDomain: OrderFlatDomain,
    ): ResponseEntity<OrderDomain> {
        return ResponseEntity.ok(
            orderPort.createOrder(orderFlatDomain)
        )
    }

    @GetMapping("/{id}")
    fun getOrderById(
        @PathVariable id: Long
    ): ResponseEntity<CompleteOrderDomain> {
        return ResponseEntity.ok(
            orderPort.getOrderBy(id)
        );
    }

}