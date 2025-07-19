package com.example.registroAndLogin.controller

import com.example.registroAndLogin.data.dto.AuthResponse
import com.example.registroAndLogin.data.dto.LoginRequest
import com.example.registroAndLogin.data.entity.User
import com.example.registroAndLogin.exceptions.UserAlreadyExistExceptions
import com.example.registroAndLogin.exceptions.UserNotFoundExceptions
import com.example.registroAndLogin.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
open class UserController(private val userService: UserService) {

    @PostMapping("/register")
    open fun registerUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        return try {
            val saveUser = userService.registerUser(user)
            ResponseEntity.status(HttpStatus.CREATED).body(saveUser)
        } catch (e: UserAlreadyExistExceptions) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PostMapping("/login")
    open fun loginUser(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<AuthResponse> {
        val jwt = userService.loginUser(loginRequest)
        return ResponseEntity.ok(AuthResponse(jwt))
    }

    @GetMapping()
    open fun findAllUser(): ResponseEntity<List<User>> {
        val user = userService.findAllUser()
        return ResponseEntity.ok(user)
    }

    // Endpoint para obtener un usuario por ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        return try {
            val user = userService.findByIdUser(id)
            ResponseEntity.ok(user) // 200 OK
        } catch (e: UserNotFoundExceptions) {
            ResponseEntity.notFound().build() // 404 Not Found
        }
    }

    // Endpoint para actualizar un usuario por ID
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody user: User): ResponseEntity<User> {
        return try {
            val updatedUser = userService.updateUser(id, user)
            ResponseEntity.ok(updatedUser) // 200 OK
        } catch (e: UserNotFoundExceptions) {
            ResponseEntity.notFound().build() // 404 Not Found
        }
    }

    // Endpoint para eliminar un usuario por ID
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            userService.deleteUser(id)
            ResponseEntity.noContent().build() // 204 No Content
        } catch (e: UserNotFoundExceptions) {
            ResponseEntity.notFound().build() // 404 Not Found
        }
    }
}
