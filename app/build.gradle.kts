plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.relay")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.hardus.trueagencyapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hardus.trueagencyapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

//        javaCompileOptions {
//            annotationProcessorOptions {
//                arguments = ("room.schemaLocation": "$projectDir/schemas".toString())
//            }
//        }
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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.camera:camera-lifecycle:1.3.3")
    implementation("androidx.media3:media3-common:1.3.1")
    val room_version = "2.6.1"

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation ("androidx.appcompat:appcompat:1.6.1")

    // room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    // coroutines support for room
    implementation("androidx.room:room-ktx:$room_version")

    implementation("androidx.compose.material:material-icons-extended")

    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha02")

    // Splash API
    implementation("androidx.core:core-splashscreen:1.0.1")

    //lottie
    implementation("com.airbnb.android:lottie-compose:4.0.0")

    // windows-screen-size
    implementation("androidx.compose.material3:material3-window-size-class")

    // navigation
    implementation("androidx.navigation:navigation-compose:2.7.5")

    // view-model
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra["lifecycle_version"]}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${rootProject.extra["lifecycle_version"]}")

    // pager and indicators - accompanist
    implementation("com.google.accompanist:accompanist-pager:${rootProject.extra["accompanist_version"]}")
    implementation("com.google.accompanist:accompanist-pager-indicators:${rootProject.extra["accompanist_version"]}")

    // datastore-preferences
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.48.1")
    implementation("com.google.firebase:firebase-database-ktx:20.3.0")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")
    kapt("androidx.hilt:hilt-compiler:1.2.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    //QR CODE by ZXING
    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation ("com.google.zxing:core:3.4.1")

    // firebase
    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation ("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation("com.google.firebase:firebase-firestore-ktx")

    //swap refresh
    implementation ("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")

    implementation ("com.karumi:dexter:6.2.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}