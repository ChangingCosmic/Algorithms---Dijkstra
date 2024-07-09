import java.util.*;

public class Dijkstra{

    private String SOURCE;
    private String DEST;
    private int INFINITY = Integer.MAX_VALUE;

    // minimum priority queue
    private static PriorityQueue<Node> DI_QUEUE = new PriorityQueue(new Node.NodeComparator());
    private LinkedList <String> SHORTEST_PATH = new LinkedList <>();

    private final int[][] MATRIX = {
                 // A   J    M    R    K     S    I    N    T    D
            /*A*/  {0,  53,  10,  12,   0,   0,   0,   0,   0,   0},
            /*J*/  {53,  0,  33,   0,   2,   0, 101,   0,   0,   0},
            /*M*/  {10, 33,   0,   9,  30,  18,   0,   0,   0,   0},
            /*R*/  {12,  0,   9,   0,   0,  17,   0,   0,   6,   0},
            /*K*/  {0,   2,  30,   0,   0,  14, 123, 122,   0,   0},
            /*S*/  {0,   0,  18,  17,  14,   0,   0, 137,   7,   0},
            /*I*/  {0, 101,   0,   0, 123,   0,   0,   8,   0,  71},
            /*N*/  {0,   0,   0,   0, 122, 137,   8,   0, 145,  66},
            /*T*/  {0,   0,   0,   6,   0,   7,   0, 145,   0, 212},
            /*D*/  {0,   0,   0,   0,   0,   0,  71,  66, 212,   0}
    };

    private final String[] NAMES = {"A", "J", "M", "R", "K", "S", "I", "N", "T", "D"};
    private final Node[] NODE_ARR = {
            new Node(NAMES[0], INFINITY, null),
            new Node(NAMES[1], INFINITY, null),
            new Node(NAMES[2], INFINITY, null),
            new Node(NAMES[3], INFINITY, null),
            new Node(NAMES[4], INFINITY, null),
            new Node(NAMES[5], INFINITY, null),
            new Node(NAMES[6], INFINITY, null),
            new Node(NAMES[7], INFINITY, null),
            new Node(NAMES[8], INFINITY, null),
            new Node(NAMES[9], INFINITY, null)
    };

    public Dijkstra(String s, String d) {
        SOURCE = s;
        System.out.println("Source is " + s);

        DEST = d;
        System.out.println("Destination is " + d);
    }

    private static class Node{
        private String name;
        private int dist_to_source;
        private boolean visited;
        private Node prev_node;

        Node(String n, int dist, Node prev) {
            name = n;
            dist_to_source = dist;
            prev_node = prev;
            visited = false;
        }

        protected static class NodeComparator implements Comparator<Node> {
            @Override
            public int compare(Node node1, Node node2) {
                return Integer.compare(node1.dist_to_source, node2.dist_to_source);
            }
        }
    }

    private LinkedList <String> getShortestPath() {
        DI_QUEUE.add(new Node(SOURCE, 0, null));
        NODE_ARR[getIndex(new Node(SOURCE, 0, null))].dist_to_source = 0;

        Node lastNode = null;

        // go through the queue!!!!!!
        while (!DI_QUEUE.isEmpty( )) { // while this is NOT empty...
            Node currentNode = DI_QUEUE.poll( ); // removes an element from front of queue
            lastNode = currentNode;

            if(currentNode.name.equals(DEST)){
                DI_QUEUE.add(currentNode);
                break;
            }

            // get the currentNode's index for the matrix...
            int row = getIndex(currentNode);

            // need to look at the neighbors
            for(int col = 0; col < MATRIX.length; col++) {
                if(MATRIX[row][col] > 0 && !NODE_ARR[col].visited) {
                    // if there's something in the matrix and it's never been visited
                    int newDist = currentNode.dist_to_source + MATRIX[row][col];

                    // if this path is shorter than the neighborNode we're on
                    if(newDist < NODE_ARR[col].dist_to_source){
                        // update the distance and previous node of this neighborNode
                        NODE_ARR[col].dist_to_source = newDist;
                        NODE_ARR[col].prev_node = currentNode;

                        // need to check if it's in the queue already to know if remove or not
                        if(DI_QUEUE.contains(NODE_ARR[col])){
                            // if it DOES contain it need to remove it first to resort it
                            DI_QUEUE.remove(NODE_ARR[col]);
                        }
                        DI_QUEUE.add(NODE_ARR[col]);
                    }
                }
            }
            currentNode.visited = true;
        }

        // now build the path, working back
        Node current = lastNode;
        while(current != null) {
            SHORTEST_PATH.add(current.name);
            //System.out.println(current.name);
            current = current.prev_node;
        }
        return SHORTEST_PATH;
    }

    public void printShortestPath(){
        getShortestPath();
        printPrintPrint();
    }

    private void printPrintPrint(){
        String str = "";
        for(int i = SHORTEST_PATH.size() - 1; i >= 0; i--){
            str += SHORTEST_PATH.get(i) + " -> ";
        }
        str = str.substring(0, str.length() - 4);
        System.out.println(str);
    }

    /**
     * just looks for the index in the name array to match up with the matrix row
     *
     * @param n the node
     * @return index of the node in the names array (corresponding to the matrix)
     */
    private int getIndex(Node n) {
        int index = -1;
        for(int i = 0; i < NAMES.length; i++) {
            if(NAMES[i].equals(n.name)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
