package com.mensshop.mensshop.dto

data class RegistroRequest(
    val nome: String,
    val sobrenome: String,
    val email: String,
    val telefone: String,
    val senha: String
)