package board;

import pieces.*;
import utils.Position;

public class Board {
    private Piece[][] grid;

    public Board() {
        grid = new Piece[8][8];
        initialize();
    }

    public void initialize() {
        for (int i = 0; i < 8; i++) {
            grid[1][i] = new Pawn("black", new Position(1, i));
            grid[6][i] = new Pawn("white", new Position(6, i));
        }
    }

    public boolean isValid(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public Piece getPiece(Position pos) {
        return grid[pos.getRow()][pos.getCol()];
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);

        if (piece != null) {
            grid[to.getRow()][to.getCol()] = piece;
            grid[from.getRow()][from.getCol()] = null;
            piece.move(to);
        }
    }

    public void display() {
        System.out.println("  A  B  C  D  E  F  G  H");

        for (int i = 0; i < 8; i++) {
            System.out.print((8 - i) + " ");
            for (int j = 0; j < 8; j++) {
                if (grid[i][j] == null) {
                    System.out.print("## ");
                } else {
                    System.out.print(grid[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}