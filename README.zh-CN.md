# Android 打地鼠

**使用 Java、Android 原生视图和 Canvas 构建的离线街机小游戏。**

玩家需要在倒计时结束前打中十只地鼠，再挑战速度逐步提升的三轮游戏。点击标题画面后进入动画菜单，从九宫格地洞开始游戏，每轮结束后显示结算画面。

## 可以做什么

- 点击标题画面展开动画菜单，开始游戏。
- 在三乘三的地洞中点击当前出现的地鼠。
- 挑战三轮游戏，每轮分别计分和计时。
- 过关后进入下一关，或选择重新开始、返回首页。
- 切换到其他应用后，返回继续尚未结束的一轮。

## 每轮如何进行

每轮限时 30 秒，打中十次即可过关。进入下一关时，分数重置为 **0 / 10**，地鼠的移动间隔依次从 1,150 毫秒缩短到 1,000 和 850 毫秒。

只有点中当前地鼠、且剩余时间大于零时才计分。下次出现会换一个地洞。达到十次命中后过关，倒计时归零则进入结算画面。应用切到后台时暂停计时和地鼠移动。

## 本地运行

需要 Android Studio、JDK 17 和 Android SDK Platform 34。在 Android 7.0（API 24）及以上的设备或模拟器上运行 `app` 模块。

Windows 下运行以下命令构建 Debug APK。

```powershell
.\gradlew.bat assembleDebug
```

macOS 或 Linux 下运行 `sh ./gradlew assembleDebug`。APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。

### 试玩流程

1. 点击标题画面，再选择 **Start game**。
2. 在倒计时结束前命中十次。
3. 过关后选择 **Next level**。
4. 超时或完成最后一关后，选择 **Play again** 或 **Exit to home**。

## 项目结构

```text
app/src/main/java/com/nine/project_2_finalver/
  MainActivity.java   关卡状态、计分、计时与页面切换
  BurrowView.java     地鼠、地洞和命中效果
  HomeArtwork.java    标题动画插画
  ResultArtwork.java  结算插画
app/src/main/res/     布局、主题与启动图标资源
```

## 当前范围

当前版本提供三轮离线单人游戏。标题动画、地洞、地鼠、木槌和结算插画由 Android Canvas 绘制。游戏无需账号或共享服务器。

[English](README.md)
