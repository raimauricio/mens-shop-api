package com.mensshop.mensshop.dto

data class ProdutoResponse(
    val id: Long,
    val nome: String,
    val preco: Double,
    val categoria: String,
    val imagem: String
)
