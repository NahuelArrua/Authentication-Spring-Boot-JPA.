package com.example.registroAndLogin.service

import com.example.registroAndLogin.data.dto.LoginRequest
import com.example.registroAndLogin.data.entity.User
import org.springframework.stereotype.Service

@Service
interface UserService {

    fun registerUser(user: User): User
    fun loginUser(loginRequest: LoginRequest): String
    fun findAllUser(): List<User>
    fun findByIdUser(id: Long): User?
    fun updateUser(id: Long, user: User): User?
    fun deleteUser(id: Long)
}