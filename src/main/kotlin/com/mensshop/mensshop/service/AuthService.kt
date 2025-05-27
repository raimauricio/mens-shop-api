package com.mensshop.mensshop.service

import com.mensshop.mensshop.dto.*
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
    fun registrar(request: RegistroRequest): AuthResponse {
        val usuario = Usuario(
            nome = request.nome,
            email = request.email,
            sobrenome = request.sobrenome,
            telefone = request.telefone,
            senha = passwordEncoder.encode(request.senha)
        )

        usuarioRepository.save(usuario)

        val token = jwtService.gerarToken(usuario.email)
        return AuthResponse(token)
    }

    fun autenticar(request: LoginRequest): AuthResponse {
        val usuario = usuarioRepository.findByEmail(request.email)
            .orElseThrow { Exception("Usuário não encontrado") }

        if (!passwordEncoder.matches(request.senha, usuario.senha)) {
            throw Exception("Senha inválida")
        }

        val token = jwtService.gerarToken(usuario.email)
        return AuthResponse(token)
    }
}
