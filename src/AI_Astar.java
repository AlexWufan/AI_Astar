

public class AI_Astar {

	public static void main(String[] args) {
		int count = 0;
		

		Maze maze = new Maze(10,10);
		//maze.initTestCase();
		maze.printMaze();
		System.out.println(maze.startPoint);
		System.out.println(maze.targetPoint);	
		maze.adaptiveAstar();
		int a = maze.numOfExpandNodes;
		System.out.println("");
		maze.repeatedFAstar();
		int b = maze.numOfExpandNodes;
		System.out.println("");
//		maze.repeatedBAstar();
	}

		
	

}
