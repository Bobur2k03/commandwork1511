plugins {
    id("com.android.application")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.command_work_1511" // Это ID приложения
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Укажите namespace здесь:
        namespace = "com.example.command_work_1511"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    viewBinding {
        enable = true
    }
}

dependencies {
    // Основные библиотеки Android
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // SQLite для работы с базами данных
    implementation("androidx.sqlite:sqlite:2.3.1")

    // Для отображения списков
    implementation("androidx.recyclerview:recyclerview:1.3.1")

    // Библиотеки для тестирования
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
