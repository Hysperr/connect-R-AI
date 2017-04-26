import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class Board {
    public enum GameType { AI, HUMAN, PRNG }
    private char [][] board;
    private int connect;
    private int currentPlayer;
    private boolean isDone;
    private int movesMade;
    private char currentPiece;
    private int pRow;
    private int pCol;
    private char pPie;

    public Board(int row, int col, int connect, int currentPlayer) {
        this.currentPiece = (currentPlayer == 1) ? 'x' : 'o';
        this.currentPlayer = currentPlayer;
        this.connect = connect;
        this.isDone = false;
        this.movesMade = 0;
        board = createBoard(row, col);
    }

    private char[][] createBoard(int row, int col) {
        char [][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = '-';
            }
        }
        return board;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printDimensions() {
        System.out.println(board.length + "x" + board[0].length + " on Connect " + connect);
    }

    private void switchPiece() {
        currentPiece = (currentPiece == 'x') ? 'o' : 'x';
    }

    private void switchCurrentPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    public char[][] getBoardActual() { return board; }

    public int getWidth() {
        return board[0].length;
    }

    public int getHeight() {
        return board.length;
    }

    public long getMovesMade() {
        return movesMade;
    }

    public boolean getIsDone() { return isDone; }

    public int getConnect() {
        return connect;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public char getOpponentPiece() {
        return (currentPiece == 'x') ? 'o' : 'x';
    }

    public void insertPiece(GameType gameType) {
        boolean flag = false;
        int col;

        while (true) {

            switch(gameType) {
                case AI:
                    col = 2;
                    break;
                case PRNG:
                    col = new Random().nextInt(board[0].length);
                    break;
                case HUMAN:
                    col = validateHumanInsert();
                    break;
                default:
                    throw new IllegalArgumentException("I don't have an option for " + gameType);
            }

            for (int i = board.length - 1; i >= 0; i--) {
                if (board[i][col] == '-') {
                    board[i][col] = currentPiece;
                    pPie = currentPiece;
                    switchPiece();
                    flag = true;
                    movesMade++;
                    pCol = col;
                    pRow = i;
                    break;
                }
            }
            if (flag) {
                checkWin();
                break;
            }
            else System.out.println("Column " + col + " already filled. Try again. Still player " + currentPlayer + "'s turn.\n");
        }

        System.out.println("Player " + currentPlayer + " placed '" + pPie + "' in col " + col);
        switchCurrentPlayer();

    }

    private boolean checkWin() {
        if ((movesMade == board.length * board[0].length)
                || checkHorizontal()
                || checkDiagonal()
                || checkVertical()) {
            isDone = true;
            return true;
        }
        return false;
    }

    private void printWinnerStats() {
        // print winner stats if checkwin() true, not in deep insert
    }

    private boolean checkHorizontal() {
        int in_a_row = 0;
        // east
        for (int j = pCol; j < board[0].length; j++) {
            if (board[pRow][j] == pPie) in_a_row++;
            else break;
        }
        if (connect == in_a_row) {
            return true;
        }
        // west
        for (int j = pCol - 1; j >= 0; j--) {
            if (board[pRow][j] == pPie) in_a_row++;
            else break;
        }
        if (connect == in_a_row) {
            return true;
        }
        return false;
    }

    private boolean checkVertical() {
        int in_a_row = 0;
        // south
        for (int i = pRow; i < board.length; i++) {
            if (board[i][pCol] == pPie) in_a_row++;
            else break;
        }
        if (connect == in_a_row) {
            return true;
        }
        return false;
    }

    private boolean checkDiagonal() {
        int in_a_row = 0, i, j;
        // NE
        j = pCol;
        for (i = pRow; i >= 0; i--) {
            if (j < board[0].length) {
                if (board[i][j] == pPie) in_a_row++;
                else break;
            }
            else break;
            j++;
        }
        if (connect == in_a_row) {
            return true;
        }
        // SW
        j = pCol - 1;
        for (i = pRow + 1; i < board.length; i++) {
            if (j >= 0) {
                if (board[i][j] == pPie) in_a_row++;
                else break;
            }
            else break;
            j--;
        }
        if (connect == in_a_row) {
            return true;
        }
        // RESET FOR NEW SLOPE
        in_a_row = 0;
        // NW
        j = pCol;
        for (i = pRow; i >= 0; i--) {
            if (j >= 0) {
                if (board[i][j] == pPie) in_a_row++;
                else break;
            }
            else break;
            j--;
        }
        if (connect == in_a_row) {
            return true;
        }
        // SE
        j = pCol + 1;
        for (i = pRow + 1; i < board.length; i++) {
            if (j < board[0].length) {
                if (board[i][j] == pPie) in_a_row++;
                else break;
            }
            else break;
            j++;
        }
        if (connect == in_a_row) {
            return true;
        }

        return false;
    }

    private int validateHumanInsert() {
        int a;
        while (true) {
            try {
                a = new Scanner(System.in).nextInt();
                if (a >= 0 && a < board[0].length)
                    return a;
                else
                    System.out.println("Invalid index. Must be between [0 and " + (board[0].length - 1) + "]");
            }
            catch (InputMismatchException e) {
                System.out.println("That's not a number.");
            }
        }
    }

    public boolean isColumnFilled(int col) {
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][col] == '-')
                return false;
        }
        return true;
    }

    public void deepInsert(int col) {
        boolean flag = false;
        for (int i = board.length - 1; i >= 0; i--) {
            if (board[i][col] == '-') {
                board[i][col] = currentPiece;
                pPie = currentPiece;
                switchPiece();
                flag = true;
                movesMade++;
                pCol = col;
                pRow = i;
                break;
            }
        }
        checkWin();
        System.out.println("Player " + currentPlayer + " placed '" + pPie + "' in col " + col);     // for debug
        switchCurrentPlayer();

    }

    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        Board board = new Board(3, 3, 3, 1);
        board.printBoard();
        while (true) {
            board.insertPiece(GameType.HUMAN);
            board.printBoard();
            if (board.getIsDone()) break;

            board.insertPiece(GameType.PRNG);
            board.printBoard();
            if (board.getIsDone()) break;
        }
    }

}

