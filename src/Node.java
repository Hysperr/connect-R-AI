import java.util.ArrayList;

public class Node {
    private static int totalNumNodes = 0;
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




    public void operationDeepDive(int depth) {
        if (this.board_obj_field.getIsDone() || depth == 0) {
            return;
        }
        else {
            for (int i = 0; i < this.board_obj_field.getWidth(); i++) {
                if (this.board_obj_field.isColumnFilled(i)) continue;
                Node node = new Node(this);
                node.isMax = !node.isMax;
                node.board_obj_field.deepInsert(i);
                this.attach(node);

                if (depth == 1 || node.board_obj_field.getIsDone()) {
                    // heuristic calculation
                }

                // recursive call
                node.operationDeepDive(depth - 1);
                // resume here as the leaf 'node' from above


            }
        }
    }











}






