package com.mensshop.mensshop.model

import jakarta.persistence.*

@Entity
data class ItemCarrinho(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val quantidade: Int,

    val tamanhoSelecionado: String,

    @ManyToOne
    @JoinColumn(name = "produto_id")
    val produto: Produto,

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    val carrinho: Carrinho
)
