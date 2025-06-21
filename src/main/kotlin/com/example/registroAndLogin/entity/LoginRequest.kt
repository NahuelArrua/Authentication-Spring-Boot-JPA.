package com.example.registroAndLogin.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank

@Entity
open class LoginRequest {
    @Id
    @Column(name = "name", nullable = false)
    @field:NotBlank(message = "the user must not empty")
    open var name: String? = null

    @Column(name = "password", nullable = false)
    @field:NotBlank(message = "the user must not empty")
    open var password: String? = null
}