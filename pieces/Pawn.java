package pieces;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import utils.Position;

public class Pawn extends Piece {

    public Pawn(String color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> possibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();

        int direction = color.equals("white") ? -1 : 1;

        int newRow = position.getRow() + direction;
        int col = position.getCol();

        if (board.isValid(newRow, col)) {
            moves.add(new Position(newRow, col));
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wp" : "bp";
    }
}