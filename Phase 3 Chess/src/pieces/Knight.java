package pieces;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import utils.Position;

public class Knight extends Piece {

    public Knight(String color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> possibleMoves(Board board) {

        List<Position> moves = new ArrayList<>();

        int[][] jumps = {
                {2,1},{2,-1},{-2,1},{-2,-1},
                {1,2},{1,-2},{-1,2},{-1,-2}
        };

        int r = position.getRow();
        int c = position.getCol();

        for (int[] j : jumps) {

            int nr = r + j[0];
            int nc = c + j[1];

            if (board.isValid(nr, nc)) {

                Piece p = board.getPiece(new Position(nr, nc));

                if (p == null || !p.getColor().equals(color)) {
                    moves.add(new Position(nr, nc));
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wn" : "bn";
    }
}