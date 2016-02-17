

public class AI_Astar {

	public static void main(String[] args) {
		int count = 0;
		

		Maze maze = new Maze(101,101);
		//maze.initTestCase();
		for(int i = 0; i < 100; ++i)
		{
		maze.printMaze();
		System.out.println(maze.startPoint);
		System.out.println(maze.targetPoint);	
		maze.adaptiveAstar();
		int a = maze.numOfExpandNodes;
		System.out.println("");
		maze.repeatedFAstar();
		int b = maze.numOfExpandNodes;
		System.out.println("");
		maze.repeatedBAstar();
		if(b > a)
			count++;
		}
		System.out.println(count);
	}

		
	

}
