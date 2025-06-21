package com.example.registroAndLogin.service

import com.example.registroAndLogin.entity.LoginRequest
import com.example.registroAndLogin.entity.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
interface UserService {
    fun registerUser(user: User) : User
    fun loginUser(loginRequest: LoginRequest): User
    fun findAllUser(): List<User>
    fun findByIdUser(id: Long): User
    fun updateUser(id: Long, user: User): User
    fun deleteUser(id: Long)
}