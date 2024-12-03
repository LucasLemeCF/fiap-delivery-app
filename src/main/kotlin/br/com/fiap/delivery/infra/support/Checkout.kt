package br.com.fiap.delivery.infra.support

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class CheckoutRequest(
    val items: List<ItemCheckoutRequest>,
)

data class ItemCheckoutRequest(
    val title: String,
    val quantity: Int = 1,
    @JsonProperty("currency_id") val currencyId: String = "BRL",
    @JsonProperty("unit_price") val unitPrice: BigDecimal,
)

data class CheckoutResponse(
    val message: String,
    @JsonProperty("external_reference") val externalReference: String,
    @JsonProperty("itens") val items: List<ItemCheckoutResponse>,
    @JsonProperty("link_pagamento") val paymentLink: String,
)

data class ItemCheckoutResponse(
    @JsonProperty("titulo") val title: String,
    @JsonProperty("preco") val price: BigDecimal,
    @JsonProperty("quantidade") val quantity: Int,
)