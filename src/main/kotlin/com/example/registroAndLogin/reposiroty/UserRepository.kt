package com.example.registroAndLogin.reposiroty

import com.example.registroAndLogin.data.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : JpaRepository<User, Long> {

    fun findByName(name: String?): Optional<User>

}