
# Minesweeper Console

## Description
This is a simple console-based Minesweeper game implemented in Java. The game allows players to uncover tiles on a grid, with the goal of avoiding mines.
## Requirements
- Java 11 or higher
- Maven 3.6 or higher

## Setup
1. Clone the repository:
    ```sh
    git clone https://github.com/tharindur-dev/minesweeper-demo.git
    cd minesweeper-demo
    ```

2. Build the project using Maven:
    ```sh
    mvn clean install
    ```

## Running the Application
To run the application, execute the `Main` class:
```sh
mvn exec:java -Dexec.mainClass="Main"
```

## Running Tests
To run the unit tests, use the following Maven command:
```sh
mvn test
```

## Project Structure
- `src/main/java`: Contains the main application code.
- `src/test/java`: Contains the unit tests.
