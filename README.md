<div align="center">

<h1>Whack-a-Mole</h1>
<p><strong>9 holes. 3 rounds. 10 hits to clear each one.</strong></p>
<p>A quick offline arcade game for Android, drawn with Canvas.</p>

<p>
<img alt="Android 7.0 or newer" src="https://img.shields.io/badge/Android-7.0%2B-3DDC84?logo=android&logoColor=white">
<img alt="Java" src="https://img.shields.io/badge/Code-Java-E76F00">
<img alt="Offline single player" src="https://img.shields.io/badge/Play-Offline-668459">
</p>

<p><a href="#pick-up-and-play">Play</a> · <a href="#three-rounds-a-faster-mole">Rounds</a> · <a href="#build-your-own-copy">Build</a> · <a href="README.zh-CN.md">中文</a></p>

</div>

---

Tap the mole before it moves, reach ten hits, and take on the next round. The score starts fresh each time, but the mole gets quicker.

## Pick up and play

1. Tap the title screen to reveal the animated menu.
2. Choose **Start game** and find the mole on the three-by-three board.
3. Reach **10 / 10** before the 30-second countdown ends.
4. Choose **Next level**, or use **Play again** and **Exit to home** after the final round or a timeout.

Switching to another app pauses the round. Return to continue with the remaining time.

## Three rounds, a faster mole

| Round | Time | Goal | Movement interval |
| --- | --- | --- | --- |
| 1 | 30 seconds | 10 hits | 1,150 ms |
| 2 | 30 seconds | 10 hits | 1,000 ms |
| 3 | 30 seconds | 10 hits | 850 ms |

A hit counts when you tap the active mole while time remains. The next appearance uses a different hole. Each cleared round opens a result screen, and the next round begins when you choose to continue.

## Build your own copy

Open the project in Android Studio with **JDK 17** and **Android SDK Platform 34**. Run the `app` module on Android 7.0, API 24, or newer.

On Windows, build the debug APK using this command.

```powershell
.\gradlew.bat assembleDebug
```

On macOS or Linux, run `sh ./gradlew assembleDebug`.

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`.

<details>
<summary><strong>How the game is built</strong></summary>

The game uses Java and native Android views. Android Canvas draws the animated title screen, burrows, mole, mallet and result artwork.

| Class | Role |
| --- | --- |
| `MainActivity.java` | Round state, timing, scoring and navigation |
| `BurrowView.java` | The board's mole and hit artwork |
| `HomeArtwork.java` | Animated title illustration |
| `ResultArtwork.java` | Round result illustration |

These classes are in `app/src/main/java/com/nine/project_2_finalver/`. Layouts, themes and launcher resources are in `app/src/main/res/`.

</details>

The current game is three-round, single-player and playable offline. No account or shared server is required.
