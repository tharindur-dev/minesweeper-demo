import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
    private int rows;
    private int cols;
    private Cell[][] cells;
    private int totalMines;
    private int totalUncoveredCells;

    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.cells = new Cell[rows][cols];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                cells[i][j] = new Cell();
            }
        }
        this.totalUncoveredCells = cells.length * cells[0].length;
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates");
        }
        return cells[row][col];
    }

    public String getDisplayString() {
        StringBuilder board = new StringBuilder("  ");
        char startChar = 'A';
        for (int j = 1; j < cols + 1; j++) {
            board.append(j).append(" ");
        }
        board.append("\n");
        for (int i = 0; i < rows; i++) {
            board.append((char)(startChar + i)).append(" ");
            for (int j = 0; j < cols; j++) {
                board.append(cells[i][j].toString()).append(" ");
            }
            board.append("\n");
        }
        return board.toString();
    }

    public void placeMines(int numberOfMines) {
        this.totalMines = numberOfMines;
        int placedMines = 0;
        while (placedMines < numberOfMines) {
            int row = (int) (Math.random() * rows);
            int col = (int) (Math.random() * cols);
            if (!cells[row][col].isHasMine()) {
                cells[row][col].setHasMine(true);
                placedMines++;
            }
        }
    }

    // place mine in cell and update adjacent cells
    public void placeMine(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates");
        }
        if (!cells[row][col].isHasMine()) {
            cells[row][col].setHasMine(true);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue; // Skip the cell itself
                    int adjRow = row + i;
                    int adjCol = col + j;
                    if (adjRow >= 0 && adjRow < rows && adjCol >= 0 && adjCol < cols) {
                        Cell cell = cells[adjRow][adjCol];
                        cell.setAdjacentMines(cell.getAdjacentMines() + 1);
                    }
                }
            }
        }
    }

    // returns false if the cell has no mine. true if it has a mine
    public boolean uncoverCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        Cell cell = cells[row][col];
        if (cell.isUncovered()) return false;
        if (cell.isHasMine()) return true;

        if (cell.getAdjacentMines() == 0) {
            cell.setUncovered(true);
            totalUncoveredCells--;
            // Uncover adjacent cells recursively
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue; // Skip the cell itself
                    uncoverCell(row + i, col + j);
                }
            }
        } else {
            cell.setUncovered(true);
            totalUncoveredCells--;
        }

        return false;
    }

    public boolean isGameWon() {
        return this.totalMines == this.totalUncoveredCells;
    }

}
