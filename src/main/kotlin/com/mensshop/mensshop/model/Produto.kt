package com.mensshop.mensshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
data class Produto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long = 0,

    @JsonProperty("nome")
    val nome: String,

    @JsonProperty("imagem")
    val imagem: String,

    @JsonProperty("preco")
    val preco: Double,

    @JsonProperty("categoria")
    val categoria: String,

    @JsonProperty("tamanhos")
    @ElementCollection
    val tamanhos: List<String>
)
