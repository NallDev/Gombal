plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSymbolProcessing)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    id("kotlin-parcelize")
}

android {
    namespace = "com.nalldev.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "BASE_URL", "\"https://www.arbeitnow.com/api\"")
        buildConfigField("String", "HOSTNAME", "\"arbeitnow.com\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)
    api(libs.androidx.activity)
    api(libs.androidx.constraintlayout)
    api(libs.junit)
    api(libs.androidx.junit)
    api(libs.androidx.espresso.core)

    api(libs.androidx.lifecycle.viewmodel.ktx)
    api(libs.coil)

    api(platform(libs.koin.bom))
    api(libs.koin.android)

    api(libs.androidx.room.runtime)
    api(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    api(libs.android.database.sqlcipher)
    api(libs.androidx.sqlite.ktx)

    api(platform(libs.ktor.bom))
    api(libs.ktor.client.okhttp)
    api(libs.ktor.client.serialization)
    api(libs.ktor.client.logging)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.serialization.kotlinx.json)
    api(libs.logging.interceptor)

    api(libs.androidx.datastore.preferences)

    api(libs.leakcanary.android)
    api(libs.androidx.biometric)
}