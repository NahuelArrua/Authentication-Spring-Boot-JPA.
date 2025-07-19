package com.example.registroAndLogin.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider {
    private val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)

    private val validityInMilliseconds: Long = 3600000

    fun generateToken(username: String): String {
        val now = Date()
        val expiryDate = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key)
            .compact()
    }

    fun getUserNameFromJwt(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJwt(token)
            .body
            .subject
    }

    fun validatedToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            true
        } catch (ex: Exception) {
            false
        }
    }
}