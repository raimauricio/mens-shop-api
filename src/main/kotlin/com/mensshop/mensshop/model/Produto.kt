package com.mensshop.mensshop.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
data class Produto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val Id: Long = 0,

    @JsonProperty("nome")
    val Nome: String,

    @JsonProperty("imagem")
    val Imagem: String,

    @JsonProperty("preco")
    val Preco: Double,

    @JsonProperty("categoria")
    val Categoria: String,

    @JsonProperty("tamanhos")
    @ElementCollection
    val Tamanhos: List<String>
)
