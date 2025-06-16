package com.mensshop.mensshop.dto


data class UsuarioUpdateRequest (
    val endereco: EnderecoResponse? = null,
    val cartao: CartaoResponse? = null,
    val carrinho: List<ItemCarrinhoRequest>? = null,
)

data class ItemCarrinhoRequest(
    val produtoId: Long,
    val quantidade: Int,
    val tamanhoSelecionado: String
)