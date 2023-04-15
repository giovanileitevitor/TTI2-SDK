# Telsel TTI - 2 - APP


The **Telsel TTI-2 APP** is designed for assist and support users from Telsel Telecom operator.

## Project Details:
- Designed in Kotlin/Java Language
- Designed with MVVM pattern and clean arch
- Designed with viewBinding and synthetics UI framework 

## Features:
- Home
- Avatar constructor/editor
- Navigation


## Instructions to Run/Compile app:
- Set Java SDK compiler to Android Studio Default JDK 11.0.13 (AndroidStudio >>> Preferencies >>> Gradle >>> Gradle JDK)
- Set Build Variant to Debug
- Set an External Device or Emulator with Android API higher > 23

## Instructions to Run Unit Tests:
    On Gradle Terminal, run 'gradle test --info' or './gradlew test'

## Instructions to Run Automated Tests with Expresso
    Not available yet

## Instructions to Run CI/CD pipeline manually
    Not available yet

## How to add JELAJAH-SDK into your android app
    In order to consume Yolo-SDk there are two ways to use it:
        1- Importing AAR file directly on build gradle.
            In this case, you need to  generate AAR file using gradle command ( ./gradle ttisdk:build )
       
     2- Importing all source code package from IDE using "Import Module" function on AS
            In this case, go to AS > File > New > Import Module > Source directory > and 
            enter the directory where you clone the package.
            OBS: dont use the same folder as you are working on main app.

## Compiler Instructions
    Gradle version: 6.7.1
    JDK version: 1.8
    Kotlin version: 1.3.72
    buildToolsVersion: 29.0.3

## Release Notes
    #version: 1.0.0
    #compiled_with: JDK 8

## More instructions:
    https://medium.com/swlh/generate-an-android-archive-aar-using-android-studio-and-add-to-an-android-project-b09ad9670ab7