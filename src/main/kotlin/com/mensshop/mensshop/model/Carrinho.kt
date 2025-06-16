package com.mensshop.mensshop.model

import jakarta.persistence.*

@Entity
data class Carrinho(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne
    @JoinColumn(name = "usuario_id")
    val usuario: Usuario,

    @OneToMany(mappedBy = "carrinho",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    val itens: List<ItemCarrinho> = emptyList()
)
