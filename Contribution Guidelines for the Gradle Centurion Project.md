# Contribution Guidelines for the Gradle Centurion Project

Welcome to the Gradle Centurion project! This JavaFX application aims to provide solutions for the various puzzles
found in the Centurion bomb mod of *Keep Talking and Nobody Explodes*. I welcome contributions from developers of all
skill levels to help enhance and expand this project!



## Getting Started

To contribute, follow these steps:

1. **Parse the README**: If you've never heard of the game, this would be an excellent place to understand what the
game is and what the project is about

2. **Fork the Repository**: Click on the "Fork" button at the top right of this repository to create a copy on your
GitHub account.

3. **Clone the Forked Repository**: Clone your fork to your local machine using:

   ```bash
   git clone https://github.com/your-username/GradleCenturion.git
   ```

4. **Set Up the Development Environment**: Ensure you have **Java 21** set up on your machine, then Gradle should be
able to handle the rest of the dependencies when loading up the project. This project is developed in Intellij; all
other IDEs are fine, but we'd have to discuss IDE specific issues if/when they occur.

5. **Explore the Codebase**: Take a look at the existing puzzles implemented in the project to understand the
structure and coding style. The codebase is set up with basic outlines for `.fxml` files and it will be up to the
programmer to work on the associated controller and puzzle classes.



## Areas of Contribution

I'm looking for contributors to help tackle some of the **unhandled puzzles** or to **fix one of the few UI issues**
I'm having with the project. Unhandled puzzles can be seen in the [Progress file](Progress.md) at the base-level of the
project repository. To claim that you'll be tackling a certain puzzle, please add a comment to the
[discussion board](https://github.com/Ultraviolet-Ninja/GradleCenturion/issues/131). After approval, create an issue on the given page so that we can discuss further instructions.



### Unhandled Puzzles

Every puzzle has an associated manual page that you can find in the `maunals/` subdirectory or on the
[KTANE Manual Page Website](https://ktane.timwi.de/). It will be your job to interpret the manual page so that a computer can solve
the puzzle. This involves developing the algorithm that will handle the inputs, the UI that displays what a
'manual expert' needs to fill in, the controller that acts as the middleware between the two and the test code that
validates the algorithm to be correct in different scenarios.



#### How to choose a puzzle to solve

The `manuals/Centurion Puzzle Difficulty.xlsx` file contains all the puzzles and their relative 'manual expert'
difficulty. This would probably be a good place to start for finding interesting problems.



#### Testing Puzzle Solutions

Puzzle solutions should have tests associated with them. Unit testing will be an important part of making sure that
your solutions are correct and tested against wrong user input.





### UI Issues

There are a few known UI issues that occur in the project. If you're interested in looking into fixing those,
I can explain more about the problem that occurs.



## Creating a Pull Request

1. **Create a New Branch**:

   - **Features** can just be labeled as the puzzle name
   - **Issues** can be prefixed with `issue/`

   ```bash
   git checkout -b puzzle-name
   ```

2. **Make Your Changes**: Implement your solution to the chosen puzzle.

3. **Commit Your Changes**: Ensure your commits are descriptive.

   ```bash
   git commit -m "Add solution for Puzzle A"
   ```

4. **Push Your Changes**:
   ```bash
   git push origin puzzle-name
   ```

5. **Open a Pull Request**: Go to the original repository and click on "Pull Requests" to open a new pull request.
Provide a summary of your changes and make sure that you are merging to the `dev` branch.



## Code Style

Please glance at existing puzzle solutions to get a feel for the coding style of the project. This project is run
through automatic code style checking using **Codacy** for new code changes, so fixing most code style issues will
be an important part of this project's development process.

Java files for a new puzzle solution should live under the `bomb.modules.[ALPHABETIC_LABEL].[PUZZLE_NAME]` package.
Sub-package organization at that point is left to your discretion.



## Questions and Support

If you have any questions, feel free to open an issue or reach out to me. I'm here to help!

Thank you for contributing to the Gradle Centurion project, and happy coding!