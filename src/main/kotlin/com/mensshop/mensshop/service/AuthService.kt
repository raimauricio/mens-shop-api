package com.mensshop.mensshop.service

import com.mensshop.mensshop.dto.*
import com.mensshop.mensshop.model.Produto
import com.mensshop.mensshop.model.Usuario
import com.mensshop.mensshop.repository.UsuarioRepository
import com.mensshop.mensshop.security.JwtService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {
    fun registrar(request: RegistroRequest): String {
        if (usuarioRepository.findByEmail(request.email).isPresent()) {
            throw Exception("Usuário já existente")
        }

        val usuario = Usuario(
            0,
            request.nome,
            request.sobrenome,
            request.telefone,
            request.email,
            passwordEncoder.encode(request.senha)
        )

        usuarioRepository.save(usuario)

        return "Usuário registrado com sucesso, realize o login!"
    }

    fun autenticar(request: LoginRequest): LoginResponse {
        val usuario = usuarioRepository.findByEmail(request.email)
            .orElseThrow { Exception("Usuário não encontrado") }

        if (!passwordEncoder.matches(request.senha, usuario.senha)) {
            throw Exception("Senha inválida")
        }

        val token = jwtService.gerarToken(usuario.email)

        val usuarioResponse = UsuarioResponse(
            usuario.id,
            usuario.nome,
            usuario.sobrenome,
            usuario.email,
            usuario.telefone,
            usuario.enderecos.map {
                EnderecoResponse(
                    it.id,
                    it.logradouro,
                    it.numero,
                    it.cep,
                    it.complemento,
                    it.bairro,
                    it.cidade,
                    it.estado
                )
            } ?: emptyList(),
            usuario.cartoes.map {
                CartaoResponse(
                    it.id,
                    it.numero,
                    it.nome,
                    it.dataValidade,
                    it.codigoSeguranca
                )
            } ?: emptyList(),
            usuario.carrinho?.itens?.map {
                CarrinhoResponse(
                    it.produto.let { produto: Produto ->
                        ProdutoResponse(
                            produto.id,
                            produto.nome,
                            produto.preco,
                            produto.categoria,
                            produto.imagem
                        )
                    }?: ProdutoResponse(0, "", 0.0, "", ""),
                    it.quantidade,
                    it.tamanhoSelecionado
                )
            } ?: emptyList()
        )
        return LoginResponse(token, usuarioResponse)
    }
}