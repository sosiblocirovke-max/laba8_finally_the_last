// Модуль приложения: Android + Kotlin + kapt + Hilt.
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    // Пространство имён для R/BuildConfig и ссылок в манифесте без полного пакета.
    namespace = "com.example.todoapp"
    // compileSdk 34 — требование задания (API 34 для компиляции).
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todoapp"
        // minSdk 24 — поддержка устройств с API 24+.
        minSdk = 24
        // targetSdk 34 — целевая версия платформы для поведения системы.
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // Java 17 — байткод и совместимость с toolchain.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    // Kotlin компилируется в JVM 17 (должно совпадать с compileOptions).
    kotlinOptions {
        jvmTarget = "17"
    }
    // Включаем Jetpack Compose в модуле.
    buildFeatures {
        compose = true
    }
    // Версия Compose Compiler, согласованная с Kotlin (см. libs.versions.toml).
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    // BOM фиксирует согласованные версии артефактов androidx.compose.*.
    implementation(platform(libs.androidx.compose.bom))
    // Core / Lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose UI + Material 3 + интеграция с Activity.
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    // Навигация в Compose.
    implementation(libs.androidx.navigation.compose)
    // Room: runtime + корутины-обёртки; compiler через kapt.
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    // Hilt DI + интеграция с Navigation Compose; процессор через kapt.
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    // Корутины на Android (Main dispatcher и т.д.).
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
