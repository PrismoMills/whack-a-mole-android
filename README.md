# Whack-a-Mole for Android

**An offline arcade game built with Java, native Android views and Canvas.**

Whack-a-Mole challenges players to hit ten moles before the timer expires, then continue through three increasingly fast rounds. An animated title screen leads into a nine-hole board, with a result screen after each round.

## What you can do

- Reveal the animated menu from the title screen and start a game.
- Tap the active mole on a three-by-three board.
- Clear three rounds with a separate score and countdown for each round.
- Continue to the next level, replay the game or return to the home screen.
- Switch apps and return to resume an unfinished round.

## How a round works

Each round gives the player 30 seconds to score ten hits. The score resets to **0 / 10** at the start of the next round, while the mole's movement interval decreases from 1,150 to 1,000 and then 850 milliseconds.

A hit counts only when the selected hole contains the active mole and time remains. The next appearance uses a different hole. Reaching ten hits clears the round; running out of time opens the result screen. The game pauses its timer and movement while in the background.

## Run locally

Requirements are Android Studio, JDK 17 and Android SDK Platform 34. Run the `app` module on an emulator or device with Android 7.0 (API 24) or newer.

Build a debug APK on Windows.

```powershell
.\gradlew.bat assembleDebug
```

On macOS or Linux, run `sh ./gradlew assembleDebug`. The APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

### Try a game

1. Tap the title screen, then choose **Start game**.
2. Hit ten moles before the countdown ends.
3. Choose **Next level** after clearing a round.
4. After a timeout or the final round, choose **Play again** or **Exit to home**.

## Project layout

```text
app/src/main/java/com/nine/project_2_finalver/
  MainActivity.java   Round state, scoring, timing and navigation
  BurrowView.java     Mole board and hit artwork
  HomeArtwork.java    Animated title illustration
  ResultArtwork.java  Round result illustration
app/src/main/res/     Layouts, themes and launcher resources
```

## Current scope

This version provides three offline single-player rounds. The title animation, burrows, mole, mallet and result illustrations are drawn with Android Canvas. The game requires no account or shared server.

[中文说明](README.zh-CN.md)
