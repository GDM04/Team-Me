package pieces;
import board.Board;
import java.util.List;
import utils.Position;

public abstract class Piece {

    protected String color;
    protected Position position;

    public Piece(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    public String getColor() {
        return color;
    }

    public Position getPosition() {
        return position;
    }

    public void move(Position p) {
        this.position = p;
    }

    public abstract List<Position> possibleMoves(Board board);
}