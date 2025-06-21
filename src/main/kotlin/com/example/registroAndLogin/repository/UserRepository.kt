package com.example.registroAndLogin.repository

import com.example.registroAndLogin.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findByName(name: String?): Optional<User>

    fun findByNameAndPassword(name: String?, password: String?): User
}