package board;

import pieces.*;
import utils.Position;

public final class Board {

    private final Piece[][] grid = new Piece[8][8];

    public Board() {
        initialize();
    }

    public Piece getPiece(Position pos) {
        return grid[pos.getRow()][pos.getCol()];
    }

    public void movePiece(Position from, Position to) {
        Piece p = getPiece(from);
        grid[to.getRow()][to.getCol()] = p;
        grid[from.getRow()][from.getCol()] = null;
        if (p != null) p.move(to);
    }

    public void initialize() {

    // Pawns
    for (int i = 0; i < 8; i++) {
        grid[1][i] = new Pawn("black", new Position(1, i));
        grid[6][i] = new Pawn("white", new Position(6, i));
    }

    // Rooks
    grid[0][0] = new Rook("black", new Position(0,0));
    grid[0][7] = new Rook("black", new Position(0,7));
    grid[7][0] = new Rook("white", new Position(7,0));
    grid[7][7] = new Rook("white", new Position(7,7));

    // Knights
    grid[0][1] = new Knight("black", new Position(0,1));
    grid[0][6] = new Knight("black", new Position(0,6));
    grid[7][1] = new Knight("white", new Position(7,1));
    grid[7][6] = new Knight("white", new Position(7,6));

    // Bishops
    grid[0][2] = new Bishop("black", new Position(0,2));
    grid[0][5] = new Bishop("black", new Position(0,5));
    grid[7][2] = new Bishop("white", new Position(7,2));
    grid[7][5] = new Bishop("white", new Position(7,5));

    // Queens
    grid[0][3] = new Queen("black", new Position(0,3));
    grid[7][3] = new Queen("white", new Position(7,3));

    // Kings
    grid[0][4] = new King("black", new Position(0,4));
    grid[7][4] = new King("white", new Position(7,4));
}

    public boolean isValid(int r, int c) {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }
}