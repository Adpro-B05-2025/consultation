plugins {
    id("org.springframework.boot") version "3.4.5"
    id("io.spring.dependency-management") version "1.1.7"
    java
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
<<<<<<< HEAD
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.micrometer:micrometer-registry-prometheus")
=======
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
<<<<<<< HEAD
    
    // PostgreSQL database
    runtimeOnly("org.postgresql:postgresql")
=======
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    
    // Jika pakai database (misal H2, MySQL, atau PostgreSQL), tambahkan juga
    runtimeOnly("com.h2database:h2") // Untuk development/testing
    runtimeOnly("org.postgresql:postgresql")  // Jika pakai PostgreSQL
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164

    // MapStruct
    implementation("org.mapstruct:mapstruct:1.5.5.Final")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")

<<<<<<< HEAD
    // Spring Boot standard
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // runtimeOnly("com.h2database:h2") // jika pakai H2
=======
    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")
    implementation("org.springframework.boot:spring-boot-starter-security")
>>>>>>> 2e29ec9bc52a6308dfb174f03ed801fa11720164
}

tasks.withType<Test> {
    useJUnitPlatform()
}
