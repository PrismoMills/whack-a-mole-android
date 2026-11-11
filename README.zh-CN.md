# Android 打地鼠

一款三关制的 Android 反应小游戏。趁地鼠钻回洞里之前点中它，在倒计时结束前完成每关目标。

## 玩法

1. 点击标题画面播放开场动画，再点 **Start game** 开始。
2. 每关限时 30 秒，打中 10 次即可过关。进入下一关时分数从 **0 / 10** 重新开始，地鼠移动速度逐关加快。
3. 过关后点 **Next level** 继续。超时或完成最后一关后，可选择 **Play again** 重开，或 **Exit to home** 返回首页。

切到后台时游戏暂停，回到应用后继续。

## 本地运行

用 Android Studio 打开仓库，配置 JDK 17 和 Android SDK Platform 34，在 Android 7.0（API 24）及以上的设备或模拟器运行 `app` 模块。

Windows 下运行 `.\gradlew.bat assembleDebug`，macOS 或 Linux 下运行 `sh ./gradlew assembleDebug`。调试 APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。

## 实现

使用 Java 和 Android 原生视图。开场动画、地鼠、洞口、木槌与结算插画都由 Android Canvas 绘制。游戏可离线运行。
