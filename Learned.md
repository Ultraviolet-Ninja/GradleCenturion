# What I've learned
## JavaFX Custom Components
- Understanding how to create CC's with this [video](https://www.youtube.com/watch?v=1yuaJQJ1FXg) as a guide.
- Experimenting with CC's to figure out how to correctly turn them into jar files that are compatible with SceneBuilder.
- Utilizing CSS to style components and make them look like the puzzles in the game.
## Project Tech/Design Choices
When my friend was working on the project, he helped me decide to move to a RadioButton design for switching between puzzles. This helped me get away from my previous design of nested TabPanes in a TabPane, which probably isn't the most visually appealing.

Another thing we talked about while he was on the project was moving back to Java 8 since it was easier to work with JavaFX. This decision was later reversed after some time experimenting in Gradle.
## Gradle/Continuous Integration
- Using the build.gradle file to organize dependencies and plugins, facilitate testing, set up distributions in zip and tar files, and releases for GitHub.
- Making custom tasks and using parameters for CI pipelines to partition my test suite.
- Automating tests with CircleCI using this [video](https://www.youtube.com/watch?v=9PgZCJNzY9M) as a guide.
- Coming across ~~LGTM~~ (_when it was still around_) Codacy and taking advantage of its code quality checking for flaws in my Java code.
## Graphs and their Algorithms
- Creating graphs by looking up the concept or using the JGraphT library.
- Making use of Dijkstra's Shortest Path and A* to solve different problems.
## From my Internship
- Using a backlog to organize tasks that are done, in progress, or haven't been looked at.
- Creating results that get deployed to GitHub and can be downloaded by anyone.
- Using a less technical version of design documents. I used them to outline how I will solve puzzles and what the front end will look like.
## From my Classes
### School
#### Tools & Practices
- Understanding how to use Git and GitHub to host my project.
- Having an introduction to Gradle from one of my teammates trying to bring it into another project. (_Wasn't successful, but still_)
#### Verification
- Understanding the perks of TestNG vs. my previous experience with J-Unit.
- Discovering Pitest, Exploratory Testing, Mockito, and Jacoco as different ways to test code and thinking about which concepts I should implement into the project.
#### Design Patterns
- Using Strategies, Factories, Facades, and Observers to facilitate repeating code and updating related modules.
- Being introduced to code coupling and cohesion, which was later applied when I had a CodeMR free trial.
### Udemy
- Understanding how to write Groovy code. [Link](https://www.udemy.com/course/gradle-for-java-developers/)
- Using CompletableFuture to split up unrelated tasks. [Link](https://www.udemy.com/course/parallel-and-asynchronous-programming-in-modern-java/)
- Utilizing functional interfaces and the Streams API wherever necessary. [Link](https://www.udemy.com/course/functional-programming-and-reactive-programming-in-java/)
- Learning about graph algorithms and heuristics. [Link](https://www.udemy.com/course/artificial-intelligence-games-in-java/learn/lecture/5958944?start=0#overview)
