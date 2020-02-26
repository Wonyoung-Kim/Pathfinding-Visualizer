import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Utility {
    public static Random r = new Random(200);

    /**
     * generate an integer from 0 to bound(exclusive)
     * @param bound upper bound of the random number (exclusive)
     * @return random integer
     */
    public static int nextInt(int bound) {
        return r.nextInt(bound);
    }

    /**
     * Read a maze from a txt file
     * @param file txt file containing maze
     * @return a maze
     */
    public static Maze readMaze(File file) {
        FileReader fr;
        try {
            fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            Scanner s = new Scanner(line);
            int size = s.nextInt();
            s.close();

            Maze maze = new Maze(size);
            Rooms[][] room = maze.room;
            line = br.readLine();
            for(int i = 0; i< size; i++) {
                for(int j = 0; j< size; j++) {
                    if(line.charAt(2*j+1) != '-') {
                        room[i][j].northWall = false;
                        if(i>0)	room[i][j].neighbors.add(room[i-1][j]);
                    }
                }
                line = br.readLine();
                for(int j = 0; j<size; j++) {
                    if(line.charAt(2*j) != '|') {
                        room[i][j].westWall = false;
                        if(j>0)	room[i][j].neighbors.add(room[i][j-1]);

                    }
                    if(line.charAt(2*(j+1)) != '|') {
                        room[i][j].eastWall = false;
                        if(j<size-1)	room[i][j].neighbors.add(room[i][j+1]);

                    }
                }
                line = br.readLine();
                for(int j = 0; j<size; j++) {
                    if(line.charAt(2*j+1) != '-') {
                        room[i][j].southWall = false;
                        if(i<size-1)	room[i][j].neighbors.add(room[i+1][j]);

                    }
                }

            }

            br.close();
            fr.close();
            return maze;

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}