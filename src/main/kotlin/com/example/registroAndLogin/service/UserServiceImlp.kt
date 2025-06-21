package com.example.registroAndLogin.service

import com.example.registroAndLogin.entity.LoginRequest
import com.example.registroAndLogin.entity.User
import com.example.registroAndLogin.exceptions.UserAlreadyExistExceptions
import com.example.registroAndLogin.exceptions.UserNotFoundExceptions
import com.example.registroAndLogin.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Transactional
@Service
open class UserServiceImplementation(private val userRepository: UserRepository) : UserService {

    @Transactional
    override fun registerUser(user: User) : User {
        if (userRepository.findByName(user.name).isPresent) {
            throw UserAlreadyExistExceptions(message = "the user ${user.name} not exist")
        }
        return userRepository.save(user)
    }


    override fun loginUser(loginRequest: LoginRequest): User {
        return userRepository.findByNameAndPassword(loginRequest.name, loginRequest.password)
    }

    override fun findAllUser(): List<User> {
        return userRepository.findAll()
    }

    override fun findByIdUser(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow { UserNotFoundExceptions(message = "the user ID ${id} not exist") }
    }

    @Transactional
    override fun updateUser(id: Long, user: User): User {
        val existUser = userRepository.findById(id).orElseThrow {

            UserNotFoundExceptions(message = "the user ID ${id} not exist")
        }
        existUser.id = user.id
        existUser.name = user.name
        existUser.apellido = user.apellido
        existUser.password = user.password

        return userRepository.save(existUser)
    }

    @Transactional
    override fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            throw UserNotFoundExceptions(message = "the user ID ${id} not exist")
        }
    }
}