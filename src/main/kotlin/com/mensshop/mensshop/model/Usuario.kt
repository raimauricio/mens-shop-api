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

    @OneToMany(mappedBy = "usuario")
    val cartoes: List<Cartao> = emptyList(),

    @OneToMany(mappedBy = "usuario")
    val enderecos: List<Endereco> = emptyList(),

    @OneToOne(mappedBy = "usuario",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    val carrinho: Carrinho? = null,
)
