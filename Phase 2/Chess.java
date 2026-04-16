import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.Stack;
import javax.swing.*;

// Phase 2: Chess Game Project

public class Chess extends JFrame {

    private final JButton[][] squares = new JButton[8][8];
    private String[][] board = new String[8][8];

    private int dragStartRow = -1;
    private int dragStartCol = -1;

    private final Stack<String[][]> history = new Stack<>();

    public Chess() {
        setTitle("Chess Game - Phase 2");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initMenu();
        initBoard();
        initializePieces();
        updateBoardUI();

        setVisible(true);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Options");

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(e -> resetBoard());

        JMenuItem saveGame = new JMenuItem("Save Game");
        saveGame.addActionListener(e -> saveGame());

        JMenuItem loadGame = new JMenuItem("Load Game");
        loadGame.addActionListener(e -> loadGame());

        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(e -> undoMove());

        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        gameMenu.add(loadGame);
        gameMenu.add(undo);

        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
    }

    private void initBoard() {
        JPanel panel = new JPanel(new GridLayout(8, 8));

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                JButton square = new JButton();

                // Board colors
                if ((row + col) % 2 == 0) {
                    square.setBackground(new Color(240, 217, 181));
                } else {
                    square.setBackground(new Color(181, 136, 99));
                }

                // Style
                square.setFont(new Font("Segoe UI Symbol", Font.BOLD, 64));
                square.setFocusPainted(false);
                square.setBorderPainted(false);
                square.setMargin(new Insets(0, 0, 0, 0));
                square.setHorizontalAlignment(SwingConstants.CENTER);
                square.setVerticalAlignment(SwingConstants.CENTER);

                final int r = row;
                final int c = col;

                // Drag & Drop
                square.setTransferHandler(new PieceTransferHandler(r, c));
                square.addMouseListener(new DragMouseAdapter());

                squares[row][col] = square;
                panel.add(square);
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        String piece = board[fromRow][fromCol];
        String target = board[toRow][toCol];

        if (target != null && isKing(target)) {
            showWinner(piece);
        }

        board[toRow][toCol] = piece;
        board[fromRow][fromCol] = null;

        updateBoardUI();
    }

    private boolean isKing(String piece) {
        return piece.equals("wK") || piece.equals("bK");
    }

    private void showWinner(String piece) {
        String winner = piece.startsWith("w") ? "White" : "Black";
        JOptionPane.showMessageDialog(this, winner + " wins!");
        System.exit(0);
    }

    private void updateBoardUI() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (board[r][c] == null) {
                    squares[r][c].setText("");
                } else {
                    squares[r][c].setText(getSymbol(board[r][c]));
                }
            }
        }
    }

    private String getSymbol(String piece) {
        return switch (piece) {
            case "wK" -> "♔";
            case "wQ" -> "♕";
            case "wR" -> "♖";
            case "wB" -> "♗";
            case "wN" -> "♘";
            case "wP" -> "♙";
            case "bK" -> "♚";
            case "bQ" -> "♛";
            case "bR" -> "♜";
            case "bB" -> "♝";
            case "bN" -> "♞";
            case "bP" -> "♟";
            default -> "";
        };
    }

    private void initializePieces() {
        for (int r = 0; r < 8; r++)
            for (int c = 0; c < 8; c++)
                board[r][c] = null;

        for (int i = 0; i < 8; i++) {
            board[1][i] = "bP";
            board[6][i] = "wP";
        }

        board[0][0] = board[0][7] = "bR";
        board[7][0] = board[7][7] = "wR";

        board[0][1] = board[0][6] = "bN";
        board[7][1] = board[7][6] = "wN";

        board[0][2] = board[0][5] = "bB";
        board[7][2] = board[7][5] = "wB";

        board[0][3] = "bQ";
        board[7][3] = "wQ";

        board[0][4] = "bK";
        board[7][4] = "wK";
    }

    private void resetBoard() {
        history.clear();
        initializePieces();
        updateBoardUI();
    }

    private void undoMove() {
        if (!history.isEmpty()) {
            board = history.pop();
            updateBoardUI();
        }
    }

    private String[][] copyBoard() {
        String[][] newBoard = new String[8][8];
        for (int r = 0; r < 8; r++)
            System.arraycopy(board[r], 0, newBoard[r], 0, 8);
        return newBoard;
    }

    private void saveGame() {
        try {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                ObjectOutputStream out = new ObjectOutputStream(
                        new FileOutputStream(chooser.getSelectedFile()));
                out.writeObject(board);
                out.close();
                JOptionPane.showMessageDialog(this, "Game saved!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Save failed.");
        }
    }

    private void loadGame() {
        try {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                ObjectInputStream in = new ObjectInputStream(
                        new FileInputStream(chooser.getSelectedFile()));
                board = (String[][]) in.readObject();
                in.close();
                updateBoardUI();
                JOptionPane.showMessageDialog(this, "Game loaded!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load failed.");
        }
    }

    class DragMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            JButton btn = (JButton) e.getSource();

            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    if (squares[r][c] == btn) {
                        dragStartRow = r;
                        dragStartCol = c;
                    }
                }
            }

            if (!btn.getText().equals("")) {
                btn.getTransferHandler().exportAsDrag(btn, e, TransferHandler.MOVE);
            }
        }
    }

    class PieceTransferHandler extends TransferHandler {

        int row, col;

        PieceTransferHandler(int r, int c) {
            row = r;
            col = c;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            JButton btn = (JButton) c;
            return new StringSelection(btn.getText());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            // 🔥 CRITICAL FIX: prevents Swing from deleting the piece
        }

        @Override
        public boolean canImport(TransferSupport support) {
            if (!support.isDrop()) return false;
            if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) return false;
            support.setShowDropLocation(true);
            return true;
        }

        @Override
        public boolean importData(TransferSupport support) {
            try {
                if (dragStartRow == row && dragStartCol == col) return false;

                history.push(copyBoard());

                movePiece(dragStartRow, dragStartCol, row, col);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Chess::new);
    }
}