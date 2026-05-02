package pieces;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import utils.Position;

public class Queen extends Piece {

    public Queen(String color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> possibleMoves(Board board) {

        List<Position> moves = new ArrayList<>();

        moves.addAll(new Rook(color, position).possibleMoves(board));
        moves.addAll(new Bishop(color, position).possibleMoves(board));

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wq" : "bq";
    }
}