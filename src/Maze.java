import java.util.*;

/**
 * Represents a Maze in a graph format
 */
public class Maze {

    Rooms firstRoom;//entrance of the maze
    Rooms lastRoom;//exit of the maze
    Rooms[][] room;//rooms in the maze

    private int size;//size of the maze
    private int time;
    private boolean pathSwitchOfDFS;
    private boolean numSwitchOfDFS;
    private boolean pathSwitchOfBFS;
    private boolean numSwitchOfBFS;

    private ArrayList<Rooms> nodesForDFS;
    private ArrayList<Rooms> nodesForBFS;
    ArrayList<Rooms> dfspath;
    ArrayList<Rooms> bfspath;
    private boolean isExist = false;

    /**
     * @param s
     */
    public Maze(int s) {
        nodesForDFS = new ArrayList<Rooms>();
        nodesForBFS = new ArrayList<Rooms>();
        dfspath = new ArrayList<>();
        bfspath = new ArrayList<>();

        pathSwitchOfDFS = false;
        numSwitchOfDFS = false;
        pathSwitchOfBFS = false;
        numSwitchOfBFS = false;
        time = 0;
        size = s;
        //initialzie a graph
        room = new Rooms[size][size];
        for (int row = 0; row < size; row++)
            for (int col = 0; col < size; col++) {
                room[row][col] = new Rooms(row, col);
                room[row][col].color = Colors.WHITE;
            }

        firstRoom = room[0][0];
        lastRoom = room[size-1][size-1];
    }

    /**
     *
     */
    public void generateMaze() {

        firstRoom.northWall = false;
        lastRoom.southWall = false;

        Stack<Rooms> stack = new Stack<Rooms>();
        int totalRoom = room.length * room.length;
        Rooms curRoom = room[0][0];
        curRoom.hasVisited = true;
        int visitedRoom = 1;
        while (visitedRoom < totalRoom) {
            ArrayList<Rooms> neighbors = getNeighbors(curRoom);
            if (neighbors.size() != 0) {
                int index = 0;
                index = Utility.nextInt(neighbors.size());
                Rooms neighborRoom = neighbors.get(index);
                curRoom.getRidOfWall(neighborRoom);
                stack.push(curRoom);
                curRoom = neighborRoom;
                curRoom.hasVisited = true;
                visitedRoom++;
            } else {
                curRoom = stack.pop();
            }
        }
    }

    /**
     * return an arraylist of rooms which is neighbor of node which has not been visited
     * @param node room
     * @return arraylist of rooms
     */
    private ArrayList<Rooms> getNeighbors(Rooms node) {
        ArrayList<Rooms> neighbors = new ArrayList<>();
        int r = node.row;
        int c = node.col;
        //we are getting neighbors here
        if (c - 1 >= 0 && room[r][c - 1].noHole() && !room[r][c - 1].hasVisited) {
            neighbors.add(room[r][c - 1]);
        }
        if (r + 1 < size && room[r + 1][c].noHole() && !room[r + 1][c].hasVisited) {
            neighbors.add(room[r + 1][c]);
        }
        if (c + 1 < size && room[r][c + 1].noHole() && !room[r][c + 1].hasVisited) {
            neighbors.add(room[r][c + 1]);
        }
        if (r - 1 >= 0 && room[r - 1][c].noHole() && !room[r - 1][c].hasVisited) {
            neighbors.add(room[r - 1][c]);
        }
        return neighbors;
    }

    /**
     * recursive way to build bfs
     */
    public void DFS() {
        if (firstRoom.color == Colors.WHITE) {
            DFS_Visit(firstRoom);
            isExist = false;
        }
        Rooms node = lastRoom;
        while(node != null) {
            dfspath.add(0, node);
            node.shortestPathDFS = true;
            node = node.pi;
        }

        firstRoom.shortestPathDFS = true;
    }

    /**
     * @param node
     */
    private void DFS_Visit(Rooms node) {
        //we are checking if the room is at lastRoom which we are about to exist
        if (!isExist) {
            nodesForDFS.add(node);
        }
        //the exit
        if (node.equals(lastRoom)) {
            isExist = true;
            nodesForDFS.add(node);
        }
        node.color = Colors.GREY;
        time++;
        node.d = time;
        for (Rooms m : node.neighbors) {
            if (m.color == Colors.WHITE) {
                m.pi = node;
                DFS_Visit(m);
            }
        }
        time++;
        node.f = time;
    }

