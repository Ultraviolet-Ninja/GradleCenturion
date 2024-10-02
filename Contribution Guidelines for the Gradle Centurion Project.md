# Contribution Guidelines for the Gradle Centurion Project

Welcome to the Centurion Puzzle Solver project! This JavaFX application aims to provide solutions for the various puzzles found in the Centurion bomb mod of *Keep Talking and Nobody Explodes*. I welcome contributions from developers of all skill levels to help enhance and expand this project!



## Getting Started

To contribute, follow these steps:

1. **Parse the README**: If you've never heard of the game, this would be an excellent place to understand what the project is about and 

2. **Fork the Repository**: Click on the "Fork" button at the top right of this repository to create a copy on your GitHub account.

3. **Clone the Forked Repository**: Clone your fork to your local machine using:

   ```bash
   git clone https://github.com/your-username/GradleCenturion.git
   ```

4. **Set Up the Development Environment**: Ensure you have Java 21 set up on your machine, then gradle should be able to handle the rest of the dependencies when loading up the project. 

5. **Explore the Codebase**: Take a look at the existing puzzles implemented in the project to understand the structure and coding style. The codebase is set up with basic outlines for `.fxml` files and it will be up to the programmer to work on the associated controller and puzzle classes.



## Contribution Areas

I'm looking for contributors to help tackle some of the **unhandled puzzles**, to **fix one of the few UI issues** I'm having with the project or to help **create executables for Linux systems**. Unhandled puzzles can be seen in the `Progress.md` file at the base-level of the project repository. To claim that you'll be tackling a certain puzzle, please create an issue on the given page so that we can discuss further instructions.



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
   git push origin feature/your-feature-name
   ```

5. **Open a Pull Request**: Go to the original repository and click on "Pull Requests" to open a new pull request. Provide a clear description of your changes.



## Code Style

Please follow the existing code style in the project. Java files for a new puzzle solution should live under the `bomb.modules.[ALPHABETIC_LABEL].[PUZZLE_NAME]` package. Sub-package organization at that point is left to your discretion. 



### Testing Puzzle Solutions

Puzzle solutions should have tests associated with them.



## Questions and Support

If you have any questions, feel free to open an issue or reach out to the maintainers. I'm here to help!

Thank you for contributing to the Gradle Centurion project, and happy coding!