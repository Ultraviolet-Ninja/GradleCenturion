# What I've learned
## JavaFX Custom Components
- Understanding how to create CC's with this [video](https://www.youtube.com/watch?v=1yuaJQJ1FXg) as a guide
- Experimenting with CC's to figure out how to correctly turn them into jar files that are compatible with SceneBuilder
## Design Choices
When my friend was working on the project, he helped me decide to move to a RadioButton design for switching between puzzles. This helped me get away from my previous design of nested TabPanes in a TabPane, which probably isn't the most visually appealing.
## Gradle/Continuous Integration
- Using the build.gradle file to organize dependencies and plugins, facilitate testing, set up distributions in zip and tar files and releases for GitHub
- Making custom tasks and using parameters for CI pipelines to test certain parts of the code
- Automating tests with CircleCI using this [video](https://www.youtube.com/watch?v=9PgZCJNzY9M) as a guide
## Graphs and their Algorithms
- Creating graphs by looking up the concept or using the JGraphT library
- Making use of Dijkstra's Shortest Path and A* to solve different problems  
## From my Internship
- Using a backlog to organize tasks that are done, in progress, or haven't been looked at
- Creating results that get deployed to GitHub and can be downloaded by anyone
- Using a less technical version of the design documents. I used them to outline how I will solve puzzles and what the frontend will look like
## From my Classes
### School
#### Tools & Practices
- Understanding how to use Git and GitHub to host my project
- Having an introduction to Gradle from one of my teammates trying to bring it into the project (Wasn't successful, but still)
#### Verification
- Understanding the perks of TestNG vs. my previous experience with J-Unit
- Discovering Pitest, Exploratory Testing, Mockito, and Jacoco as different ways to test code and thinking about which concepts I should implement into the project
#### Design Patterns
- Using Factories, Facade, and Observers to facilitate repeating code and updating related modules
- Being introduced to code coupling and cohesion
### Udemy
- Understanding how to write Groovy code
- Using CompletableFuture to split up unrelated tasks
- Utilizing functional interfaces and the Streams API wherever necessary
- Learning about graph algorithms and heuristics
