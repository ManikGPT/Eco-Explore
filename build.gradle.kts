// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.google.gms:google-services:4.3.8")
        classpath ("com.android.tools.build:gradle:7.3.1")

    }

}
allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}
