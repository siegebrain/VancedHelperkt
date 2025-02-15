import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
}

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    val kotlinVersion = "1.6.10"
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))

    implementation("dev.kord:kord-core:0.8.0-M9")

    implementation("org.apache.commons:commons-math3:3.6.1")

    implementation("org.litote.kmongo:kmongo:4.4.0")

    val logbackVersion = "1.2.10"
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("ch.qos.logback:logback-core:$logbackVersion")

    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-gson:$retrofitVersion")

    implementation("io.insert-koin:koin-core:3.1.5")

    implementation("org.reflections:reflections:0.10.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Jar> {
    archiveFileName.set("bot.jar")

    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    exclude("META-INF/*.RSA", "META-INF/*.DSA", "META-INF/*.SF")

    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }

    configurations["runtimeClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

application {
    mainClass.set("MainKt")
}