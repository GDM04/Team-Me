package game;

import board.Board;
import java.awt.*;
import java.io.*;
import java.util.Stack;
import javax.swing.*;
import pieces.Piece;
import utils.Position;

public class Chess extends JFrame {

    private JButton[][] squares = new JButton[8][8];
    private Board board;

    private int selectedRow = -1;
    private int selectedCol = -1;

    private String currentTurn = "white";

    private Stack<Board> history = new Stack<>();

    public Chess() {

        board = new Board();

        setTitle("Chess Game - Phase 3");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        initBoard();
        updateBoardUI();

        setupMenu(); //     MENU ADDED

        setVisible(true);
    }

    // MENU
    private void setupMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");

        JMenuItem saveItem = new JMenuItem("Save Game");
        JMenuItem loadItem = new JMenuItem("Load Game");
        JMenuItem undoItem = new JMenuItem("Undo Move");

        saveItem.addActionListener(e -> saveGame());
        loadItem.addActionListener(e -> loadGame());
        undoItem.addActionListener(e -> undoMove());

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        editMenu.add(undoItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);

        setJMenuBar(menuBar);
    }

    // BOARD UI 
    private void initBoard() {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                JButton btn = new JButton();
                btn.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 36));
                btn.setFocusPainted(false);

                final int row = r;
                final int col = c;

                btn.addActionListener(e -> handleClick(row, col));

                squares[r][c] = btn;
                add(btn);
            }
        }
    }

    private void handleClick(int r, int c) {

        Piece clicked = board.getPiece(new Position(r, c));

        // FIRST CLICK (select piece)
        if (selectedRow == -1) {

            if (clicked == null) return;

            if (!clicked.getColor().equals(currentTurn)) {
                JOptionPane.showMessageDialog(this, "Not your turn!");
                return;
            }

            selectedRow = r;
            selectedCol = c;
            return;
        }

        // SECOND CLICK (move attempt)
        Piece piece = board.getPiece(new Position(selectedRow, selectedCol));

        if (piece != null) {

            boolean valid = piece.possibleMoves(board)
                    .stream()
                    .anyMatch(p -> p.getRow() == r && p.getCol() == c);

            if (valid) {

                history.push(cloneBoard());

                board.movePiece(
                        new Position(selectedRow, selectedCol),
                        new Position(r, c)
                );

                currentTurn = currentTurn.equals("white") ? "black" : "white";
            }
        }

        selectedRow = -1;
        selectedCol = -1;

        updateBoardUI();
    }

    private void updateBoardUI() {

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {

                Piece p = board.getPiece(new Position(r, c));

                squares[r][c].setText(p == null ? "" : getSymbol(p));
            }
        }
    }

    private String getSymbol(Piece p) {

        return switch (p.getClass().getSimpleName()) {

            case "Pawn" -> p.getColor().equals("white") ? "♙" : "♟";
            case "Rook" -> p.getColor().equals("white") ? "♖" : "♜";
            case "Knight" -> p.getColor().equals("white") ? "♘" : "♞";
            case "Bishop" -> p.getColor().equals("white") ? "♗" : "♝";
            case "Queen" -> p.getColor().equals("white") ? "♕" : "♛";
            case "King" -> p.getColor().equals("white") ? "♔" : "♚";

            default -> "";
        };
    }

    // ================= SAVE GAME =================
    private void saveGame() {

        JFileChooser chooser = new JFileChooser();
        int option = chooser.showSaveDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {

            try (ObjectOutputStream out =
                         new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()))) {

                out.writeObject(board);
                out.writeObject(currentTurn);

                JOptionPane.showMessageDialog(this, "Game saved!");

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Save failed!");
            }
        }
    }

    //LOAD GAME 
    private void loadGame() {

        JFileChooser chooser = new JFileChooser();
        int option = chooser.showOpenDialog(this);

        if (option == JFileChooser.APPROVE_OPTION) {

            try (ObjectInputStream in =
                         new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))) {

                board = (Board) in.readObject();
                currentTurn = (String) in.readObject();

                updateBoardUI();

                JOptionPane.showMessageDialog(this, "Game loaded!");

            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Load failed!");
            }
        }
    }

    // UNDO
    private void undoMove() {

        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No moves to undo!");
            return;
        }

        board = history.pop();

        currentTurn = currentTurn.equals("white") ? "black" : "white";

        updateBoardUI();
    }
    private Board cloneBoard() {

    Board copy = new Board();

    for (int r = 0; r < 8; r++) {
        for (int c = 0; c < 8; c++) {

            Piece p = board.getPiece(new Position(r, c));

            if (p != null) {

                // rebuild piece WITHOUT clone/placePiece
                copy.movePiece(
                        new Position(r, c),
                        new Position(r, c)
                );
            }
        }
    }

    return copy;
}
    public static void main(String[] args) {
        new Chess();
    }
}