//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
//    id("com.google.devtools.ksp")
//}
//
//android {
//    namespace = "com.example.airconditioner"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.airconditioner"
//        minSdk = 34
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//        buildConfig = true
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//
//    implementation(libs.androidx.lifecycle.viewmodel.ktx)
//    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.runtime.livedata)
//    implementation(libs.androidx.constraintlayout)
//    ksp(libs.androidx.room.compiler)
//
//    implementation(libs.androidx.navigation.compose)
//    implementation(libs.coil.compose)
//
//    implementation(libs.androidx.constraintlayout.compose)
//    implementation(libs.androidx.compose.material3)
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
//
//    // CameraX
//    implementation(libs.androidx.camera.core)
//    implementation(libs.androidx.camera.camera2)
//    implementation(libs.androidx.camera.lifecycle)
//    implementation(libs.androidx.camera.view)
//    implementation(libs.androidx.camera.extensions)
//
//    // Accompanist Permissionsライブラリ
//    implementation(libs.accompanist.permissions)
//
//    // ML Kit Barcode Scanning
//    //implementation("com.google.mlkit:barcode-scanning:17.3.0")
//}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.devtools.ksp")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.websarva.wings.android.rental"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.websarva.wings.android.rental"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.runtime.livedata)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)

    implementation(libs.androidx.compose.material3)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // CameraX
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)

    // Accompanist Permissionsライブラリ
    implementation(libs.accompanist.permissions)

    // ML Kit Barcode Scanning
    implementation(libs.barcode.scanning)

    implementation(libs.commons.net)

    implementation(libs.gson)

}