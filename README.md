# JBubbleBobble

JBubbleBobble is a desktop arcade game inspired by *Bubble Bobble*, developed in Java as a Programming Methodologies project for the Bachelor's degree in Computer Science at Sapienza University of Rome. It recreates the bubble-trapping gameplay in a Swing interface and includes eight levels.
## Gameplay
Move around each level, trap enemies in bubbles, and clear the stage to advance. The game tracks lives and score, and includes enemies, items, and power-ups.
| Key | Action |
| --- | --- |
| Left / Right | Move horizontally |
| Up | Jump |
| Down | Blow a bubble / attack |
From the main menu, you can set a player name (up to four characters), choose from five avatars, start a game, or view the rankings. Rankings can be sorted by name, level, games played, wins, total score, or best score. Player rankings and statistics are stored locally in `Ranking.txt`.
## Requirements
- Java Development Kit (JDK) 22
- A desktop environment capable of running Java Swing
The repository includes Eclipse project metadata (`.project` and `.classpath`) and has no Maven or Gradle build file.
## Build and run
From the repository root in Windows PowerShell, compile the Java sources:
```powershell
$sources = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d bin $sources
```
Run the game from `bin` so the relative paths to `resources` and `Ranking.txt` resolve correctly. Include `src` on the runtime classpath for the image resources loaded relative to the view packages:
```powershell
Push-Location bin
java -cp ".;..\src;.." JBubbleBobble
Pop-Location
```
In Eclipse, import the repository as an existing Java project, configure JDK 22, and run `JBubbleBobble` as a Java application. Set the run configuration's working directory to the project's `bin` directory and include the `src` folder on its runtime classpath.
## Project layout
```text
src/
  JBubbleBobble.java       Application entry point
  constants/               Shared game constants
  controller/              Game loop, input, audio, buttons, and rankings
  model/                   Player, enemies, bubbles, items, and eight levels
  view/                    Swing windows, panels, and entity rendering
resources/                 Sprites, backgrounds, fonts, and audio
Ranking.txt                Local player ranking and statistics
doc/                       Generated Java API documentation
```
The code separates the model, view, and controller responsibilities. The entry point creates the main Swing frame and application controller. Open `doc/index.html` in a browser to browse the generated API documentation.
