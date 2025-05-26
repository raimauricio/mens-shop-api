package com.mensshop.mensshop.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys

import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {

    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    fun gerarToken(username: String): String {
        val agora = Date()
        val expiracao = Date(agora.time + 1000 * 60 * 60 * 24) // 24h

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(agora)
            .setExpiration(expiracao)
            .signWith(secretKey)
            .compact()
    }

    fun validarToken(token: String): String? {
        return try {
            val parsed = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)

            parsed.body.subject
        } catch (e: Exception) {
            null
        }
    }
}
