# The Gradle Centurion Bomb Manual
*101 modules, 100 minutes, exponentially more problems.*

[![CircleCI](https://circleci.com/gh/Ultraviolet-Ninja/GradleCenturion/tree/main.svg?style=shield)](https://circleci.com/gh/Ultraviolet-Ninja/GradleCenturion/tree/main)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/Ultraviolet-Ninja/GradleCenturion.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/Ultraviolet-Ninja/GradleCenturion/context:java)
![Project Version](https://img.shields.io/badge/version-0.16.0-blueviolet)
### Intro
This project is designed to solve all puzzles found on the Centurion Bomb from Keep Talking and Nobody Explodes, which is a combination of many community-made puzzles and some from the base game set in different languages.<br>

This is a huge project for one man to tackle, but it's taught me about the challenges of Java 8 with FX, CI pipelines and working in Gradle.

### Technologies
- Java 8 with FX
- Gradle 6.8.1
#### Plugins
- Jacoco
- Palantir Docker
- Breadmoirai GitHub Release
#### Dependencies
- ControlFX ver 8.40.18
- JFoenix ver 8.0.10
- JavaTuple ver 1.2
- Medusa ver. 8.3
- JGraphT ver. 1.4
#### Other Technologies
- Circle CI
- LGTM Code Quality
- CodeMR Free Trial

### Status
In progress<br>
See the running list of modules [here](Progress.md)

### Inspiration
After my first manual turning out to be successful in solving the main-game bombs, I thought "Why stop there?".
I started creating this project working on the auto-solver for the vanilla game, which was, by comparison, much easier. 

### Example Bomb
#### Front view
![Front](markdown/Front.jpg)

#### Back view
![Back](markdown/Back.jpg)

#### Example Widgets
Widgets are important details about the bomb such that many modules depend on whether particular features are present or
not. The list of details includes Port Types, Number of Port Plates, Lit and Unlit Indicators, Number of Batteries,
Number of Battery Holders, Two-Factor Authentication, and the Serial Code.
![WidgetOne](markdown/Widget1.jpg)
One D Battery in one holder and a lit NSA indicator

![WidgetTwo](markdown/Widget2.jpg)
One port plate with a PS/2 port, another D battery in one holder and a serial code "PN4XC5".<br>
Overall, this edgework would be given as "1 PS2 port in 1 plate, a lit NSA, 2 in 2 (2 batteries in 2 holders), and serial is Papa November 4 X-ray Charlie 5" (Using the Nato Phonetic Alphabet)
