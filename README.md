當然可以 👍
下面是**完整中文版本 README.md**，你可以直接複製覆蓋原本的 README。

---

# 📄 README.md（中文版本）

```markdown
# 貪食蛇（Swing 視窗版）

這是一個使用純 Java 開發的貪食蛇遊戲。

專案採用「核心邏輯與 UI 分離」的設計方式：

- `snake.core` → 遊戲邏輯（引擎、狀態、規則）
- `snake.ui` → Swing 視窗畫面
- `snake.app` → 程式進入點

核心邏輯不依賴任何 UI 框架，因此可輕鬆改成：
- Console 版本
- JavaFX 版本
- LibGDX 版本
- 其他圖形框架

---

## 🎮 功能特色

- 網格型移動系統
- 方向緩衝（避免瞬間反向）
- 撞牆 / 撞到自己判定
- 食物隨機生成
- 分數計算
- Swing 視窗即時渲染

---

## 📂 專案結構

```

src/
snake/
app/
SwingMain.java
core/
GameEngine.java
GameState.java
Snake.java
Direction.java
FoodSpawner.java
Position.java
GameConfig.java
ui/
SwingRendererPanel.java

````

---

## 🛠 編譯方式

在專案根目錄開啟 PowerShell：

```powershell
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse -Filter *.java src | % { $_.FullName })
````

---

## ▶ 執行方式（Swing 視窗版）

```powershell
java -cp out snake.app.SwingMain
```

---

## ⌨ 操作方式

| 按鍵    | 功能 |
| ----- | -- |
| W 或 ↑ | 向上 |
| S 或 ↓ | 向下 |
| A 或 ← | 向左 |
| D 或 → | 向右 |

---

## 📜 遊戲規則

* 吃到 `*` 會變長並加分
* 撞到牆壁會遊戲結束
* 撞到自己會遊戲結束

---

## 🧠 設計說明

* `GameEngine` 先計算 `nextHead`
* 再做碰撞判定
* 最後才提交移動：

    * `snake.addHead(nextHead)`
    * 若未吃到食物 → `snake.removeTail()`

這樣可以確保：

* 碰撞判定與實際移動一致
* 邏輯清晰可測試
* UI 層與核心邏輯完全分離

---

## 🚀 未來可擴充方向

* 重新開始功能
* 暫停功能
* 彩色方塊渲染（使用 fillRect）
* 勝利條件（蛇填滿整個地圖）
* 改寫成 LibGDX 版本
* 撰寫單元測試

---

## 📌 授權

僅供學習與個人使用。

```
