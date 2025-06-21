package com.example.registroAndLogin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.example.registroAndLogin")
class RegistroAndLoginApplication

fun main(args: Array<String>) {
	runApplication<RegistroAndLoginApplication>(*args)
}
