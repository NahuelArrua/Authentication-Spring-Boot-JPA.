package com.example.registroAndLogin.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
open class SecurityConfig {

    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/h2-console/**", "/api/users/**")
            }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/api/users/register").permitAll()
                    .requestMatchers("/api/users/login").permitAll()
                    .anyRequest().authenticated()
            }
            .headers { headers ->
                headers.frameOptions { frameOptions ->
                    frameOptions.sameOrigin()
                }
            }
        return http.build()
    }
}
