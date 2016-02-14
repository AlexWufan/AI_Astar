
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Maze {
	
	Integer width = 101;
	Integer length = 101;
	boolean[][] path;
	boolean[][] mutli;
	boolean[][] grid;
	List<Integer> startPoint;
	List<Integer> targetPoint;
	List<List<Integer>> unBlocked;
	
	public Maze(int width, int length)
	{
		initMaze(width,length);
	}
	
	
	public void initMaze(Integer width,Integer length){
		grid = new boolean[width][length];
		mutli = new boolean[width][length];
		Random rand = new Random();
		startPoint = new ArrayList<Integer>();
		
		startPoint.add(rand.nextInt(101));
		startPoint.add(rand.nextInt(101));
		//tmp
		targetPoint = new ArrayList<Integer>();
		targetPoint.add(rand.nextInt(width));
		targetPoint.add(rand.nextInt(length));
		//
		dfsGenerate();
		
	}
	
	private void dfsGenerate(){
	}
	
	
	
}
