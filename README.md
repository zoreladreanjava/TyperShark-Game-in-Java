# Typer Shark

A small Java Swing typing game created as an Object-Oriented Programming course project. Type each falling word and press **Space** before it reaches the bottom. Players start with three lives and earn 10 points for each correct word.

## Features

- Swing desktop interface
- Random words from a built-in word bank
- Score, high score, and three-life tracking
- A brief red input-field flash when a submitted word does not match
- Automatic game restart after game over

The high score is kept for the current application session and resets when the program closes. Word speed does not currently increase with score.

## Requirements

- Java Development Kit (JDK) 8 or newer

## Run from the command line

From the project root, compile the source files into `bin` and launch the game:

```bash
javac -d bin src/*.java
java -cp bin TyperShark
```

On Windows Command Prompt, use `javac -d bin src\*.java` for the compile command.

You can also open the folder in Visual Studio Code with the Java extensions installed. The project settings use `src` as the source folder and `bin` as the output folder.

## Controls

1. Type a word shown on screen into the input field.
2. Press **Space** to submit it.
3. Missed words cost one life. The game restarts automatically after game over.

## Project structure

```text
TyperSharkGame/
├── src/
│   ├── Game.java        # Game window, state, scoring, and input handling
│   ├── SharkWord.java   # A falling word and its animation
│   ├── TyperShark.java  # Application entry point
│   └── WordBank.java    # Built-in list of words
├── bin/                 # Compiled Java classes
├── .vscode/             # Visual Studio Code Java project settings
└── README.md
```

## Implementation notes

- `TyperShark` starts the interface on Swing's Event Dispatch Thread.
- `Game` manages the interface, score, lives, and spawning words.
- `SharkWord` represents and animates an individual falling word.
- `WordBank` provides the words used during play.
- Swing timers drive word spawning and falling animations.

## Possible improvements

- Save the high score between runs
- Add selectable difficulty levels
- Improve word placement to avoid overlap
- Add word categories, sound effects, or visual themes
