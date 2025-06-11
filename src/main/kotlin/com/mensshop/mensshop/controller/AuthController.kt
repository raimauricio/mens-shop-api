package com.mensshop.mensshop.controller

import com.mensshop.mensshop.dto.*
import com.mensshop.mensshop.service.AuthService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/registro")
    fun registrar(@RequestBody request: RegistroRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.registrar(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest, response: HttpServletResponse): ResponseEntity<Any> {
        return try {
            val responseAutenticacao = authService.autenticar(request)
            response.setHeader("Authorization", "Bearer ${responseAutenticacao.token}")
            ResponseEntity.ok(responseAutenticacao.userResponse)
        } catch (ex: Exception) {
            val erro = mapOf(
                "titulo" to ("Erro"),
                "mensagem" to ("Erro ao tentar realizar o login, verifique as credenciais e tente novamente!")
            )
            ResponseEntity.badRequest().body(erro)
        }
    }
}
