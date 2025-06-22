package com.mensshop.mensshop.dto

import java.math.BigDecimal

data class CompraRequest(
    val produtos: MutableList<ProdutoCompradoRequest>,
    val recebimento: RecebimentoRequest,
    val pagamento: PagamentoRequest,
)

data class ProdutoCompradoRequest(
    val produtoId: Long,
    val quantidade: Int,
    val tamanhoSelecionado: String,
)

data class RecebimentoRequest(
    val tipo: String,
    val enderecoId: Long? = null,
    val novoEndereco: EnderecoResponse? = null,
    val retiradaLoja: Int? = null,
)

data class PagamentoRequest(
    val tipo: String,
    val valorTotal: BigDecimal,
    val cartaoId: Long? = null,
    val novoCartao: CartaoResponse? = null
)
