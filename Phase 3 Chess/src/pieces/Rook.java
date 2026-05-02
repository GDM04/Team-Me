package pieces;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import utils.Position;

public class Rook extends Piece {

    public Rook(String color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> possibleMoves(Board board) {

        List<Position> moves = new ArrayList<>();

        int r = position.getRow();
        int c = position.getCol();

        int[][] dirs = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };

        for (int[] d : dirs) {
            int nr = r + d[0];
            int nc = c + d[1];

            while (board.isValid(nr, nc)) {

                Piece p = board.getPiece(new Position(nr, nc));

                if (p == null) {
                    moves.add(new Position(nr, nc));
                } else {
                    if (!p.getColor().equals(color)) {
                        moves.add(new Position(nr, nc));
                    }
                    break;
                }

                nr += d[0];
                nc += d[1];
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wr" : "br";
    }
}