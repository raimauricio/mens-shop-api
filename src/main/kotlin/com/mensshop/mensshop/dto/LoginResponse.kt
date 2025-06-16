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

data class EnderecoResponse(
    val id: Long,
    val logradouro: String,
    val numero: Int,
    val cep: String,
    val complemento: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)

data class CartaoResponse(
    val id: Long,
    val numero: String,
    val nome: String,
    val dataValidade: String,
    val codigoSeguranca: String,
)

data class CarrinhoResponse(
    val produto: ProdutoResponse,
    val quantidade: Int,
    val tamanhoSelecionado: String
)
