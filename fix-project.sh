#!/bin/bash
echo "Starting project fix..."

cp build.gradle build.gradle.backup 2>/dev/null
cp app/build.gradle app/build.gradle.backup 2>/dev/null

rm -f build.gradle.kts settings.gradle.kts 2>/dev/null

echo "include ':app'" > settings.gradle

cat > gradle.properties << 'EOF'
org.gradle.jvmargs=-Xmx2048m
android.useAndroidX=true
android.enableJetifier=true
EOF

cat > build.gradle << 'EOF'
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:4.2.2'
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
EOF

cat > app/build.gradle << 'EOF'
plugins {
    id 'com.android.application'
}

android {
    compileSdk 34
    namespace 'com.example.app'

    defaultConfig {
        applicationId "com.example.app"
        minSdk 21
        targetSdk 34
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }
    
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.9.0'
}
EOF

chmod +x gradlew

echo "Running build..."
./gradlew clean build --console=plain

if [ $? -eq 0 ]; then
    echo "BUILD SUCCESS!"
else
    echo "BUILD FAILED!"
fi
