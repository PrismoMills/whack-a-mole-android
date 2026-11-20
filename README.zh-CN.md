<div align="center">

<h1>Android 打地鼠</h1>
<p><strong>9 个地洞，3 轮挑战，每轮打中 10 次。</strong></p>
<p>一款由 Canvas 绘制、可以离线玩的 Android 街机小游戏。</p>

<p>
<img alt="Android 7.0 及以上" src="https://img.shields.io/badge/Android-7.0%2B-3DDC84?logo=android&logoColor=white">
<img alt="Java" src="https://img.shields.io/badge/Code-Java-E76F00">
<img alt="离线单人" src="https://img.shields.io/badge/Play-Offline-668459">
</p>

<p><a href="#打开就能玩">玩法</a> · <a href="#三轮挑战速度递增">关卡</a> · <a href="#构建并运行">构建</a> · <a href="README.md">English</a></p>

</div>

---

趁地鼠换洞前点中它，累计十次命中，再挑战下一轮。每轮重新计分，地鼠也会跑得更快。

## 打开就能玩

1. 点击标题画面，展开动画菜单。
2. 选择 **Start game**，在九宫格里找到地鼠。
3. 在 30 秒倒计时结束前达到 **10 / 10**。
4. 过关后选择 **Next level**。超时或完成最后一轮后，可以 **Play again** 或 **Exit to home**。

切换到其他应用时，本轮暂停。返回后继续使用剩余时间。

## 三轮挑战，速度递增

| 关卡 | 时间 | 目标 | 地鼠移动间隔 |
| --- | --- | --- | --- |
| 1 | 30 秒 | 命中 10 次 | 1,150 毫秒 |
| 2 | 30 秒 | 命中 10 次 | 1,000 毫秒 |
| 3 | 30 秒 | 命中 10 次 | 850 毫秒 |

剩余时间大于零且点中当前地鼠时才计分，下次出现会换一个地洞。每轮结束后显示结算画面，选择继续后才开始下一轮。

## 构建并运行

用 Android Studio 打开项目，配置 **JDK 17** 和 **Android SDK Platform 34**，在 Android 7.0（API 24）及以上的设备或模拟器运行 `app` 模块。

Windows 下运行以下命令。

```powershell
.\gradlew.bat assembleDebug
```

macOS 或 Linux 下运行 `sh ./gradlew assembleDebug`。调试 APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。

<details>
<summary><strong>游戏是怎样实现的</strong></summary>

使用 Java 和 Android 原生视图，标题动画、地洞、地鼠、木槌和结算插画由 Android Canvas 绘制。

| 类 | 用途 |
| --- | --- |
| `MainActivity.java` | 关卡状态、计时、计分与页面切换 |
| `BurrowView.java` | 地鼠、地洞与命中效果 |
| `HomeArtwork.java` | 标题动画插画 |
| `ResultArtwork.java` | 结算插画 |

这些类位于 `app/src/main/java/com/nine/project_2_finalver/`，布局、主题和启动图标在 `app/src/main/res/`。

</details>

当前版本提供三轮离线单人游戏，无需账号或共享服务器。
