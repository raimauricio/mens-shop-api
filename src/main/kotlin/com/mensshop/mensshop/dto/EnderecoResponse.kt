package com.mensshop.mensshop.dto

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