import java.util.Scanner;

public class Board {
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
    }

    public void printDimensions() {
        System.out.println(board.length + "x" + board[0].length + " on Connect " + connect);
    }

    private void switchPiece() {
        currentPiece = (currentPiece == 'x') ? 'o' : 'x';
    }

    public void insertPiece(Scanner scanner) {
        boolean flag = false;
        while (true) {
            int col = scanner.nextInt();
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
            if (flag) break;
        }
    }

    public boolean isDone() {
        if (movesMade == board.length * board[0].length)
            return true;
        if (checkHorizontal())
            return true;
        if (checkDiagonal())
            return true;
        if (checkVertical())
            return true;
        return false;
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


    public static void main(String[] args) {
        Scanner k = new Scanner(System.in);
        Board board = new Board(3, 3, 3, 1);
        board.printBoard();
        while (!board.isDone()) {
            board.insertPiece(k);
            board.printBoard();
        }
    }

}

