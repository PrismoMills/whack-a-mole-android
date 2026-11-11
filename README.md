# Whack-a-Mole for Android

A short arcade game for Android. Tap the mole before it ducks back underground and clear all three rounds before time runs out.

## How to play

1. Tap the title screen to reveal the animated menu, then choose **Start game**.
2. Hit 10 moles in 30 seconds to clear each round. The score resets to **0 / 10** each round, and the mole moves faster as you advance.
3. Choose **Next level** after clearing a round. After a timeout or the final round, choose **Play again** or **Exit to home**.

The game pauses when you switch apps and resumes when you return.

## Run locally

Open the repository in Android Studio, use JDK 17 and Android SDK Platform 34, and run the `app` module on an emulator or device with Android 7.0 (API 24) or newer.

To build a debug APK, run `.\gradlew.bat assembleDebug` on Windows or `sh ./gradlew assembleDebug` on macOS or Linux. Find it at `app/build/outputs/apk/debug/app-debug.apk`.

## Implementation

Written in Java with native Android views. The title animation, burrows, mole, mallet, and result illustrations are drawn with Android Canvas. The game runs offline.
