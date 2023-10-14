plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.carmechconnect"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.carmechconnect"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //OSM
    implementation ("org.osmdroid:osmdroid-android:6.1.10")
    implementation ("org.osmdroid:osmdroid-mapsforge:6.1.10")
    implementation ("androidx.preference:preference:1.2.1")
    implementation ("com.github.MKergall:osmbonuspack:6.9.0")
    implementation ("org.apache.commons:commons-lang3:3.8.1")
    implementation ("com.google.code.gson:gson:2.8.9")
    implementation ("com.squareup.okhttp3:okhttp:4.10.0")

    //Rive
    implementation ("app.rive:rive-android:5.0.0")
    implementation ("androidx.startup:startup-runtime:1.1.1")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

//    // Retrofit with Scalar Converter
//    implementation ("com.squareup.retrofit2:converter-scalars:2.9.0")
//
//    //Retrofit with xml convertor
//    implementation ("com.squareup.retrofit2:converter-simplexml:2.9.0")

    // Moshi
    implementation ("com.squareup.moshi:moshi-kotlin:1.9.3")

    // Retrofit with Moshi Converter
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
//
//    //lifecycle
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
//
//    //couroutines
//    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
//
//    //coil
//    implementation ("io.coil-kt:coil:2.2.2")
}