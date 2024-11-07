plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinSymbolProcessing)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.nalldev.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)

    implementation(libs.androidx.datastore.preferences)

    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
}