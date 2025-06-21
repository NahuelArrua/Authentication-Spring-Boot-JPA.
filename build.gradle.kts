plugins {

    kotlin("jvm") version "1.9.25"
    id("org.springframework.boot") version "3.5.0"

    id("io.spring.dependency-management") version "1.1.6"

    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.9.23"
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

noArg {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

group = "com.example" // Puedes cambiar esto a tu grupo de paquetes
version = "0.0.1-SNAPSHOT" // Puedes cambiar esto a tu versión

java {
    sourceCompatibility = JavaVersion.VERSION_17 // Coincide con Java 17 de tus logs
}

repositories {
    mavenCentral() // Repositorio central de Maven para obtener dependencias
}

dependencies {
    // Spring Boot Starter para aplicaciones web (incluye Tomcat)
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Módulo Jackson para Kotlin (manejo de JSON)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    // Soporte para reflexión de Kotlin (a menudo necesario con Spring y Hibernate)
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // Kotlin Standard Library para JDK 8 (se usa aunque tu JDK sea 17, es una dependencia estándar)
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Spring Boot Starter para JPA (incluye Hibernate)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // Driver de la base de datos H2 (en memoria o basado en archivos)
    runtimeOnly("com.h2database:h2") // 'runtimeOnly' porque solo se necesita en tiempo de ejecución

    // Spring Boot Starter para validación (ej. @NotBlank, @Size)
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // Spring Boot Starter para Spring Security
    implementation("org.springframework.boot:spring-boot-starter-security")

    // Dependencias de prueba (para tests unitarios e de integración)
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test") // Para testing de seguridad
    testImplementation(kotlin("test")) // Para escribir tests en Kotlin
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17" // También aquí la versión de JVM
    }
}

// Configuración para JUnit 5 para las tareas de prueba
tasks.test {
    useJUnitPlatform()
}

