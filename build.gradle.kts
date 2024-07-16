// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.5.21" apply false
}