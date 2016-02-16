

public class AI_Astar {

	public static void main(String[] args) {
		
		Maze maze = new Maze(10,10);
		maze.printMaze();
		System.out.println(maze.startPoint);
		System.out.println(maze.targetPoint);
		maze.repeatedBAstar();
		System.out.println("");
		maze.printPath();
	}

}
