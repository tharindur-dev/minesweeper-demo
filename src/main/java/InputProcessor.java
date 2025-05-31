import java.io.IOException;
import java.util.Scanner;

public class InputProcessor {

    public static void startProgram() throws IOException {
        while (true) {
            playGame();
            System.in.read();
        }
    }

    static void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Minesweeper!");

        int gridSize = readGridSize(scanner);
        int numberOfMines = readNumberOfMines(scanner, gridSize);

        Board board = new Board(gridSize, gridSize);
        board.placeMines(numberOfMines);

        System.out.println("Here is your minefield:");
        System.out.println(board.getDisplayString());

        boolean gameOver;
        do {
            System.out.println("Select a square to reveal (e.g. A1):");
            String input = scanner.next().toUpperCase();
            gameOver = processSquareInput(input, board);
        } while (!gameOver);
    }

    static int readGridSize(Scanner in) {
        System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid):");
        int gridSize = in.nextInt();
        if (gridSize < 1 || gridSize > 26) {
            System.out.println("Invalid grid size. Please enter a number between 1 and 26.");
            return readGridSize(in);
        }
        return gridSize;
    }

    static int readNumberOfMines(Scanner in, int gridSize) {
        System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares):");
        int mines = in.nextInt();
        if (0 > mines || mines > gridSize * gridSize * 0.35) {
            System.out.println("Invalid number of mines. Please enter a number between 0 and " + (int) (gridSize * gridSize * 0.35) + ".");
            return readNumberOfMines(in, gridSize);
        }
        return mines;
    }

    static boolean processSquareInput(String input, Board board) {
        if (!input.matches("^[A-Z][1-9]{1,2}$")) {
            System.out.println("Invalid input. Please enter a square in the format A1, B2, etc.");
            return false;
        }
        char rowChar = input.charAt(0);
        int col = Integer.parseInt(input.substring(1)) - 1;
        int row = rowChar - 'A';

        if (row < 0 || row >= board.getRows() || col < 0 || col >= board.getCols()) {
            System.out.println("Invalid square. Please try again.");
            return false;

        }

        Cell cell = board.getCell(row, col);
        if (cell.isHasMine()) {
            System.out.println("Oh no, you detonated a mine! Game over.");
            System.out.println("Press any key to play again...");
            return true;
        }
        board.uncoverCell(row, col);

        System.out.printf("This square contains %d adjacent mines.\n", cell.getAdjacentMines());
        System.out.println("Here is your updated minefield:");
        System.out.println(board.getDisplayString());

        if (board.isGameWon()) {
            System.out.println("Congratulations, you have won the game!");
            System.out.println("Press any key to play again...");
            return true;
        }
        return false;
    }
}
