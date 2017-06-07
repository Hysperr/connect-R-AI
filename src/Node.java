import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Node {
    private static int totalNumNodes = 0;
    public  static int DEPTH = 2;
    private ArrayList<Node> arrayList;
    private Board board_obj_field;
    private int id;
    private int bestMove;
    private boolean isMax;
    private int numChildren;

    public Node(Board aboard) {
        arrayList = new ArrayList<>();
        board_obj_field = aboard;
        totalNumNodes++;
        id = totalNumNodes;
        this.id = totalNumNodes;
    }

    public Node(Node anode) {
        this.arrayList = new ArrayList<>();
        this.isMax = anode.isMax;
        this.board_obj_field = new Board (
                anode.board_obj_field.getHeight(),
                anode.board_obj_field.getWidth(),
                anode.board_obj_field.getConnect(),
                anode.board_obj_field.getCurrentPlayer());
        for (int i = 0; i < anode.board_obj_field.getHeight(); i++) {
            for (int j = 0; j < anode.board_obj_field.getWidth(); j++) {
                this.board_obj_field.getBoardActual()[i][j] = anode.board_obj_field.getBoardActual()[i][j];
            }
        }
        totalNumNodes++;
        this.id = totalNumNodes;
    }

    public void attach(Node nn) {
        arrayList.add(nn);
        numChildren++;
    }
    public void printNode() {
        board_obj_field.printBoard();
    }
    public void printImmediateChildren() {
        for (Node a : getArrayList())
            a.printNode();
    }
    public int getId() { return id; }

    public int getBestMove() {
        return bestMove;
    }

    public int getNumChildren() { return numChildren; }

    public ArrayList<Node> getArrayList() { return arrayList; }

    public Board getBoard_obj_field() { return board_obj_field; }


    public void opDD(int depth) {
        Stack<Node> stack = new Stack<>();
        stack.push(this);
        for (int i = 0; i < board_obj_field.getWidth(); i++) {
            if (stack.peek().board_obj_field.isColumnFilled(i)) continue;
            for (int j = 0; j < depth; j++) {
                if (stack.peek().board_obj_field.isColumnFilled(i)) continue;
                Node sub = new Node(stack.peek());
                sub.isMax = !stack.peek().isMax;
                sub.board_obj_field.deepInsert(i);
                sub.bestMove = i;
                stack.peek().attach(sub);

            }
        }
    }


    public ArrayList<Integer> operationDeepDive(ArrayList<Integer> aL, int depth) {
        if (this.board_obj_field.getIsDone() || depth == 0) {
            return aL;
        }
        else {
            for (int i = 0; i < this.board_obj_field.getWidth(); i++) {
                if (this.board_obj_field.isColumnFilled(i)) {
                    aL.add(i);
                    continue;
                }
                Node sub = new Node(this);
                sub.isMax = !sub.isMax;
                sub.bestMove = i;
                this.bestMove=sub.bestMove;
                sub.board_obj_field.deepInsert(i);
                this.attach(sub);

                if (depth == 1 || sub.board_obj_field.getIsDone()) {
                    // heuristic calculation
                    sub.board_obj_field.setHeuristic(sub.board_obj_field.calculate_deep_dive_heuristic());
                }

                aL = sub.operationDeepDive(aL, depth - 1);

                // `this` is calling node
                if (this.isMax) {
//                    System.out.println("MAX " + depth);
                    if (sub.board_obj_field.getHeuristic() >= this.board_obj_field.getHeuristic()) {
                        this.board_obj_field.setHeuristic(sub.board_obj_field.getHeuristic());
//                        this.bestMove = sub.bestMove;
                    }
                }
                else {
//                    System.out.println("NOT MAX " + depth);
                    if (sub.board_obj_field.getHeuristic() <= this.board_obj_field.getHeuristic()) {
                        this.board_obj_field.setHeuristic(sub.board_obj_field.getHeuristic());
//                        this.bestMove = sub.bestMove;
                    }
                }
            }
            if (depth == DEPTH) {
                int g = 0; double max = -10000;
                for (Node a : this.arrayList) {
                    if(a.board_obj_field.getHeuristic() > max){
                        this.bestMove = g;
                        max = a.board_obj_field.getHeuristic();
                    }
                    g++;
                }
            }
        }
    }
}
