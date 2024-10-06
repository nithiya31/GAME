import java.util.*;

class TicTacToe {
    static char[][] board; // creation of empty 3x3 array

    public TicTacToe() // constructor
    {
        board = new char[3][3];
        initBoard(); // initializing call
    }

    void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static void displayBoard() {
        System.out.println("------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("------------");
        }
    }

    static void placeMark(int row, int col, char mark) {
        if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
            board[row][col] = mark;
        } else {
            System.out.println("Invalid position");
        }
    }

    static boolean checkColWin() {
        for (int j = 0; j <= 2; j++) {
            if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return true;
            }
        }
        return false;
    }

    static boolean checkRowWin() {
        for (int i = 0; i <= 2; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return true;
            }
        }
        return false;
    }

    static boolean checkDiagWin() {
        return (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])
                || (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
    }

    static boolean checkDraw() {
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}

abstract class Player {
    String name;
    char mark;

    Player(String name, char mark) {
        this.name = name;
        this.mark = mark;
    }

    abstract void makeMove(Scanner scan);

    boolean isValidMove(int row, int col) {
        if (row >= 0 && row <= 2 && col >= 0 && col <= 2) {
            return TicTacToe.board[row][col] == ' ';
        }
        return false;
    }
}

class HumanPlayer extends Player {
    HumanPlayer(String name, char mark) {
        super(name, mark);
    }

    void makeMove(Scanner scan) {
        int row, col;
        do {
            System.out.println("Enter the row and column:");
            row = scan.nextInt();
            col = scan.nextInt();
        } while (!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}

class AIPlayer extends Player {
    AIPlayer(String name, char mark) {
        super(name, mark);
    }

    void makeMove(Scanner scan) {
        int row, col;
        Random r = new Random();
        do {
            row = r.nextInt(3);
            col = r.nextInt(3);
        } while (!isValidMove(row, col));

        TicTacToe.placeMark(row, col, mark);
    }
}

public class Game {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        TicTacToe game = new TicTacToe();

        HumanPlayer p1 = new HumanPlayer("Nithiya", 'X');
        AIPlayer p2 = new AIPlayer("AI", 'O');

        Player currentPlayer = p1;

        while (true) {
            System.out.println(currentPlayer.name + "'s TURN");
            currentPlayer.makeMove(scan);
            TicTacToe.displayBoard();

            if (TicTacToe.checkColWin() || TicTacToe.checkRowWin() || TicTacToe.checkDiagWin()) {
                System.out.println(currentPlayer.name + " has won!");
                break;
            } else if (TicTacToe.checkDraw()) {
                System.out.println("Game is a draw.");
                break;
            } else {
                currentPlayer = (currentPlayer == p1) ? p2 : p1;
            }
        }

        scan.close();
    }
}
