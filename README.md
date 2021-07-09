# The Gradle Centurion Bomb Manual
*101 modules, 100 minutes, exponentially more problems.*

[![CircleCI](https://circleci.com/gh/Ultraviolet-Ninja/GradleCenturion/tree/main.svg?style=shield)](https://circleci.com/gh/Ultraviolet-Ninja/GradleCenturion/tree/main)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/Ultraviolet-Ninja/GradleCenturion.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Ultraviolet-Ninja/GradleCenturion/context:java)
### Intro
This project is designed to solve all puzzles found on the Centurion Bomb from Keep Talking and Nobody Explodes, which is a combination of many community-made puzzles.<br>


This is the Gradle implementation of the original project, created with the intention of using Continuous Integration to test module solutions and data structures as the project progresses. This also has helped me understand how JavaFX, Gradle and Circle CI interact with each other.

### Technologies
- JDK 8
- Java FX - GUI
- Gradle - Automation Tool
- Circle CI - Test Automation
- Jacoco - Test Coverage Analysis
- JGraphT ver. 1.0.1 - Graph Library
- CodeMR Free Trial - Coupling/Cohesion/Code Complexity Analysis

#### Technologies Under Test
- ControlFX - JavaFX Library
- JFoenix - JavaFX Library
- Medusa - Gauge Library
- FontAwesomeFX - Font Styling
- RichText - Font Styling

### Status
In progress<br>
See the running list of modules [here](Progress.md)

### Inspiration
After my first manual turning out to be successful in solving the main-game bombs, I thought "Why stop there?".
I started creating this project working on the auto-solver for the vanilla game, which was, by comparison, much easier. 

### Example Bomb
##### Front view
![Front](markdown/Front.jpg)

##### Back view
![Back](markdown/Back.jpg)

##### Example Widgets
Widgets are important details about the bomb such that many modules depend on whether particular features are present or
not. The list of details includes Port Types, Number of Port Plates, Lit and Unlit Indicators, Number of Batteries,
Number of Battery Holders, Two-Factor Authentication, and the Serial Code.
![WidgetOne](markdown/Widget1.jpg)
One D Battery in one holder and a lit NSA indicator

![WidgetTwo](markdown/Widget2.jpg)
One port plate with a PS/2 port, another D battery in one holder and a serial code "PN4XC5".<br>
Overall, this edgework would be given as "1 PS2 port in 1 plate, a lit NSA, 2 in 2 (2 batteries in 2 holders), and serial is Papa November 4 X-ray Charlie 5" (Using the Nato Phonetic Alphabet)
