package com.mensshop.mensshop.dto

import com.mensshop.mensshop.model.Cartao
import com.mensshop.mensshop.model.Endereco

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
    val enderecos: List<Endereco>,
    val cartoes: List<Cartao>
)
