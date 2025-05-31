import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testBoardInitialization() {
        Board board = new Board(5, 5);
        assert board.getRows() == 5;
        assert board.getCols() == 5;
        assertEquals(5, board.getCells().length);
        assertEquals(5, board.getRows());
    }

    @Test
    public void testGetDisplayString(){
        Board board = new Board(3, 3);
        String expected = """
                  1 2 3\s
                A _ _ _\s
                B _ _ _\s
                C _ _ _\s
                """;
        assertEquals(expected, board.getDisplayString());

        board.placeMine(0, 0);
        board.getCell(0, 1).setUncovered(true);
        board.getCell(1, 0).setUncovered(true);
        expected = """
                  1 2 3\s
                A _ 1 _\s
                B 1 _ _\s
                C _ _ _\s
                """;

        assertEquals(expected, board.getDisplayString());

    }

    @Test
    public void testMinePlacement() {
        Board board = createBoardWithMines();
        uncoverAllCells(board);
        String expected = """
                  1 2 3 4\s
                A 2 * 2 0\s
                B 3 * 2 0\s
                C * 2 1 0\s
                D 1 1 0 0\s
                """;
        assertEquals(expected, board.getDisplayString());
    }

    @Test
    public void testRandomMinePlacement(){
        Board  board = new Board(4, 4);
        board.placeMines(3);
        assertEquals(3, board.getTotalMines(), "Total mines should be 3");
        assertEquals(16, board.getTotalUncoveredCells(), "Total uncovered cells should be 16");
    }

    @Test
    public void testUncoverCellWithMine() {
        Board board = createBoardWithMines();
        boolean result = board.uncoverCell(0, 1);
        assertTrue(result, "Uncovering a cell with a mine should return true");

    }

    @Test
    public void testUncoverCellAdjacentToMine() {
        Board board = createBoardWithMines();
        board.uncoverCell(0,0);

        String display = board.getDisplayString();
        String expected = """
                  1 2 3 4\s
                A 2 _ _ _\s
                B _ _ _ _\s
                C _ _ _ _\s
                D _ _ _ _\s
                """;

        assertEquals(expected, display);
    }

    @Test
    public void testUncoverCellNoAdjacentMines() {
        Board board = createBoardWithMines();
        board.uncoverCell(3, 3);

        String display = board.getDisplayString();
        String expected = """
                  1 2 3 4\s
                A _ _ 2 0\s
                B _ _ 2 0\s
                C _ 2 1 0\s
                D _ 1 0 0\s
                """;

        assertEquals(expected, display);
    }

    @Test
    public void testGameWinningCondition() {
        Board board = createBoardWithMines();
        board.uncoverCell(3,3);

        assertFalse(board.isGameWon(), "Game should not be won yet");

        board.uncoverCell(1, 0);
        board.uncoverCell(0,0);
        board.uncoverCell(2, 1);
        board.uncoverCell(3, 0);

        assertTrue(board.isGameWon(), "Game should be won after uncovering all non-mine cells");
    }

    private Board createBoardWithMines() {
        Board board = new Board(4, 4);
        board.placeMine(0, 1);
        board.placeMine(1, 1);
        board.placeMine(2, 0);
        board.setTotalMines(3);
        return board;
    }

    private void uncoverAllCells(Board board) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                board.getCell(i, j).setUncovered(true);
            }
        }
    }

}
