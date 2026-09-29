
# 🦈 Typer Shark Game - School Project

## 🎯 Project Description

This is an educational typing game developed as part of our Object-Oriented Programming course. The game challenges players to type falling words before they reach the bottom of the screen, helping to improve typing speed and accuracy while demonstrating core Java programming concepts.

## 📋 Learning Objectives

This project demonstrates understanding of:

-   ✅ Object-Oriented Programming principles
    
-   ✅ Java Swing for GUI development
    
-   ✅ Event handling and listeners
    
-   ✅ Multi-threading and timers
    
-   ✅ Collections and data structures
    
-   ✅ Game development fundamentals
    
-   ✅ Code organization and documentation
    

## 🏗️ Project Structure

```text

TyperSharkProject/
├── src/
│   ├── TyperShark.java          # Main class - application entry point
│   ├── Game.java                # Main game logic and UI (JFrame)
│   ├── SharkWord.java           # Falling word object (JLabel)
│   └── WordBank.java            # Word database utility class
└── README.md                    # This file
```
## 📖 Class Documentation

### `TyperShark.java`

**Purpose:** Main driver class  
**Responsibilities:**

-   Program entry point
    
-   Launch application using EventQueue
    
-   Ensure thread-safe GUI initialization
    

**Key Methods:**

-   `main(String[] args)` - Starts the application
    

### `Game.java` (extends JFrame)

**Purpose:** Main game controller and UI  
**Responsibilities:**

-   Manage game window and components
    
-   Handle game state and logic
    
-   Coordinate between different game elements
    
-   Manage scoring and life system
    

**Key Methods:**

-   `enterInput()` - Process user typed words
    
-   `spawnWord()` - Create new falling words
    
-   `wordMissed()` - Handle missed words
    
-   `gameOver()` - End game logic
    

### `SharkWord.java` (extends JLabel)

**Purpose:** Represent falling words  
**Responsibilities:**

-   Animate word movement
    
-   Handle word state (active/inactive)
    
-   Detect when words reach bottom
    

**Key Methods:**

-   `start()` - Begin word animation
    
-   `match()` - Check if input matches word
    
-   `fall()` - Update word position
    

### `WordBank.java`

**Purpose:** Data storage utility  
**Responsibilities:**

-   Store available words for the game
    
-   Provide random word selection
    

## 🛠️ Technical Implementation

### Core Technologies Used

-   **Java 17** - Programming language
    
-   **Swing** - GUI framework
    
-   **AWT** - Event handling
    
-   **Swing Timer** - Game animation
    

### Key Programming Concepts Demonstrated

1.  **Inheritance:**  `Game extends JFrame`, `SharkWord extends JLabel`
    
2.  **Encapsulation:** Private fields with public getters/setters
    
3.  **Polymorphism:** Method overriding in Swing components
    
4.  **Collections:**  `ArrayList`, `CopyOnWriteArrayList` for word management
    
5.  **Event Handling:** Action listeners for user input
    
6.  **Multi-threading:** Swing Timer for smooth animations
    
7.  **Exception Handling:** Try-catch blocks for robust code
    

## 🎮 How to Compile and Run

### Prerequisites

-   Java Development Kit (JDK) 17 or higher
    
-   Any Java IDE (Eclipse, IntelliJ, NetBeans) or command line
    

### Compilation Instructions

1.  **Using Command Line:**
    
   ``` bash
    cd desktop
    javac *.java
    java TyperShark
   ```
2.  **Using IDE:**
    
    -   Create new Java project
        
    -   Add all .java files to source folder
        
    -   Run `TyperShark.java` as main class
        

### Game Controls

-   **Type** words in the input field
    
-   **Press Enter** to submit
    
-   Game automatically restarts after game over
    

## 📊 Features Implemented

### Core Requirements ✅

-   Graphical User Interface using Swing
    
-   Interactive game mechanics
    
-   Score tracking system
    
-   Life management (3 lives)
    
-   Dynamic word spawning
    
-   Collision detection (words reaching bottom)
    

### Advanced Features ✅

-   Progressive difficulty (speed increases with score)
    
-   High score persistence during session
    
