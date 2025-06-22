package com.mensshop.mensshop.model

import jakarta.persistence.*

@Entity
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nome: String,

    val sobrenome: String,

    val telefone: String,

    @Column(unique = true)
    val email: String,

    val senha: String,

    @OneToMany(mappedBy = "usuario",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var cartoes: MutableList<Cartao> = mutableListOf(),

    @OneToMany(mappedBy = "usuario",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var enderecos: MutableList<Endereco> = mutableListOf(),

    @OneToMany(mappedBy = "usuario",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var compras: MutableList<Compra> = mutableListOf(),

    @OneToOne(mappedBy = "usuario",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var carrinho: Carrinho? = null
)
