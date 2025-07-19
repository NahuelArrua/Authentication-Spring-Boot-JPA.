plugins {

    kotlin("jvm") version "1.9.25"
    id("org.springframework.boot") version "3.5.0"

    id("io.spring.dependency-management") version "1.1.6"

    id("org.jetbrains.kotlin.plugin.allopen") version "1.9.23"
    id("org.jetbrains.kotlin.plugin.noarg") version "1.9.23"
}


group = "com.example" // Puedes cambiar esto a tu grupo de paquetes
version = "0.0.1-SNAPSHOT" // Puedes cambiar esto a tu versión


// El bloque 'java' debe ir aquí, fuera del bloque 'plugins'
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // ¡Asegúrate de que sea 17!
    }
}

repositories {
    mavenCentral() // Repositorio central de Maven para obtener dependencias
}

dependencies {
    // Spring Boot Starter para aplicaciones web (incluye Tomcat)
    implementation("org.springframework.boot:spring-boot-starter-web")
    // Módulo Jackson para Kotlin (manejo de JSON)
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

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

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")



    // Para manejar JSON con Kotlin
    // Otras dependencias necesarias
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    
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

