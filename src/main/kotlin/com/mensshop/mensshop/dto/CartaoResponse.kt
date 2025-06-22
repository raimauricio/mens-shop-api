package com.mensshop.mensshop.dto

data class CartaoResponse(
    val id: Long,
    val numero: String,
    val nome: String,
    val dataValidade: String,
    val codigoSeguranca: String,
)