package br.com.fiap.delivery.adapters.inbound

import br.com.fiap.delivery.adapters.inbound.form.CategoryForm
import br.com.fiap.delivery.core.domain.CategoryDomain
import br.com.fiap.delivery.core.ports.inbound.CategoryPort
import br.com.fiap.delivery.infra.mappers.CategoryMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryResource(
    val categoryPort: CategoryPort,
) {

    @PostMapping
    fun create(
        @RequestBody categoryForm: CategoryForm
    ): ResponseEntity<CategoryDomain> {
        return ResponseEntity.ok(
            categoryPort.create(
                CategoryMapper.toDomain(categoryForm)
            )
        )
    }

    @GetMapping
    fun searchCategories(): ResponseEntity<List<CategoryDomain>> {
        return ResponseEntity.ok(
            categoryPort.searchAll()
        )
    }

}