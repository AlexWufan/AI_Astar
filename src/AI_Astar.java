

public class AI_Astar {

	public static void main(String[] args) {
		int count = 0;
		int smaller = 0;
		int equal = 0;

		for(int i = 0; i < 1; ++i)
		{
		Maze maze = new Maze(101, 101);
		//maze.initTestCase();
		//maze.printMaze();
		System.out.println("");
		System.out.println("startpoint and targetpoint");
		System.out.println(maze.startPoint);
		System.out.println(maze.targetPoint);

		System.out.println("Adaptive A* ");
		maze.adaptiveAstar();
//		int b = maze.numOfExpandNodes;
		System.out.println("");

		System.out.println("Repeated forward A* ");
		maze.repeatedFAstar();
		int a = maze.numOfExpandNodes;
		System.out.println("");

		System.out.println("Repeated forward A* small g-value");
		maze.repeatedFAstar2();
		int b = maze.numOfExpandNodes;

		System.out.println("");
		System.out.println("Repeated backward A* ");
		maze.repeatedBAstar();
//		int b = maze.numOfExpandNodes;

		if (maze.success) {
			if (b > a)
			count++;
			else if (b == a)
			equal++;
			else {
			smaller++;
			//System.out.println("*");
			}
		}
	}


		System.out.println(count);
		System.out.println(equal);
		System.out.println(smaller);
	}


}