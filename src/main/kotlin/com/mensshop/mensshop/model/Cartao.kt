package com.mensshop.mensshop.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
data class Cartao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long = 0,

    @JsonProperty("numero")
    val numero: String,

    @JsonProperty("nome")
    val nome: String,

    @JsonProperty("dataValidade")
    val dataValidade: String,

    @JsonProperty("codigoSeguranca")
    val codigoSeguranca: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", nullable = false)
    @JsonIgnore
    val usuario: Usuario
)