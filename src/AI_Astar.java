

public class AI_Astar {

	public static void main(String[] args) {
		
		Maze maze = new Maze(5,5);
		maze.initTestCase();
		maze.printMaze();
		System.out.println(maze.startPoint);
		System.out.println(maze.targetPoint);	
		maze.adaptiveAstar();
		System.out.println("");
		maze.repeatedFAstar();
		System.out.println("");
		//maze.repeatedBAstar();
	}

}
