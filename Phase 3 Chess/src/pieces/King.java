package pieces;

import board.Board;
import java.util.ArrayList;
import java.util.List;
import utils.Position;

public class King extends Piece {

    public King(String color, Position position) {
        super(color, position);
    }

    @Override
    public List<Position> possibleMoves(Board board) {

        List<Position> moves = new ArrayList<>();

        int r = position.getRow();
        int c = position.getCol();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                if (i == 0 && j == 0) continue;

                int nr = r + i;
                int nc = c + j;

                if (board.isValid(nr, nc)) {

                    Piece p = board.getPiece(new Position(nr, nc));

                    if (p == null || !p.getColor().equals(color)) {
                        moves.add(new Position(nr, nc));
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color.equals("white") ? "wk" : "bk";
    }
}