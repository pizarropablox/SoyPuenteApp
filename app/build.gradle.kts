plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.duoc.soypuenteapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.duoc.soypuenteapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)

    // Dependencias para pruebas unitarias
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-core:3.11.2") // Mockito para pruebas unitarias
    testImplementation("androidx.arch.core:core-testing:2.1.0") // InstantTaskExecutorRule

    // Dependencias para pruebas instrumentadas de UI con Compose
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4) // Pruebas de UI de Compose

    // Para herramientas de depuración y pruebas en tiempo de ejecución
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Si estás usando el LiveData en tus ViewModel
    testImplementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.1")
    testImplementation("androidx.lifecycle:lifecycle-runtime-testing:2.4.1") // Para probar LiveData

    // Pruebas de ViewModel
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")

}
