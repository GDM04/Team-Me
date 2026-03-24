package game;

import board.Board;
import java.util.Scanner;
import utils.Position;

public class Game {
    private final Board board;
    private String currentTurn;

    public Game() {
        board = new Board();
        currentTurn = "white";
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.display();
            System.out.println(currentTurn + "'s move:");

            String input = scanner.nextLine();

            if (!isValidInput(input)) {
                System.out.println("Invalid format. Use E2 E4");
                continue;
            }

            Position from = parsePosition(input.substring(0, 2));
            Position to = parsePosition(input.substring(3, 5));

            board.movePiece(from, to);

            switchTurn();
        }
    }

    private boolean isValidInput(String input) {
        return input.matches("^[A-H][1-8] [A-H][1-8]$");
    }

    private Position parsePosition(String s) {
        int col = s.charAt(0) - 'A';
        int row = 8 - (s.charAt(1) - '0');
        return new Position(row, col);
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("white") ? "black" : "white";
    }
}