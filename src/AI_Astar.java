

public class AI_Astar {

	public static void main(String[] args) {
		int count = 0;

		int equal = 0;
		
//		for(int i = 0; i < 100; ++i)
//		{
		Maze maze = new Maze(101,101);
		maze.initTestCase();

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

//		if(maze.success)
//		{
//		if(b > a)
//			count++;
//		else if(b == a)
//			equal++;
//		else
//		{
//			System.out.println("*");
//		}
//		}
//		}
//
//		System.out.println(count);
//		System.out.println(equal);
	}

}