package com.example.registroAndLogin.data.dto

import jakarta.persistence.Column
import jakarta.validation.constraints.NotEmpty

open class LoginRequest {

    @field:NotEmpty(message = "The user must not empty")
    @Column(name = "NOMBRE", nullable = false)
    open var name: String? = null

    @field:NotEmpty(message = "The user must not empty")
    @Column(name = "password", nullable = false)
    open var password: String? = null
}