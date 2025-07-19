package com.example.registroAndLogin.security

import com.example.registroAndLogin.reposiroty.UserRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

@Configuration
open class SecurityConfig(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    open fun userDetailService(): UserDetailsService {

        return UserDetailsService { username: String ->
            val user = userRepository.findByName(username)
                .orElseThrow { UsernameNotFoundException("User not found whit username") }
            User(user.name, user.password, emptyList())
        }
    }

    @Bean
    open fun jwtRequestFilter(): OncePerRequestFilter {
        return object : OncePerRequestFilter() {
            override fun doFilterInternal(
                request: HttpServletRequest,
                response: HttpServletResponse,
                filterChain: FilterChain
            ) {
                val authenticationHeader = request.getHeader("Authentication")

                var jwt: String? = null
                var username: String? = null

                if (authenticationHeader != null && authenticationHeader.startsWith("bearer")) {
                    jwt = authenticationHeader.substring(7)
                    username = jwtTokenProvider.getUserNameFromJwt(jwt)
                }

                if (username != null && SecurityContextHolder.getContext().authentication == null) {
                    val userDetails = userDetailService().loadUserByUsername(username)


                    if (jwtTokenProvider.validatedToken(jwt!!)) {
                        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.authorities
                        )
                        usernamePasswordAuthenticationToken.details =
                            WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
                    }
                }
                filterChain.doFilter(request, response)
            }
        }
    }


    @Bean
    open fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { csrf ->
            csrf.ignoringRequestMatchers("/h2-console/**", "/api/users/**")
        }.authorizeHttpRequests { authorize ->

            authorize
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                .requestMatchers("/api/users/login").permitAll()
                .requestMatchers("/api/users/**").authenticated()
                .anyRequest().authenticated()
        }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(
                jwtRequestFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .headers { headers ->
                headers.frameOptions { frameOptions ->
                    frameOptions.sameOrigin()
                }
            }
        return http.build()
    }
}