-   Visual feedback for user actions
    
-   Proper game state management
    
-   Thread-safe GUI updates
    

## 🔧 Code Quality

### Documentation

-   Clear class and method documentation
    
-   Inline comments explaining complex logic
    
-   Consistent code formatting
    

### Design Patterns

-   **MVC Pattern:** Separation of data (WordBank), view (Swing components), and controller (Game class)
    
-   **Observer Pattern:** Event listeners for user input
    
-   **Factory Pattern:** Word creation and management
    

## 🐛 Challenges Overcome

1.  **Threading Issues:** Resolved conflicts between Swing thread and game logic
    
2.  **Word Positioning:** Implemented algorithms to prevent word overlap
    
3.  **Game State Management:** Proper handling of game restart and session management
    
4.  **Performance Optimization:** Efficient word spawning and cleanup
    

## 🚀 Possible Enhancements

### For Academic Purposes

-   Add difficulty levels selection
    
-   Implement word categories (technical terms, common words)
    
-   Create a settings configuration file
    
-   Add sound effects for better user experience
    

### For Further Study

-   Database integration for persistent high scores
    
-   Network multiplayer functionality
    
-   Advanced animation effects
    
-   Custom theme support
    

## 📝 Academic Usage

This project is suitable for:

-   Object-Oriented Programming courses
    
-   Java programming assignments
    
-   GUI development projects
    
-   Game development introductions
---
---
---
### 🚫 Why We Removed Runnable/Threads:

### 1. **Thread Safety Issues with Swing**

```java
// OLD VERSION - Problematic:
public class Game extends JFrame implements Runnable {
    public void run() {
        while (true) {
            // This runs in separate thread, but updates UI components
            // This can cause race conditions and visual glitches
        }
    }
}
```

### 2. **Swing is Not Thread-Safe**

-   Swing components should only be updated from the **Event Dispatch Thread (EDT)**
    
-   Manual threads can cause:
    
    -   Visual artifacts
        
    -   Crashes
        
    -   Inconsistent UI state
        
    -   Race conditions
        

### 3. **Swing Timer is the Proper Solution**

```java
// NEW VERSION - Thread-safe:
private void startGame() {
    gameTimer = new javax.swing.Timer(1000, e -> spawnWord());
    gameTimer.start(); // This automatically runs on EDT
}
```
### 4. **Cleaner Code Structure**

**Before (Complex):**

```java
public void run() {
    while (true) {
        // Complex game loop
        // Manual thread sleeping
        // Explicit SwingUtilities.invokeLater() calls needed
    }
}
```
**After (Simple):**

```java
private void spawnWord() {
    // Automatically called on EDT - no threading concerns
    // Clean, focused method
}
```
## 🔧 Technical Benefits of Using Swing Timer:

### **Automatic EDT Compliance**

```java
gameTimer = new javax.swing.Timer(1000, e -> {
    // This actionPerformed runs on EDT automatically
    spawnWord(); // Safe UI updates
});
```
### **Better Resource Management**

```java
// Easy to stop/start
gameTimer.stop();    // Pause game
gameTimer.start();   // Resume game

// VS manual threads:
thread.interrupt();  // More complex, can leave resources
```
### **Simplified Game State**

No need to manage:

-   Thread interrupts
    
-   Volatile variables for communication
    
-   Complex synchronization
    
-   Potential deadlocks
    

## 🎯 Educational Perspective:

For a school project, using `Swing Timer` demonstrates:

-   ✅ Understanding of Swing threading model
    
-   ✅ Proper use of framework-provided solutions
    
-   ✅ Cleaner, more maintainable code
    
-   ✅ Industry best practices
    

## ⚡ When You Would Use Runnable/Threads:

You'd only use manual threads for:

-   Heavy background computations
    
-   File I/O operations
    
-   Network operations
    
-   Database queries
    
-   **NOT** for Swing UI updates
    

## 📚 Summary:

The change from `Runnable` to `Swing Timer` represents:

-   **Better practice** for Swing applications
    
-   **More reliable** game behavior
    
-   **Cleaner architecture**
    
-   **Proper use of framework features**