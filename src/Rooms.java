import java.util.ArrayList;

class Rooms {
    //walls
    boolean northWall;
    boolean southWall;
    boolean eastWall;
    boolean westWall;

    //location
    int row;
    int col;

    boolean hasVisited;//if the room is visited or not
    ArrayList<Rooms> neighbors;//neighbor rooms of this room

    //for bfs and dfs
    Colors color;
    Integer d;
    Integer f;
    Integer distanceFromFirstRoom;
    boolean shortestPathDFS = false;
    boolean shortestPathBFS = false;
    Rooms pi = null;

    /**
     * Constructor Rooms
     * @param r row
     * @param c column
     */
    public Rooms(int r, int c) {
        neighbors = new ArrayList<Rooms>();
        color = Colors.WHITE;
        hasVisited = false;
        westWall = true;
        eastWall = true;
        northWall = true;
        southWall = true;
        row = r;
        col = c;
        distanceFromFirstRoom = 0;
        d = 0;
        f = 0;

    }

    /**
     * if the maze has neighbors or not
     * @return
     */
    public boolean noHole(){
        return neighbors.size() == 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj){
        Rooms compared = (Rooms)obj;
        return compared.row == row && compared.col == col;
    }

    /**
     * git rid of walls of the room
     * @param room the neighbor room of this room
     */
    public void getRidOfWall(Rooms room){
        //west neighbor
        if(room.row == row && room.col == col - 1){
            neighbors.add(room);
            room.neighbors.add(this);
            this.westWall = false;
            room.eastWall = false;
        }
        //south neighbor
        else if(room.col == col && room.row == row + 1){
            neighbors.add(room);
            room.neighbors.add(this);
            this.southWall = false;
            room.northWall = false;
        }
        else if(room.col == col && room.row == row + 1){
            neighbors.add(room);
            room.neighbors.add(this);
            this.southWall = false;
            room.northWall = false;
        }
        //east neighbor
        else if(room.row == row && room.col == col + 1){
            neighbors.add(room);
            room.neighbors.add(this);
            this.eastWall = false;
            room.westWall = false;
        }
        //north neighbor
        else if(room.col == col && room.row == row - 1){
            neighbors.add(room);
            room.neighbors.add(this);
            this.northWall = false;
            room.southWall = false;
        }
    }

}