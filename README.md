# The Gradle Centurion Bomb Manual
*101 modules, 100 minutes, exponentially more problems.*

[![CircleCI](https://circleci.com/gh/Ultraviolet-Ninja/GradleCenturion/tree/main.svg?style=shield)](https://circleci.com/gh/Ultraviolet-Ninja/GradleCenturion/tree/main)
![Project Version](https://img.shields.io/badge/version-0.22.3-blueviolet)

## Intro
This project is designed to solve all puzzles found on the Centurion Bomb from Keep Talking and Nobody Explodes, which is a combination of many community-made puzzles and some from the base game set in different languages.<br>

This is a huge project for one man to tackle, but I've [learned a lot](Learned.md) from the challenges I've faced.

## Technologies
- Java 17
- Gradle 7.4
### Plugins
- JavaFX
- Palantir Docker
- Breadmoirai GitHub Release
### Dependencies
- MaterialFX ver. 11.12.0
- JFoenix ver. 9.0.4
- JavaTuple ver. 1.2
- JGraphT ver. 1.5.1
- OpenCSV ver. 5.7.1
### Other Technologies
- Circle CI
- CodeMR Free Trial

## Status - In progress
See the running list of modules [here](Progress.md)

## How to run the program
*Disclaimer*: This is referring to program versions `0.22.2` onward
- The `source code` option of the [Release Page](https://github.com/Ultraviolet-Ninja/GradleCenturion/releases) contains an executable, a Windows batch file and all the necessary jar files to run the program
- The `Gradle-Centurion-[VERSION]-[OPERATING SYSTEM]` contains a OS-specific executable file with a runtime environment for the program to run in
  - *Only supports Windows currently*

## Inspiration
After my first manual turning out to be successful in solving the main-game bombs, I thought "*Why stop there?*".
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
**One D Battery** in **one holder** and a **lit NSA indicator**

![WidgetTwo](markdown/Widget2.jpg)
**One port plate** with a **PS/2 port**, another **D battery in one holder** and a serial code **"PN4XC5"**.<br>
Overall, this edgework would be given as "1 PS2 port in 1 plate, a lit November SA, 2 in 2 (2 batteries in 2 holders), and serial is Papa November 4 X-ray Charlie 5" (Using the Nato Phonetic Alphabet)
