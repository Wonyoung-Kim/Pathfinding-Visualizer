import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class MazeTest {

    /**
     * test 4 generated maze and solve them using dfs and bfs
     */
    @Test
    void test() {
        for(int i = 4; i<=10; i++) {
            Maze maze1 = new Maze(i);
            maze1.generateMaze();
            System.out.println(maze1.toString());
            maze1.printDFSSolution();
            maze1.printBFSSolution();
            assertEquals(maze1.dfspath, maze1.bfspath);
        }
    }

    /**
     *
     */
    @Test
    void solveMazeFiles() {
        ArrayList<File> mazeFiles = new ArrayList<>();
        mazeFiles.add(new File("data/maze4.txt"));
        mazeFiles.add(new File("data/maze6.txt"));
        mazeFiles.add(new File("data/maze8.txt"));
        mazeFiles.add(new File("data/maze10.txt"));
        mazeFiles.add(new File("data/maze20.txt"));

        for(File file: mazeFiles) {
            Maze maze2 = Utility.readMaze(file);
            System.out.println(maze2.toString());
            maze2.printDFSSolution();
            maze2.printBFSSolution();
            assertEquals(maze2.dfspath, maze2.bfspath);
        }
    }

    @Test
    void runtimeTester() {
        for(int i = 4; i<=10; i++) {
            Maze maze1 = new Maze(i);
            System.out.println("Maze size of " + i + "\n");
            Long start = System.currentTimeMillis();
            maze1.generateMaze();
            System.out.println(maze1.toString());
            maze1.printDFSSolution();
            Long finishingOfDFS = System.currentTimeMillis();
            timeOfDFS.add((finishingOfDFS - start));
        }
        for(int i = 4; i<=10; i++) {
            Maze maze1 = new Maze(i);
            System.out.println("Maze size of " + i + "\n");
            Long start = System.currentTimeMillis();
            maze1.generateMaze();
            System.out.println(maze1.toString());
            maze1.printBFSSolution();
            Long finishingOfBFS = System.currentTimeMillis();
            timeOfBFS.add((finishingOfBFS - start));
        }

        System.out.println("Runtime Anaysis of maze size from 4 to 10" + "\n");
        for (int i = 0; i < timeOfBFS.size(); i++) {
            System.out.println("Maze size of " + start);
            System.out.println("Running time of DFS " + " -> " + timeOfDFS.get(i));
            System.out.println("Running time of BFS " + " -> " + timeOfBFS.get(i) + "\n");
            start++;
        }
    }
    private int start = 4;
    private ArrayList<Long> timeOfDFS = new ArrayList<>();
    private ArrayList<Long> timeOfBFS = new ArrayList<>();
}
