package com.mensshop.mensshop.controller

import com.mensshop.mensshop.dto.CompraRequest
import com.mensshop.mensshop.dto.CompraResponse
import com.mensshop.mensshop.dto.Etapas
import com.mensshop.mensshop.dto.ProdutosPedidoResponse
import com.mensshop.mensshop.model.Cartao
import com.mensshop.mensshop.model.Compra
import com.mensshop.mensshop.model.Endereco
import com.mensshop.mensshop.model.Pagamento
import com.mensshop.mensshop.model.ProdutoComprado
import com.mensshop.mensshop.model.Recebimento
import com.mensshop.mensshop.repository.ProdutoRepository
import com.mensshop.mensshop.repository.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import java.time.LocalDateTime
import org.springframework.security.core.userdetails.User as SpringUser

@RestController
@RequestMapping("/compra")
class CompraController(
    private val usuarioRepository: UsuarioRepository,
    private val produtoRepository: ProdutoRepository
) {
    @PostMapping("/{id}")
    fun realizarCompra(
        @PathVariable id: Long,
        @RequestBody compraRequest: CompraRequest,
        auth: Authentication,
    ): ResponseEntity<Any> {
        val springUser = auth.principal as SpringUser
        val emailLogado = springUser.username

        val usuario = usuarioRepository.findByEmail(emailLogado)
            .orElseThrow { ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido") }

        if (usuario.id != id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode modificar outro usuário")
        }

        val produtosComprados: MutableList<ProdutoComprado> = mutableListOf()

        compraRequest.produtos.map { produtoRequest ->
            val produto = produtoRepository.findById(produtoRequest.produtoId)
                .orElseThrow {
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Produto ${produtoRequest.produtoId} não encontrado"
                    )
                }

            produtosComprados.add(
                ProdutoComprado(
                    0,
                    produtoRequest.quantidade,
                    produtoRequest.tamanhoSelecionado,
                    produto
                )
            )
        }

        var cartaoPagamento: Cartao? = null
        if(compraRequest.pagamento.cartao != null) {
            if (compraRequest.pagamento?.cartao?.id == 0L) {
                val cartao = compraRequest.pagamento.cartao
                Cartao(
                    0,
                    cartao.numero,
                    cartao.nome,
                    cartao.dataValidade,
                    cartao.codigoSeguranca,
                    usuario
                ).let { cartao ->
                    cartaoPagamento = cartao
                    usuario.cartoes.add(cartao)
                }
            } else {
                cartaoPagamento = usuario.cartoes.find { it.id == compraRequest.pagamento.cartao?.id }
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado")
            }
        }


        val pagamento = Pagamento(
            0,
            compraRequest.pagamento.valorTotal,
            compraRequest.pagamento.tipo,
            cartaoPagamento
        )

        var enderecoEntrega: Endereco? = null
        if(compraRequest.recebimento.retiradaLoja == null) {
            if (compraRequest.recebimento?.endereco?.id == 0L) {
                val endereco = compraRequest.recebimento.endereco
                Endereco(
                    0,
                    endereco.logradouro,
                    endereco.numero,
                    endereco.cep,
                    endereco.complemento,
                    endereco.bairro,
                    endereco.cidade,
                    endereco.estado,
                    usuario
                ).let { endereco ->
                    usuario.enderecos.add(endereco)
                    enderecoEntrega = endereco
                }
            } else {
                enderecoEntrega = usuario.enderecos.find { it.id == compraRequest.recebimento.endereco?.id }
                    ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado")
            }
        }


        val recebimento = Recebimento(
            0,
            compraRequest.recebimento.tipo,
            enderecoEntrega ?: null,
            compraRequest.recebimento.retiradaLoja ?: 0
        )

        usuario.compras.add(
            Compra(
                0,
                LocalDateTime.now(),
                produtosComprados,
                recebimento,
                pagamento,
                usuario
            )
        )
        usuario.carrinho = null

        usuarioRepository.save(usuario)

        return ResponseEntity.ok( mapOf(
            "titulo" to ("Compra concluída."),
            "mensagem" to ("Acompanhe o status da sua compra no nosso menu de Minhas compras.")
        ))
    }

    @GetMapping("/{id}")
    fun minhasCompras(
        @PathVariable id: Long,
        auth: Authentication,
    ): ResponseEntity<List<CompraResponse>> {
        val springUser = auth.principal as SpringUser
        val emailLogado = springUser.username

        val usuario = usuarioRepository.findByEmail(emailLogado)
            .orElseThrow { ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido") }

        if (usuario.id != id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode modificar outro usuário")
        }

        if (usuario.compras.isEmpty()) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma compra encontrada")
        }

        val etapas: List<Etapas> = listOf(
            Etapas("Processando pagamento", LocalDateTime.now(), "pi pi-credit-card")
        )



        if(usuario.compras.isEmpty()){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma compra encontrada")
        }

        val compras = usuario.compras
            .map { compra ->
                CompraResponse(
                    compra.id,
                    compra.data,
                    compra.pagamento.valorTotal,
                    "Pedido em processamento",
                    if (compra.recebimento.tipo == "retirada")
                            "${compra.recebimento.retiradaLoja}"
                        else
                            "Entrega a Domicílio - ${compra.recebimento.enderecoEntrega?.logradouro}, ${compra.recebimento.enderecoEntrega?.numero} - ${compra.recebimento.enderecoEntrega?.cep}",
                    compra.produtos.map{ it ->
                        ProdutosPedidoResponse(
                            it.produto.nome,
                            it.quantidade,
                            it.tamanhoSelecionado,
                            it.produto.preco
                        )

                    },
                    etapas.map { it.copy(data = compra.data) }
                )
            }

        return ResponseEntity.ok(compras)
    }
}



