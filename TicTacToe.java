import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';

    public static void main(String[] args) {
        initializeBoard();
        char winner = '\0';

        while (winner == '\0') {
            displayBoard();
            if (currentPlayer == 'X') {
                getPlayerMove();
                winner = checkWinner('X');
            } else {
                getComputerMove();
                winner = checkWinner('O');
            }
            switchPlayer();
        }

        displayBoard();
        if (winner == 'D') {
            System.out.println("It's a draw!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }
    }

    private static void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void displayBoard() {
        System.out.println("  0 1 2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("  -+-+-");
            }
        }
        System.out.println();
    }

    private static void getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col;
        do {
            System.out.print("Player " + currentPlayer + ", enter row (0, 1, or 2): ");
            row = scanner.nextInt();
            System.out.print("Player " + currentPlayer + ", enter column (0, 1, or 2): ");
            col = scanner.nextInt();
        } while (!isValidMove(row, col));

        board[row][col] = currentPlayer;
    }

    private static void getComputerMove() {
        // Simple AI to choose next move
        int[] move = findBestMove();
        board[move[0]][move[1]] = currentPlayer;
        System.out.println("Computer plays O at (" + move[0] + ", " + move[1] + ")");
    }

    private static int[] findBestMove() {
        // Check if a win is possible
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O';
                    if (checkWinner('O') == 'O') {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }

        // Try to block X from winning if a win is not possible
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'X';
                    if (checkWinner('X') == 'X') {
                        board[i][j] = ' ';
                        return new int[]{i, j};
                    }
                    board[i][j] = ' ';
                }
            }
        }

        // Prioritize corners and center
        int[][] preferredMoves = {{0, 0}, {0, 2}, {2, 0}, {2, 2}, {1, 1}};
        for (int[] move : preferredMoves) {
            if (board[move[0]][move[1]] == ' ') {
                return new int[]{move[0], move[1]};
            }
        }

        // Pick first available move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{-1, -1};   // fallback value
    }

    private static boolean isValidMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != ' ') {
            System.out.println("Invalid move! Try again.");
            return false;
        }
        return true;
    }

    private static char checkWinner(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return player; // Row win
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return player; // Column win
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return player; // Diagonal win
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return player; // Diagonal win
        }

        // Check for a draw
        boolean isBoardFull = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    isBoardFull = false;
                    break;
                }
            }
        }

        return isBoardFull ? 'D' : '\0'; // 'D' for Draw, '\0' for no winner yet
    }

    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}