    /**
     *
     */
    public void BFS() {
        //after DFS, initializing room with color WHITE
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                room[row][col].color = Colors.WHITE;
            }
        }
        int visit = 1;
        Rooms node = room[0][0];
        node.color = Colors.GREY;
        node.d = 0;
        node.distanceFromFirstRoom = 0;
        nodesForBFS.add(node);
        Queue<Rooms> q = new LinkedList<Rooms>();
        q.add(node);
        while (q.size() != 0) {
            node = q.poll();
            if (node.equals(lastRoom))
                isExist = true;
            for (Rooms room : node.neighbors)
                if (room.color == Colors.WHITE) {
                    room.pi = node;
                    if (room.equals(lastRoom))
                        isExist = true;
                    if (!isExist)
                        nodesForBFS.add(room);
                    room.color = Colors.GREY;
                    room.distanceFromFirstRoom = visit;
                    visit++;
                    q.add(room);
                }
        }
        nodesForBFS.add(lastRoom);
        node = lastRoom;
        while(node != null) {
            bfspath.add(0,node);
            node.shortestPathBFS = true;
            node = node.pi;
        }
        firstRoom.shortestPathBFS = true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
        String[][] chars = new String[size*2 + 1][size*2 + 1];
        String s = "";
        for(int row = 0; row < chars.length; row ++){
            for(int col = 0; col < chars.length; col ++){
                Rooms node = null;
                if(row % 2 == 1 && col % 2 == 1)
                    node = room[row /2][col/2];
                if((row == 0 && col == 1) || (row == size*2 && col == size*2 - 1))
                    chars[row][col] = "  ";
                else if(chars[row][col] == null && row % 2 == 0 && col % 2 == 0)
                    chars[row][col] = "+";
                else if(row == 0 || row == chars.length - 1)
                    chars[row][col] = "--";
                else if(row % 2 == 1 && (col == 0 || col == chars[0].length - 1))
                    chars[row][col] = "|";
                else if(node != null){
                    if(node.westWall)
                        chars[row][col - 1] = "|";
                    else
                        chars[row][col - 1] = " ";
                    if(node.eastWall)
                        chars[row][col + 1] = "|";
                    else
                        chars[row][col + 1] = " ";
                    if(node.northWall)
                        chars[row - 1][col] = "--";
                    if(node.southWall)
                        chars[row + 1][col] = "--";
                    if(numSwitchOfDFS && nodesForDFS.indexOf(node) < 10 && nodesForDFS.contains(node))
                        chars[row][col] = nodesForDFS.indexOf(node) + " ";
                    else if(numSwitchOfDFS && nodesForDFS.indexOf(node) >= 10 && nodesForDFS.contains(node))
                        chars[row][col] = nodesForDFS.indexOf(node) + "";
                    else if(pathSwitchOfDFS  && node.shortestPathDFS)
                        chars[row][col] = "##";
                    else if(numSwitchOfBFS && node.distanceFromFirstRoom < 10 && nodesForBFS.contains(node))
                        chars[row][col] = node.distanceFromFirstRoom.toString() + " ";
                    else if(numSwitchOfBFS && node.distanceFromFirstRoom >= 10 && nodesForBFS.contains(node))
                        chars[row][col] = node.distanceFromFirstRoom.toString() + "";
                    else if(pathSwitchOfBFS  && node.shortestPathBFS)
                        chars[row][col] = "##";
                    else
                        chars[row][col] = "  ";
                }
                else
                    chars[row][col] = "  ";
            }
        }
        for(int row = 0; row < chars.length; row ++){
            for(int col = 0; col < chars.length; col ++)
                s += chars[row][col];
            s += "\n";
        }
        return s;

    }
    /**
     *
     */
    public void printDFSSolution() {
        //Printer of DFS
        DFS();
        System.out.println("DFS " + size);
        numSwitchOfDFS = true;
        String numberRepresentationOfDFS = this.toString();
        numSwitchOfDFS = false;
        System.out.println(numberRepresentationOfDFS);
        System.out.println(" ");
        pathSwitchOfDFS = true;
        String pathRepresantationOfDFS = this.toString();
        pathSwitchOfDFS = false;
        System.out.println(pathRepresantationOfDFS);
        System.out.println(" ");
        System.out.print("Path: (0,0)");
        for(int i =1; i<dfspath.size(); i++) {
            Rooms r = dfspath.get(i);
            System.out.print(", (" + r.row + ", " + r.col + ")");
        }
        System.out.print("\n");
        System.out.println("Length of path: " + dfspath.size());
        System.out.println("Visited cells:" + nodesForDFS.size());

    }
    /**
     *
     */
    public void printBFSSolution() {
        BFS();
        System.out.println("BFS " + size);
        numSwitchOfBFS = true;
        String numRepresantationOfBFS = this.toString();
        numSwitchOfBFS = false;
        System.out.println(numRepresantationOfBFS);
        pathSwitchOfBFS = true;
        String pathRepresantationOfBFS = this.toString();
        pathSwitchOfBFS = false;
        System.out.println(pathRepresantationOfBFS);
        System.out.print("Path: (0,0)");
        for(int i =1; i<bfspath.size(); i++) {
            Rooms r = bfspath.get(i);
            System.out.print(", (" + r.row + ", " + r.col + ")");
        }
        System.out.print("\n");
        System.out.println("Length of path: " + bfspath.size());
        System.out.println("Visited cells:" + nodesForBFS.size());
        System.out.println(" ");


    }

}
