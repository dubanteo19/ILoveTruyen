plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ilovetruyen"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ilovetruyen"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.recyclerview)
    implementation(libs.navigation.fragment)
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation(libs.cardview)
    implementation(libs.navigation.ui)
    implementation(libs.activity)
    implementation("com.github.IslamKhSh:CardSlider:1.0.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //gslide dependency
    implementation( "com.github.bumptech.glide:glide:3.7.0")
    //NOTE : không sử dụng pack android:support v7 design, pallet,... --> lỗi version
}