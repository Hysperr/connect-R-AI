import java.util.*;

public class Node {
    private static int totalNumNodes = 0;
    private ArrayList<Node> arrayList;
    private Board board_obj_field;
    private int id;
    private int bestMove;
    private boolean isMax = true;
    private int numChildren;

    /**
     * Copy constructor
     * @param aboard
     */
    public Node(Board aboard) {
        arrayList = new ArrayList<>();
        board_obj_field = aboard;
        totalNumNodes++;
        id = totalNumNodes;
//        this.id = totalNumNodes;
    }

    /**
     * Deep copy constructor
     * @param anode
     */
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

    /** getters */

    public int getId() { return id; }

    public int getBestMove() { return bestMove; }

    public int getNumChildren() { return numChildren; }

    public ArrayList<Node> getArrayList() { return arrayList; }

    public Board getBoard_obj_field() { return board_obj_field; }

    /** member functions */

    public void attach(Node nn) {
        arrayList.add(nn);
        numChildren++;
    }

    /**
     * Prints all node's contents.
     */
    public void printNode() {
        for (int i = 0; i < board_obj_field.getHeight(); i++) {
            for (int j = 0; j < board_obj_field.getWidth(); j++) {
                System.out.print(board_obj_field.getBoardActual()[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("Is max? " + isMax +
                "\nNum children: " + numChildren +
                "\nBest move: " + bestMove +
                "\nIs done? " + board_obj_field.getIsDone() +
                "\nHeuristic: " + board_obj_field.getHeuristic() +
                '\n');
    }

    /**
     * Print the immediate children of calling node.
     * Calls <code>printNode()</code>
     */
    public void printImmediateChildren() {
        System.out.println("My children are...");
        for (Node a : getArrayList())
            a.printNode();
    }

    private void setDefaultNodeHeuristic() {
        if (isMax) board_obj_field.setHeuristic(Integer.MIN_VALUE);
        else board_obj_field.setHeuristic(Integer.MAX_VALUE);
    }

/*    public void opDD(int depth) {
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
    }*/

/*    *//**
     * Creates MiniMax Tree for AI agent to "look ahead for best move"
     * @param depth
     *//*
    public void createMiniMaxTree_1(int depth) {
        if (board_obj_field.getIsDone() || depth == 0) {
            return;
        }
        else {
            for (int i = 0; i < board_obj_field.getWidth(); i++) {
                if (board_obj_field.isColumnFilled(i)) continue;
                Node sub = new Node(this);
                sub.isMax = !sub.isMax;
                sub.bestMove = i;
//                this.bestMove = sub.bestMove;
                sub.board_obj_field.deepInsert(i);
                this.attach(sub);

                if (depth == 1 || sub.board_obj_field.getIsDone()) {
                    // heuristic calculation
                    sub.board_obj_field.setHeuristic(sub.board_obj_field.calculate_deep_dive_heuristic());
                }

                *//** Recursion, bringing best move upwards through tree *//*
                sub.createMiniMaxTree_1(depth - 1);

                // 'this' is calling node, 'sub' is child

            }
        }
    }*/



    /**
     * Creates MiniMax Tree for AI agent to "look ahead for best move"
     * @param depth
     */
    public void createMiniMaxTree(int depth, int count) {
        if (board_obj_field.getIsDone() || depth == 0) {
            return;
        }
        else {
            for (int i = 0; i < board_obj_field.getWidth(); i++) {
                this.setDefaultNodeHeuristic();
                if (board_obj_field.isColumnFilled(i)) continue;
                Node sub = new Node(this);
                sub.isMax = !sub.isMax;
                sub.setDefaultNodeHeuristic();
//                sub.bestMove = i;
//                this.bestMove = sub.bestMove;
                sub.board_obj_field.deepInsert(i);
                this.attach(sub);

                if (depth == 1 || sub.board_obj_field.getIsDone()) {
                    // heuristic calculation
                    sub.board_obj_field.setHeuristic(sub.board_obj_field.calculate_deep_dive_heuristic());
                }

                /** Recursion */
                sub.createMiniMaxTree(depth - 1, count + 1);

                // 'this' is calling node, 'sub' is child

                if (this.isMax) {
                    if (sub.board_obj_field.getHeuristic() > this.board_obj_field.getHeuristic()) {
                        this.board_obj_field.setHeuristic(sub.board_obj_field.getHeuristic());
                    }
                }
                else {
                    if (sub.board_obj_field.getHeuristic() < this.board_obj_field.getHeuristic()) {
                        this.board_obj_field.setHeuristic(sub.board_obj_field.getHeuristic());
                    }
                }
            }

            // if count == 0, 'this' is the root and max level. go through all children, best move is
            // child with highest value.
            if (count == 0) {
                for (int k = 0; k < this.arrayList.size(); k++) {
                    if (this.arrayList.get(k).board_obj_field.getHeuristic() > this.board_obj_field.getHeuristic()) {
                        this.board_obj_field.setHeuristic(this.arrayList.get(k).board_obj_field.getHeuristic());
                        this.bestMove = k;
                    }
                }
                System.out.println("THE BEST MOVE IS " + this.bestMove);
            }
        }
    }







    /*
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
    */

    /**
     * BFS print MiniMaxTree. Root is always printed first.
     * @param root
     */
    public static void printMiniMaxTree(Node root) {
        System.out.println("Printing MiniMax Tree BREADTH FIRST");
        int count = 0;
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            count++;
            Node tmp = queue.peek();
            tmp.printNode();
            queue.poll();
            if (tmp.getNumChildren() != 0) {
                for (Node a : tmp.getArrayList()) {
                    queue.add(a);
                }
            }
        }
        System.out.println("TOTAL NODES: " + count);
    }
}
