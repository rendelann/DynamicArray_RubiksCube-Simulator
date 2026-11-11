# Dynamic Array Implementation for Rubik’s Cube Simulator

A Java-based Rubik’s Cube Simulator that demonstrates the implementation of a **Dynamic Array** to store and manage move history. The project shows how a Dynamic Array automatically increases its capacity as more moves are performed.

## Project Overview

The main challenge addressed by this project is that the number of moves performed in a Rubik’s Cube Simulator cannot be predetermined.

A Fixed-Size Array requires a predefined capacity. Once that capacity is reached, additional storage is needed to accommodate more elements.

To address this limitation, this project implements a **Dynamic Array** that automatically expands when its capacity becomes full.

The simulator provides a graphical interface where users can perform cube movements, shuffle the cube, undo moves, reset the cube, and observe how the Dynamic Array manages move history.

## Features

- **Interactive Rubik’s Cube:** Displays the current state of the Rubik’s Cube.
- **Move Controls:** Supports U, U', D, D', L, L', R, R', F, F', B, and B' movements.
- **Shuffle:** Randomly performs 20 cube movements.
- **Undo:** Reverses the most recently performed move.
- **Reset:** Restores the cube and clears its move history.
- **Move History:** Records and displays the sequence of performed moves.
- **Automatic Resizing:** Doubles the Dynamic Array capacity whenever it becomes full.
- **Array Information:** Displays the current size, capacity, usage, and resize events.
- **Runtime Testing:** Compares the performance of Fixed-Size Arrays and Dynamic Arrays.

## Data Structure: Dynamic Array

The Dynamic Array is used to store the Rubik’s Cube move history.

It begins with an initial capacity of **2** and automatically doubles its capacity whenever the array becomes full.

### Capacity Expansion

The capacity follows this sequence:

`2 → 4 → 8 → 16 → 32 → 64 → ...`

For example:

1. The array initially holds 2 moves.
2. When a third move is added, the array becomes full.
3. A new array with double the capacity is created.
4. Existing moves are copied into the new array.
5. The new move is inserted.

This allows the simulator to continue recording moves without requiring a predetermined maximum capacity.

## Implementation

The project uses the following classes:

| Class | Responsibility |
|---|---|
| `DynamicArray<T>` | Stores elements and automatically expands its capacity. |
| `Move` | Represents an individual Rubik’s Cube move and provides its inverse. |
| `MoveHistory` | Manages the sequence of moves using the Dynamic Array. |
| `RubiksCubeGUI` | Provides the graphical interface and connects user actions to the application logic. |
| `RuntimeTest` | Measures and compares Fixed-Size Array and Dynamic Array performance. |

### How It Works

The general process is:

```text
User performs a cube move
          |
          v
     performMove()
          |
          v
      cube.move()
          |
          v
   history.addMove()
          |
          v
  DynamicArray.add()
          |
          v
     Is the array full?
        /       \
      No         Yes
      |           |
      v           v
 Store move    Resize array
                  |
                  v
             Copy elements
                  |
                  v
               Store move
                  |
                  v
             Update GUI
```

## Time Complexity Analysis

| Operation | Time Complexity |
|---|---|
| Add | O(1) amortized |
| Add during resizing | O(n) |
| Get | O(1) |
| Set | O(1) |
| Remove Last | O(1) |
| Search | O(n) |
| Display History | O(n) |

**Amortized Analysis:** Although an individual insertion may take O(n) time when resizing occurs, the capacity doubles each time. Resizing happens occasionally, making the average insertion cost O(1) amortized over a sequence of insertions.

## Runtime Performance Testing

The project compares the runtime performance of a Fixed-Size Array and a Dynamic Array using `System.nanoTime()`.

The experiment uses:
- 3 warm-up runs, excluded from the measurements.
- 5 simulations per input size.
- Average runtime for comparison.
- Different input sizes to observe performance.

### Large-Scale Simulation Results

| Number of Moves | Fixed-Size Array | Dynamic Array |
|---:|---:|---:|
| 10 | 1,100 ns | 1,600 ns |
| 100 | 9,480 ns | 11,720 ns |
| 1,000 | 171,120 ns | 925,440 ns |

The results show that the Fixed-Size Array had lower measured runtime in the large-scale simulations. However, this does not establish that it is always the better choice.

The Dynamic Array was selected primarily because of its ability to automatically expand and accommodate an unknown and growing number of moves.

**Note:** The runtime experiment measures array storage operations, not the full graphical application's runtime. Actual performance may vary depending on JVM behavior and testing conditions.

## Technologies Used

- **Programming Language:** Java
- **GUI Framework:** Java Swing
- **Development Environment:** Eclipse IDE
- **Data Structure:** Dynamic Array
- **Performance Measurement:** `System.nanoTime()`

## How to Run the Project

### Requirements

- Java Development Kit (JDK)
- Eclipse IDE or another Java IDE

### Using Eclipse IDE

1. Clone or download this repository.
2. Open Eclipse IDE.
3. Select **File → Import → Existing Projects into Workspace**.
4. Choose the cloned project folder.
5. Finish the import process.
6. Locate `RubiksCubeGUI.java`.
7. Run the class containing the application's `main()` method.

## Project Purpose

This project demonstrates how a Dynamic Array can be applied to a practical application. Through the Rubik’s Cube Simulator, the behavior of automatic resizing, element storage, move history management, and amortized time complexity can be observed and analyzed.

## Author

**Rendel Ann M. Davao**

Data Structures and Algorithms (CCE105)

**Project:** Dynamic Array Implementation for Rubik’s Cube Simulator
