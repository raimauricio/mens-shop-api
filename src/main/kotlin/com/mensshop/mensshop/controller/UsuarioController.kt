package com.mensshop.mensshop.controller

import com.mensshop.mensshop.dto.CartaoResponse
import com.mensshop.mensshop.dto.EnderecoResponse
import com.mensshop.mensshop.dto.ItemCarrinhoRequest
import com.mensshop.mensshop.dto.UsuarioUpdateRequest
import com.mensshop.mensshop.model.Carrinho
import com.mensshop.mensshop.model.Cartao
import com.mensshop.mensshop.model.Endereco
import com.mensshop.mensshop.model.ItemCarrinho
import com.mensshop.mensshop.model.Usuario
import com.mensshop.mensshop.repository.ProdutoRepository
import com.mensshop.mensshop.repository.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User as SpringUser

@RestController
@RequestMapping("/usuario")
class UsuarioController(
    private val usuarioRepository: UsuarioRepository,
    private val produtoRepository: ProdutoRepository
) {
    @PutMapping("/{id}")
    fun atualizarUsuario(
        @PathVariable id: Long,
        @RequestBody usuarioUpdateRequest: UsuarioUpdateRequest,
        auth: Authentication,
    ): ResponseEntity<Void> {
        val springUser = auth.principal as SpringUser
        val emailLogado = springUser.username

        val usuario = usuarioRepository.findByEmail(emailLogado)
            .orElseThrow { ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário inválido") }

        if (usuario.id != id) {
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Você não pode modificar outro usuário")
        }

        usuarioUpdateRequest.endereco?.let {
            if (!usuario.enderecos.any { end -> end.id == it.id }) {
                adicionarEndereco(it, usuario)
            }
        }

        usuarioUpdateRequest.cartao?.let {
            if (!usuario.cartoes.any { cart -> cart.id == it.id }) {
                adicionarCartao(it, usuario)
            }
        }

        usuarioUpdateRequest.carrinho?.let {
            atualizarCarrinho(it, usuario)
        }

        usuarioRepository.save(usuario)

        return ResponseEntity.ok().build()
    }

    fun adicionarEndereco(novoEndereco: EnderecoResponse, usuario: Usuario) {
        val endereco = Endereco(
           id = 0,
           novoEndereco.logradouro,
           novoEndereco.numero,
           novoEndereco.complemento,
           novoEndereco.cep,
           novoEndereco.bairro,
           novoEndereco.cidade,
           novoEndereco.estado,
           usuario = usuario
        )
        usuario.enderecos.add(endereco)
    }

    fun adicionarCartao(novoCartao: CartaoResponse, usuario: Usuario) {
        val cartao = Cartao(
            id = 0,
            novoCartao.numero,
            novoCartao.nome,
            novoCartao.dataValidade,
            novoCartao.codigoSeguranca,
            usuario
        )
        usuario.cartoes.add(cartao)
    }

    fun atualizarCarrinho(novoCarrinho: List<ItemCarrinhoRequest>, usuario: Usuario): Usuario {
        val carrinho = usuario.carrinho ?: Carrinho(usuario = usuario)

        carrinho.itens.clear()

        val novosItens = novoCarrinho.map { itemResponse ->
            val produto = produtoRepository.findById(itemResponse.produtoId)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Produto ${itemResponse.produtoId} não encontrado") }

            ItemCarrinho(
                id = 0,
                itemResponse.quantidade,
                itemResponse.tamanhoSelecionado,
                produto,
                carrinho
            )
        }

        carrinho.itens.addAll(novosItens)

        usuario.carrinho = carrinho

        return usuario
    }

}



