package com.mensshop.mensshop.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*

@Entity
data class Endereco(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long = 0,

    @JsonProperty("logradouro")
    val logradouro: String,

    @JsonProperty("numero")
    val numero: Int,

    @JsonProperty("complemento")
    val complemento: String,

    @JsonProperty("cep")
    val cep: String,

    @JsonProperty("bairro")
    val bairro: String,

    @JsonProperty("cidade")
    val cidade: String,

    @JsonProperty("estado")
    val estado: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", nullable = false)
    @JsonIgnore
    val usuario: Usuario
)
