// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    id( "org.jetbrains.kotlin.jvm") version "1.9.0"
    id("com.google.devtools.ksp") version "2.0.0-1.0.21"
    alias(libs.plugins.hilt) apply false

}