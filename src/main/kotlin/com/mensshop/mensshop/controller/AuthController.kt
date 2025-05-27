package com.mensshop.mensshop.controller

import com.mensshop.mensshop.dto.*
import com.mensshop.mensshop.service.AuthService
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
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        return ResponseEntity.ok(authService.autenticar(request))
    }
}
