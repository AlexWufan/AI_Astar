

public class AI_Astar {

	public static void main(String[] args) {
		
		Maze maze = new Maze(101,101);
		for(int i = 0; i < maze.grid.length; i++)
		{
			for(int j = 0; j < maze.grid[0].length; j++)
			{
				if(maze.grid[i][j] == false)
				{
					System.out.print("#");
				}
				else
				{
					System.out.print(" ");
				}		
			}	
			System.out.println("");
		}
	}

}
