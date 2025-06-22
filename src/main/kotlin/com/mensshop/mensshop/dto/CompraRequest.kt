package com.mensshop.mensshop.dto

import com.mensshop.mensshop.model.Endereco
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
    val endereco: EnderecoResponse? = null,
    val retiradaLoja: Int? = null,
)

data class PagamentoRequest(
    val tipo: String,
    val valorTotal: BigDecimal,
    val cartao: CartaoResponse? = null
)
