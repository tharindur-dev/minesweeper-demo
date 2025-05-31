import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cell {
    boolean hasMine;
    int adjacentMines;
    boolean uncovered;

    @Override
    public String toString() {
        return uncovered ? (hasMine ? "*" : String.valueOf(adjacentMines)) : "_";
    }
}
