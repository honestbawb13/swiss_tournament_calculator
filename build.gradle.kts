plugins {
    // Versions resolved by top-level Gradle; users can adjust as needed
    kotlin("android") version "1.9.24" apply false
    id("com.android.application") version "8.5.2" apply false
    id("com.android.library") version "8.5.2" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    kotlin("kapt") version "1.9.24" apply false
}

tasks.register<Delete>("clean") {
    // Use the Provider API instead of deprecated buildDir getter
    delete(layout.buildDirectory)
}
