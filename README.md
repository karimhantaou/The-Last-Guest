# The Last Guest


## The game
The Last Guest is a 2D, turn-based game with a top-down view of a board.  
The goal is to discover the identity of the murderer among the other characters living in a mansion, before the killer eliminates everyone.  
The player controls one of six characters, taking on the role of an inspector. Each character has unique stats.  
At the end of each turn, the inspector can choose various actions depending on the situation.  

## How to Play

1. **Pick Your Character**

    * Each character has a unique story and special perks.


2. **Controls**

    * **Right Click**: Open the action menu.
    * **Space**: Skip your turn and make a guess on who the murderer is.
    * **Escape**: Open the pause menu, where you can:

        * Restart the game
        * Change the music volume
        * Activate God Mode
        * Exit the game
    * **C**: Show your character's sheet


3. **Inventory**

    * Click on the items in your inventory (located on the left side) to use or interact with them.

## Installation and Running

### Requirements

* Java Development Kit (JDK) 11 or higher
* [Gradle](https://gradle.org/) (optional if using Gradle Wrapper)
* LibGDX libraries (included in the project if using standard setup)

### Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/EpitechMscProPromo2028/T-JAV-501-MPL_1.git
   cd T-JAV-501-MPL_1.git
   ```

2. **Build the Project**

    * If using Gradle:

      ```bash
      ./gradlew build
      ```
    * If using an IDE (IntelliJ IDEA, Eclipse, etc.), import the project as a Gradle project.

3. **Run the Game**

    * Using Gradle:

      ```bash
      ./gradlew run
      ```
    * Using IDE:

        * Open `DesktopLauncher.java` in the `desktop` module.
        * Run the `main` method.

4. **Enjoy the Game!**
