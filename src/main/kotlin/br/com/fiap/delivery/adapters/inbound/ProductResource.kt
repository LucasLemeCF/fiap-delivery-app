package br.com.fiap.delivery.adapters.inbound

import br.com.fiap.delivery.adapters.inbound.form.ProductForm
import br.com.fiap.delivery.core.domain.ProductDomain
import br.com.fiap.delivery.core.ports.inbound.ProductPort
import br.com.fiap.delivery.infra.mappers.ProductMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductResource(
    val productPort: ProductPort,
) {

    @PostMapping
    fun createProduct(
        @RequestBody productForm: ProductForm,
    ): ResponseEntity<ProductDomain> {
        return ResponseEntity.ok(
            productPort.create(
                ProductMapper.toDomain(productForm)
            )
        )
    }

    @GetMapping
    fun searchProducts(): ResponseEntity<List<ProductDomain>> {
        return ResponseEntity.ok(
            productPort.searchAll()
        )
    }

}