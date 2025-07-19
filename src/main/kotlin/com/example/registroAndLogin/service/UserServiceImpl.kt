package com.example.registroAndLogin.service

import com.example.registroAndLogin.data.dto.LoginRequest
import com.example.registroAndLogin.data.entity.User
import com.example.registroAndLogin.exceptions.UserAlreadyExistExceptions
import com.example.registroAndLogin.exceptions.UserNotFoundExceptions
import com.example.registroAndLogin.reposiroty.UserRepository
import com.example.registroAndLogin.security.JwtTokenProvider
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
@Transactional
open class UserServiceImplementation(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : UserService {

    @Transactional
    override fun registerUser(user: User): User {
        if (userRepository.findByName(user.name).isPresent) {
            throw UserAlreadyExistExceptions(message = "The user ${user.name} not exist")
        }
        user.password = passwordEncoder.encode(user.password)
        return userRepository.save(user)
    }

    override fun loginUser(loginRequest: LoginRequest): String {
        val userOptional = userRepository.findByName(loginRequest.name)

        if (userOptional.isPresent) {
            val user = userOptional.get()
            if (passwordEncoder.matches(loginRequest.password, user.password)) {
                return jwtTokenProvider.generateToken(user.name!!)
            }
        }

        throw UserNotFoundExceptions(message = "Invalid credentials username of password")
    }


    override fun findAllUser(): List<User> {
        return userRepository.findAll()
    }

    override fun findByIdUser(id: Long): User {
        return userRepository.findById(id).orElseThrow { UserNotFoundExceptions(message = "The user not exist") }
    }

    override fun updateUser(id: Long, user: User): User {
        val userExist = userRepository.findById(id).orElseThrow {
            UserNotFoundExceptions(message = "The user ${id} not exist")
        }
        userExist.id = user.id
        userExist.name = user.name
        userExist.apellido = user.apellido
        userExist.password = user.password

        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        if (userRepository.existsById(id)) {
            throw UserNotFoundExceptions(message = "The user ${id} not exist")
        }
    }
}