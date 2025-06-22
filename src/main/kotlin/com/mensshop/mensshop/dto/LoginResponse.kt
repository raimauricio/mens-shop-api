package com.mensshop.mensshop.dto

data class LoginResponse(
    val token: String,
    val userResponse: UsuarioResponse
)

data class UsuarioResponse(
    val id: Long,
    val nome: String,
    val sobrenome: String,
    val email: String,
    val telefone: String,
    val enderecos: List<EnderecoResponse>,
    val cartoes: List<CartaoResponse>,
    val carrinho: List<CarrinhoResponse>
)

data class CarrinhoResponse(
    val produto: ProdutoResponse,
    val quantidade: Int,
    val tamanhoSelecionado: String
)
