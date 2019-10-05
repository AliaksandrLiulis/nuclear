import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.util.jaxb.*
import org.jooq.util.jaxb.Configuration
import org.jooq.util.jaxb.Target
import java.util.*

plugins {
    groovy
    java
    war
    idea
    kotlin("jvm") version "1.3.41"
    kotlin("kapt") version "1.3.30"
    id("org.springframework.boot") version "2.0.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("ua.eshepelyuk.ManifestClasspath") version "1.0.0"
    id("com.rohanprabhu.kotlin-dsl-jooq") version "0.3.1"
}

group = "by.isotop_technology.storage"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.5.RELEASE")
    }
}

val jvmVersion: JavaVersion = JavaVersion.VERSION_1_8

java {
    sourceCompatibility = jvmVersion
    targetCompatibility = jvmVersion
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = jvmVersion.toString()
}

// Dependency version numbers
val jacksonVersion = "2.9.8"
val jacksonKotlinModuleVersion = "2.9.7"
val postgresVersion = "42.2.5"
val jooqVersion = "3.11.11"
val simpleFlatMapperVersion = "7.0.0"
val springSecurityVersion = "2.1.3.RELEASE"
val springSecurityOauthVersion = "2.3.5.RELEASE"
val springSecurityJwtVersion = "1.0.10.RELEASE"

dependencies {

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    // Spring dependencies
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-aop")

    implementation("org.jboss.resteasy:resteasy-jaxrs:3.0.14.Final")
    implementation("org.jboss.resteasy:resteasy-client:3.0.14.Final")
    implementation("org.jboss.resteasy:resteasy-jackson2-provider:3.0.14.Final")

    // Kotlin Reflect
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.30")


    implementation("javax.servlet:javax.servlet-api:3.1.0")

    implementation("javax.cache:cache-api:1.0.0")

    implementation("javax.inject:javax.inject:1")

    providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

    implementation("org.postgresql:postgresql:$postgresVersion")

    // To convert responses to XML
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonKotlinModuleVersion}")

    // For Kotlin
    implementation(kotlin("stdlib-jdk8"))

    implementation("org.jooq:jooq:$jooqVersion")
    implementation("org.simpleflatmapper:sfm-jooq:$simpleFlatMapperVersion")


    // Spring dependencies
    implementation("org.springframework.boot:spring-boot-starter-security:$springSecurityVersion")
    implementation("org.springframework.security.oauth:spring-security-oauth2:$springSecurityOauthVersion")
    implementation("org.springframework.security:spring-security-jwt:$springSecurityJwtVersion")

    jooqGeneratorRuntime("org.postgresql:postgresql:$postgresVersion")
}

springBoot {
    mainClassName = "com.isotop.storage.ServiceKt"
}

val props = Properties()
props.load(file("jooq.properties").inputStream())

jooqGenerator {
    configuration("base", project.the<SourceSetContainer>()["main"]) {
        configuration = Configuration().apply {
            jdbc = Jdbc().withDriver("org.postgresql.Driver")
                .withUsername("postgres")
                .withPassword("root")
                .withUrl("jdbc:postgresql://localhost:5432/nuclear_base")
            generator = Generator().withTarget(Target()
                .withPackageName("com.isotop.storage.jooq")
                .withDirectory("${project.buildDir}/generated/jooq/base"))
                .withDatabase(Database().withTableValuedFunctions(false)
                    .withInputSchema("public")
                    .withForcedTypes(ForcedType().withTypes("TIMESTAMPTZ")
                        .withUserType("java.time.ZonedDateTime")
                        .withConverter("com.isotop.storage.repository.jooq.TimestampBinding")))
                .withGenerate(Generate()
                    .withRoutines(false)
                    .withIndexes(false)
                    .withQueues(false)
                    .withRecordsImplementingRecordN(false))
        }
    }
}