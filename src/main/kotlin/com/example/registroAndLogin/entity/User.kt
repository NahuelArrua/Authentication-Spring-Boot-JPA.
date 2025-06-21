package com.example.registroAndLogin.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "users")
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @field:NotBlank(message = "the user must not empty")
    @Column(name = "NOMBRE", nullable = false)
    open var name: String? = null

    @field:NotBlank(message = "the user must not empty")
    @Column(name = "apellido", nullable = false)
    open var apellido: String? = null

    @field:NotBlank(message = "the user must not empty")
    @Column(name = "password", nullable = false)
    open var password: String? = null
}