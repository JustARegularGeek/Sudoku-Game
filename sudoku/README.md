# Sudoku Game in Java

This repository contains two implementations of Sudoku in Java: 
1. A **JavaFX GUI version** 
2. A **terminal-based version**. 

Both versions are launched via the `Sudoku.java` class, which asks the user whether they want to play the graphical or terminal game.

## How It Works

1. Run `Sudoku.java`.
2. You will be prompted to select the game mode:
   - **GUI**: Play using the JavaFX graphical interface.
   - **Terminal**: Play in the console.
3. For both versions, you will then select:
   - Board size: 4x4 or 9x9
   - Difficulty level: Easy, Medium, or Hard

## Sudoku GUI Game

A graphical Sudoku game using JavaFX.

### Features
- 4x4 and 9x9 boards
- Multiple difficulty levels (Easy, Medium, Hard)
- Ending scene when puzzle is solved
- Non-resizable, non-fullscreen window for consistent layout

## Terminal Sudoku Game

A text-based version playable in the console.

### Features
- Play 4x4 or 9x9 Sudoku in terminal
- Difficulty levels: Easy, Medium, Hard
- Fixed cells displayed in parentheses
- Invalid moves are rejected with an error message
- Game ends when the board is correctly filled

## Requirements
- Java 21
- JavaFX 21(for GUI version)

