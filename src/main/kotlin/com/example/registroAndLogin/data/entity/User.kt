package com.example.registroAndLogin.data.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
open class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Long? = null

    @field:NotEmpty(message = "The user must not empty")
    @Column(name = "NOMBRE", nullable = false)
    open var name: String? = null

    @field:NotEmpty(message = "The user must not empty")
    @Column(name = "apellido", nullable = false)
    open var apellido: String? = null

    @field:NotEmpty(message = "The user must not empty")
    @Column(name = "password", nullable = false)
    open var password: String? = null
}