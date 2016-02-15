
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class Maze {
	
	Integer width = 101;
	Integer length = 101;
	boolean[][] path;
	boolean[][] visit;
	boolean[][] grid;
	List<Integer> startPoint;
	List<Integer> targetPoint;
	List<List<Integer>> unBlocked;
	
	public Maze(int width, int length)
	{
		this.width = width;
		this.length = length;
		initMaze(width,length);
	}
	public void initMaze(Integer width,Integer length){
		
		grid = new boolean[width][length];

		path = new boolean[width][length];
		visit = new boolean[width][length];

		Random rand = new Random();
		startPoint = new ArrayList<Integer>();	
		startPoint.add(rand.nextInt(length));
		startPoint.add(rand.nextInt(width));
		targetPoint = new ArrayList<Integer>();
		targetPoint.add(rand.nextInt(width));
		targetPoint.add(rand.nextInt(length));
		grid[startPoint.get(0)][startPoint.get(1)] = true;
		visit[startPoint.get(0)][startPoint.get(1)] = true;
		dfsGenerate();
	}
	
	public void printMaze(){
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j] == false)
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
	
	public void printPath(){
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j] == false)
				{
					System.out.print("1");
				}
				else if (path[i][j] == true)
				{
					System.out.print("2");
				}		
				else 
				{
					System.out.print("0");
				}
			}	
			System.out.println("");
		}
	}
	
	private void dfsGenerate(){
		Stack<List<Integer>> stack = new Stack<List<Integer>>();
		stack.push(startPoint);
		while(!stack.isEmpty())
		{
			List<Integer> curPoint = stack.pop();
			Random rand = new Random();
			visit[curPoint.get(0)][curPoint.get(1)] = true;
			System.out.println(curPoint);
			int x = curPoint.get(0);
			int y = curPoint.get(1);
			if(curPoint != startPoint)
			{
				if(rand.nextInt(100) > 70){
					grid[x][y] = false;
				}
				else{
					grid[x][y] = true;
				}
			}
			
			List<ArrayList<Integer>>  neiborPoints= new ArrayList<ArrayList<Integer>>();
			if(x + 1 < length)
			{
				if(!visit[x + 1][y])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x + 1);
					tmp.add(y);
					neiborPoints.add(tmp);
				}
			}
			if(x - 1 >= 0)
			{
				if(!visit[x - 1][y])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x - 1);
					tmp.add(y);
					neiborPoints.add(tmp);
				}
			}
			if(y + 1 < width)
			{
				if(!visit[x][y + 1])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x);
					tmp.add(y + 1);
					neiborPoints.add(tmp);
				}
			}
			if(y - 1 > 0)
			{
				if(!visit[x][y - 1])
				{
					ArrayList<Integer> tmp = new ArrayList<Integer>();
					tmp.add(x);
					tmp.add(y - 1);
					neiborPoints.add(tmp);
				}
			}
			
			
			while(neiborPoints.size() > 0)
			{
				int size = neiborPoints.size();
				int index = rand.nextInt(size);
				stack.push(neiborPoints.get(index));
				neiborPoints.remove(index);
				
				
			}
		}
		
		
		
		
	}
	
	
	
}
