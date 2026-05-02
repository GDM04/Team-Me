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

        int dir = color.equals("white") ? -1 : 1;

        int r = position.getRow();
        int c = position.getCol();

        int forward = r + dir;

        // forward move
        if (board.isValid(forward, c)
                && board.getPiece(new Position(forward, c)) == null) {
            moves.add(new Position(forward, c));
        }

        // captures
        int[] cols = {c - 1, c + 1};

        for (int cc : cols) {
            if (board.isValid(forward, cc)) {
                Piece target = board.getPiece(new Position(forward, cc));
                if (target != null && !target.getColor().equals(color)) {
                    moves.add(new Position(forward, cc));
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wp" : "bp";
    }
}