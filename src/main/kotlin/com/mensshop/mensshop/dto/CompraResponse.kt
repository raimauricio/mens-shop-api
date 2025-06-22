package com.mensshop.mensshop.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CompraResponse(
    val id: Long,
    val dataCompra: LocalDateTime,
    val valorTotal: BigDecimal,
    val statusAtual: String,
    val tipoRecebimento: String,
    val produtos: List<ProdutosPedidoResponse>,
    val etapas: List<Etapas>?,
)

data class ProdutosPedidoResponse(
    val nome: String,
    val quantidade: Int,
    val tamanho: String,
    val preco: Double
)

data class Etapas(
    val status: String,
    val data: LocalDateTime,
    val icon: String? = null
)