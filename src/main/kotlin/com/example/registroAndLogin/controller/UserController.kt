package com.example.registroAndLogin.controller

import com.example.registroAndLogin.entity.User
import com.example.registroAndLogin.exceptions.UserAlreadyExistExceptions
import com.example.registroAndLogin.exceptions.UserNotFoundExceptions
import com.example.registroAndLogin.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody user: User): ResponseEntity<User> {
        return try {
            val savedUser = userService.registerUser(user)
                ResponseEntity.status(HttpStatus.CREATED).body(savedUser)

        } catch (e: UserAlreadyExistExceptions) {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }
}