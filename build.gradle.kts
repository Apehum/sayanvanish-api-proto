plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    alias(libs.plugins.kapt)
    alias(libs.plugins.protobuf)
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(libs.kotlinx.coroutines.core)
    compileOnly(libs.kotlinx.coroutines.jdk8)

    compileOnly(libs.velocity)
    kapt(libs.velocity)

    compileOnly(libs.sayanvanish.api)

    compileOnly(libs.protobuf.kotlin) {
        exclude("org.jetbrains.kotlin")
    }

    protobuf(files("proto/"))
}

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")

    exclusiveContent {
        forRepository {
            maven {
                name = "sayandev"
                url = uri("https://repo.sayandev.org/snapshots")
            }
        }
        filter {
            includeGroup("org.sayandev")
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("kotlin")
            }
        }
    }
}
