plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.superhero"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.superhero"
        minSdk = 24
        targetSdk = 36
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
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // ViewModel 测试
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    // Coroutines 测试
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // Paging 测试
    testImplementation("androidx.paging:paging-common:3.2.1")
    testImplementation("androidx.paging:paging-testing:3.2.1")
    // Mock 框架
    testImplementation("org.mockito:mockito-core:5.8.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    // JUnit
    testImplementation("junit:junit:4.13.2")

    // Retrofit 核心库
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Gson 解析器（将返回的 JSON 转为 Kotlin 数据类）
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // 协程适配（支持 suspend 函数）
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    // 可选：日志拦截器（调试时打印网络请求日志）
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Kotlin 协程核心（Android 工程）
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    // 1. ViewModel 核心依赖（必须）
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    // 2. Compose + ViewModel 适配依赖（关键！缺失会导致 viewModel() 找不到）
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Paging 3 核心（Compose 适配）
    implementation("androidx.paging:paging-runtime:3.2.1")
    implementation("androidx.paging:paging-compose:3.2.1")

    // 下拉刷新
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.compose.material:material:1.5.4")
    // Coil 图片加载（Compose 版）
    implementation("io.coil-kt:coil-compose:2.4.0")
}