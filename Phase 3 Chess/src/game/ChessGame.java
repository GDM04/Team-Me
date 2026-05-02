package game;

import board.Board;

public class ChessGame {

    private Board board;
    private boolean whiteTurn = true;

    public ChessGame() {
        board = new Board();
    }

    public Board getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public boolean makeMove(int fr, int fc, int tr, int tc) {

        var from = new utils.Position(fr, fc);
        var to = new utils.Position(tr, tc);

        var piece = board.getPiece(from);

        if (piece == null) return false;

        String expected = whiteTurn ? "white" : "black";
        if (!piece.getColor().equals(expected)) return false;

        var moves = piece.possibleMoves(board);

        boolean valid = moves.stream()
                .anyMatch(p -> p.getRow() == tr && p.getCol() == tc);

        if (!valid) return false;

        board.movePiece(from, to);

        whiteTurn = !whiteTurn;
        return true;
    }
}