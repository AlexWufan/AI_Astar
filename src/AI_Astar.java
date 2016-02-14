

public class AI_Astar {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Maze maze = new Maze(101,101);
		for(int i = 0; i < maze.grid.length; i++)
		{
			for(int j = 0; j < maze.grid[0].length; j++)
			{
				if(maze.grid[i][j] == false)
				{
					System.out.print("1");
				}
				else
				{
					System.out.print("0");
				}		
			}	
			System.out.println("");
		}
	}

}
