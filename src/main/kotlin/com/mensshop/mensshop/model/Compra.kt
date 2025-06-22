package com.mensshop.mensshop.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Compra(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    val id: Long = 0,

    @JsonProperty("data")
    val data: LocalDateTime,


    @OneToMany(cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    @JsonProperty("produtos")
    val produtos: MutableList<ProdutoComprado> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.ALL],fetch = FetchType.LAZY)
    @JoinColumn(name = "recebimento_id")
    @JsonProperty("recebimento")
    val recebimento: Recebimento,

    @OneToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_id")
    @JsonProperty("pagamento")
    val pagamento: Pagamento,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioId", nullable = false)
    @JsonIgnore
    val usuario: Usuario
)

@Entity
class Pagamento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @JsonProperty("valorTotal")
    val valorTotal: BigDecimal,

    @JsonProperty("formaPagamento")
    val formaPagamento: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartaoId", nullable = false)
    @JsonIgnore
    @JsonProperty("cartaoUsado")
    val cartaoUsado: Cartao? = null
)

@Entity
class Recebimento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @JsonProperty("tipo")
    val tipo: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enderecoId", nullable = true)
    @JsonIgnore
    @JsonProperty("enderecoEntrega")
    val enderecoEntrega: Endereco? = null,

    val retiradaLoja: Int
)

@Entity
class ProdutoComprado(
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   val id: Long = 0,

    @JsonProperty("quantidade")
   val quantidade: Int,

    @JsonProperty("tamanhoSelecionado")
   val tamanhoSelecionado: String,

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "produto_id")
   val produto: Produto
)