
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.aboutlibraries.plugin)
}


android {
    namespace = "com.devhjs.mysmokinglog"
    compileSdk {
        version = release(36)
    }


    defaultConfig {
        applicationId = "com.devhjs.mysmokinglog"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "1.2.0"

        testInstrumentationRunner = "com.devhjs.mysmokinglog.CustomTestRunner"
    }

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            manifestPlaceholders["adMobAppId"] = "ca-app-pub-3940256099942544~3347511713"
            buildConfigField("String", "ADMOB_BANNER_ID", "\"ca-app-pub-3940256099942544/6300978111\"")
            buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"ca-app-pub-3940256099942544/1033173712\"") // Google Test Interstitial ID
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            manifestPlaceholders["adMobAppId"] = localProperties.getProperty("ADMOB_APP_ID") ?: ""
            buildConfigField("String", "ADMOB_BANNER_ID", "\"${localProperties.getProperty("ADMOB_BANNER_ID")}\"")
            buildConfigField("String", "ADMOB_INTERSTITIAL_ID", "\"${localProperties.getProperty("ADMOB_INTERSTITIAL_ID")}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)
    
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Navigation
    implementation(libs.navigation.compose)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // AdMob
    implementation(libs.play.services.ads)

    // AboutLibraries
    implementation(libs.aboutlibraries.compose)
    
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")
}