# ISO 15939 Measurement Process Simulator

**Student Name:** Yiğit Zümbüloğlu  
**Student ID:** 202428057

---

## Prerequisites
- **Java Development Kit (JDK) 17** or later must be installed on your system.
- Ensure that `javac` and `java` commands are available in your system's PATH.

## Project Structure
- `src/main/java`: Contains the source code organized into MVC packages.
  - `com.simulator.model`: Logic and data structures.
  - `com.simulator.view`: Swing UI components and panels.
  - `com.simulator.controller`: Glue between model and view.
- `bin/`: Directory for compiled `.class` files.
- `screenshot.png`: A visual demonstration of the application.

## Detailed Compilation Instructions
1. Open your terminal or Command Prompt.
2. Navigate to the project root directory.
3. Create a `bin` directory if it doesn't already exist:
   ```bash
   mkdir bin
   ```
4. Execute the following command to compile all source files:
   ```bash
   javac -d bin -sourcepath src/main/java src/main/java/com/simulator/Main.java
   ```

## Detailed Run Instructions
1. After successful compilation, start the application by running:
   ```bash
   java -cp bin com.simulator.Main
   ```
2. The application window **"ISO 15939 Measurement Process Simulator"** will appear.

## Using the Application
The simulator follows a 5-step wizard process:
1. **Profile**: Enter your user and session details.
2. **Define**: Select the Measurement Mode (Health/Education) and a specific Scenario.
3. **Plan**: Review the measurement plan (metrics and dimensions).
4. **Collect**: Enter raw data. The application will normalize the values (1.0 - 5.0) automatically.
5. **Analyse**: View the Radar Chart, weighted scores, and a generated Gap Analysis report.

---

## Screenshot Demonstration
Below is a visual demonstration of the application in action:

![Application Screenshot](screenshot.png)

---

## Notes
- The application uses **Java Swing** for the graphical interface and **Graphics2D** for custom chart rendering.
