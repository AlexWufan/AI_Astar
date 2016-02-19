

import java.util.*;
import java.util.Map.Entry;

public class Maze {
	
	Integer width = 101;
	Integer length = 101;
	
	boolean backward = false;
	
	boolean[][] path;
	boolean[][] visit;
	boolean[][] grid;
	boolean[][] expanded;
	boolean[][] closelist;
	List<Integer> startPoint;
	List<Integer> start;
	List<Integer> targetPoint;
	List<List<Integer>> unBlocked;
	int[][][] tree;
	boolean success = true;
	int numOfExpandNodes = 0;
	
	int[][] search;
	int[][] goal;
	int counter = 0;
	
	int[][] hValue;
	
	TreeMap<Integer, List<List<Integer>>> open;
	TreeMap <Integer,List<List<Integer>>> close;

	int gValueOfG = -1;
	
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
		start = startPoint;
		grid[startPoint.get(0)][startPoint.get(1)] = true;
		visit[startPoint.get(0)][startPoint.get(1)] = true;
		dfsGenerate();
		targetPoint = new ArrayList<Integer>();
		targetPoint.add(rand.nextInt(width));
		targetPoint.add(rand.nextInt(length));
		while(!grid[targetPoint.get(0)][targetPoint.get(1)] || targetPoint.equals(startPoint))
		{
			//targetPoint = new ArrayList<Integer>();
			targetPoint.set(0,rand.nextInt(width));
			targetPoint.set(1,rand.nextInt(length));
		}
	}
	private void setCost(List<Integer> s)
	{
		int x = s.get(0);
		int y = s.get(1);
		if(x + 1 < length)
		{
			visit[x + 1][y] = true;
		}
		if(x - 1 >= 0)
		{
			visit[x - 1][y] = true;
		}
		if(y + 1 < width)
		{
			visit[x][y + 1] = true;
		}
		if(y - 1 >= 0)
		{
			visit[x][y - 1] = true;
		}
	}
	
	public void printMaze(){
		for(int i = 0; i < grid.length; i++)
		{
			for(int j = 0; j < grid[0].length; j++)
			{
				if(grid[i][j] == false)
				{

					System.out.print(" ◎");
				}
				else
				{
					System.out.print(" ◇");
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
					System.out.print(" ◎");
				}
				else if (path[i][j] == true)
				{
					System.out.print(" ♞");
				}
				else
				{
					System.out.print(" ◇");
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
			//System.out.println(curPoint);
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
			if(y - 1 >= 0)
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
//		
//		for(int i = 0; i < length; i++)
//		{
//			for(int j = 0; j <width; j++)
//			{
//				if(grid[i][j] == false)
//				{
//					System.out.println("grid[" + i + "]" + "[" + j + "]" + "=" + "false");
//				}
//			}
//		}
		
		
	}
	
	private void ComputePath()
	{
		if(backward == true)
		{
			List<Integer> tmp = startPoint;
			startPoint = targetPoint;
			targetPoint = tmp;
		}
		
		//System.out.println("****");
		Entry <Integer,List<List<Integer>>> first = open.firstEntry();
		List<List<Integer>> pList = first.getValue();
		List<Integer> s = pList.get(0);
		while(goal[targetPoint.get(0)][targetPoint.get(1)] >  first.getKey())
		{
			numOfExpandNodes++;
			//this.expanded[s.get(0)][s.get(1)] = true;
			//System.out.println(s);
			//System.out.println(open);
			if(!close.containsKey(first.getKey()))
				close.put(first.getKey(),first.getValue());
			//open.remove(first.getKey());
			
			pList.remove(s);
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
			}
			else
			{
				open.put(first.getKey(),pList);
			}
			
			int x = s.get(0); int y = s.get(1);
			List<ArrayList<Integer>>  tmp = new ArrayList<ArrayList<Integer>>();
			if(x - 1 >= 0)
			{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x - 1);
					t.add(y);
						tmp.add(t);
	
			}
			
			if(x+ 1 <length )
			{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x + 1);
					t.add(y);
					
						tmp.add(t);

			}
			if(y - 1 >= 0)
			{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x);
					t.add(y -1);
					tmp.add(t);

			}
			if(y + 1 < width)
			{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x );
					t.add(y + 1);
					tmp.add(t);
			}
			
			for(List<Integer> p:tmp)
			{
				if(search[p.get(0)][p.get(1)]  < counter)
				{
					goal[p.get(0)][p.get(1)] = Integer.MAX_VALUE;
					search[p.get(0)][p.get(1)] = counter;
				}
				if(visit[p.get(0)][p.get(1)]&&!grid[p.get(0)][p.get(1)]) continue;
				int cost = 1;
				if(goal[p.get(0)][p.get(1)] > goal[s.get(0)][s.get(1)] + cost)
				{
					//System.out.println(p);
					int g = goal[p.get(0)][p.get(1)];
					goal[p.get(0)][p.get(1)] = goal[s.get(0)][s.get(1)] + cost;
					tree[p.get(0)][p.get(1)][0] = s.get(0);
					tree[p.get(0)][p.get(1)][1] = s.get(1);
					if(open.containsKey(g + h(p)))
					{
						List<List<Integer>> l = open.get(g+ h(p));
						if(l.contains(p))
						{
							l.remove(p);
						}
					}
					List<List<Integer>> l = new ArrayList<List<Integer>>();
					if(open.containsKey(goal[p.get(0)][p.get(1)] + h(p)))
					{
						l = open.get(goal[p.get(0)][p.get(1)] + h(p));
					}
					l.add(p);
					open.put(goal[p.get(0)][p.get(1)] + h(p),l);
				}	
			}
			if(open.size() == 0)
				break;
//			for(int i = 0; i < length; i++)
//			{
//				for(int j = 0; j < width; j++)
//				{
//					System.out.print(tree[i][j][0]);
//					System.out.print(tree[i][j][1]);
//					System.out.print(" ");
//				}
//				System.out.println("");
//			}
			first = open.firstEntry();
			
			pList = first.getValue();
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
				break;
			}
			s = pList.get(0);
			for(List<Integer> n : pList)
			{
				if(goal[n.get(0)][n.get(1)] > goal[s.get(0)][s.get(1)])
					s = n;
			}
//			if(first.getKey() == goal[targetPoint.get(0)][targetPoint.get(1)])
//			{
//				System.out.println("1st");
//			}
		}
		if(backward == true)
		{
			List<Integer> tmp2 = startPoint;
			startPoint = targetPoint;
			targetPoint = tmp2;
		}
		
	}

	private void ComputePath2()
	{
		//System.out.println("****");
		Entry <Integer,List<List<Integer>>> first = open.firstEntry();
		List<List<Integer>> pList = first.getValue();
		List<Integer> s = pList.get(0);
		while(goal[targetPoint.get(0)][targetPoint.get(1)] >  first.getKey())
		{
			numOfExpandNodes++;
			//this.expanded[s.get(0)][s.get(1)] = true;
			//System.out.println(s);
			//System.out.println(open);
			if(!close.containsKey(first.getKey()))
				close.put(first.getKey(),first.getValue());
			//open.remove(first.getKey());

			pList.remove(s);
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
			}
			else
			{
				open.put(first.getKey(),pList);
			}

			int x = s.get(0); int y = s.get(1);
			List<ArrayList<Integer>>  tmp = new ArrayList<ArrayList<Integer>>();
			if(x - 1 >= 0)
			{
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x - 1);
				t.add(y);
				tmp.add(t);

			}

			if(x+ 1 <length )
			{
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x + 1);
				t.add(y);

				tmp.add(t);

			}
			if(y - 1 >= 0)
			{
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x);
				t.add(y -1);
				tmp.add(t);

			}
			if(y + 1 < width)
			{
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x );
				t.add(y + 1);
				tmp.add(t);
			}

			for(List<Integer> p:tmp)
			{
				if(search[p.get(0)][p.get(1)]  < counter)
				{
					goal[p.get(0)][p.get(1)] = Integer.MAX_VALUE;
					search[p.get(0)][p.get(1)] = counter;
				}
				if(visit[p.get(0)][p.get(1)]&&!grid[p.get(0)][p.get(1)]) continue;
				int cost = 1;
				if(goal[p.get(0)][p.get(1)] > goal[s.get(0)][s.get(1)] + cost)
				{
					//System.out.println(p);
					int g = goal[p.get(0)][p.get(1)];
					goal[p.get(0)][p.get(1)] = goal[s.get(0)][s.get(1)] + cost;
					tree[p.get(0)][p.get(1)][0] = s.get(0);
					tree[p.get(0)][p.get(1)][1] = s.get(1);
					if(open.containsKey(g + h(p)))
					{
						List<List<Integer>> l = open.get(g+ h(p));
						if(l.contains(p))
						{
							l.remove(p);
						}
					}
					List<List<Integer>> l = new ArrayList<List<Integer>>();
					if(open.containsKey(goal[p.get(0)][p.get(1)] + h(p)))
					{
						l = open.get(goal[p.get(0)][p.get(1)] + h(p));
					}
					l.add(p);
					open.put(goal[p.get(0)][p.get(1)] + h(p),l);
				}
			}
			if(open.size() == 0)
				break;
			first = open.firstEntry();
			pList = first.getValue();
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
				break;
			}
			s = pList.get(0);
			for(List<Integer> n : pList)
			{
				if(goal[n.get(0)][n.get(1)] < goal[s.get(0)][s.get(1)])
				s = n;
			}
//			if(first.getKey() == goal[targetPoint.get(0)][targetPoint.get(1)])
//			{
//				System.out.println("1st");
//			}
		}
	}


	public void repeatedFAstar()
	{
		this.hValue = new int[width][length];
		path = new boolean[width][length];
		numOfExpandNodes = 0;
		search = new int[width][length];
		goal = new int[width][length];
		visit = new boolean[width][length];
		this.expanded = new boolean[width][length];
		counter = 0;
		setCost(startPoint);
		List<List<Integer>> route_final = new ArrayList<List<Integer>>();
		List<Integer> point;
		while(!startPoint.equals(targetPoint))
		{
			counter = counter + 1;
			search[startPoint.get(0)][startPoint.get(1)] = counter;
			
			search[targetPoint.get(0)][targetPoint.get(1)] = counter;
			if(this.backward == false)
			{
			goal[startPoint.get(0)][startPoint.get(1)] = 0;
			goal[targetPoint.get(0)][targetPoint.get(1)] = Integer.MAX_VALUE;
			}
			else{
				goal[startPoint.get(0)][startPoint.get(1)] = Integer.MAX_VALUE;
				goal[targetPoint.get(0)][targetPoint.get(1)] = 0;
			}
			open = new TreeMap<Integer,List<List<Integer>>>();
			close = new TreeMap();
			List<List<Integer>> L = new ArrayList<List<Integer>> ();
			if(this.backward == true)
			{
				L.add(this.targetPoint);
				open.put(goal[targetPoint.get(0)][targetPoint.get(1)] + h(targetPoint),L);
			}
			else
			{
				L.add(startPoint);
				open.put(goal[startPoint.get(0)][startPoint.get(1)] + h(startPoint),L);
			}
			
			tree = new int[width][length][2];
			ComputePath();

			if(open.isEmpty())
			{	
				System.out.println("Failed");
				success = false;
				break;
			}
			List<List<Integer>> route= new ArrayList<List<Integer>>();
			if(this.backward == false)
			{
				point = targetPoint;


				while(!point.equals(startPoint))
				{
					route.add(point);
					List<Integer>tmp =new ArrayList<Integer>();
					tmp.add(tree[point.get(0)][point.get(1)][0]);
					tmp.add(tree[point.get(0)][point.get(1)][1]);
					point = tmp;
				}
				
				for(int i = route.size() - 1; i >= 0 ; i--)
				{
					List<Integer> p = route.get(i);
					if(grid[p.get(0)][p.get(1)] == false)
					{
						if(i + 1 == route.size())
							System.out.println("out of index");
						startPoint = route.get(i + 1);
						break;
					}
					startPoint = p;
					route_final.add(p);
					setCost(p);
				}
			}
			else
			{
				point = startPoint;
				
				while(!point.equals(targetPoint))
				{
					route.add(point);
					List<Integer>tmp =new ArrayList<Integer>();
					tmp.add(tree[point.get(0)][point.get(1)][0]);
					tmp.add(tree[point.get(0)][point.get(1)][1]);
					point = tmp;
				}
				if(this.backward == true)
					route.add(point);
				
				for(int i = 0; i < route.size() ; i++)
				{
					List<Integer> p = route.get(i);
					if(grid[p.get(0)][p.get(1)] == false)
					{
						if(i - 1 == route.size())
							System.out.println("out of index");
						startPoint = route.get(i - 1);
						break;
					}
					startPoint = p;
					route_final.add(p);
					setCost(p);
				}
				
				
			}
		}
		point = targetPoint;
		if(success)
		{
			for(List<Integer> a : route_final)
			{
				path[a.get(0)][a.get(1)] = true;
			}
//			while(!point.equals(start))
//			{
//				path[point.get(0)][point.get(1)] = true;
//				List<Integer>tmp =new ArrayList<Integer>();
//				tmp.add(tree[point.get(0)][point.get(1)][0]);
//				tmp.add(tree[point.get(0)][point.get(1)][1]);
//				point = tmp;
//			}
			System.out.println("I reached the target");
		}
		this.printPath();
		System.out.println("Number of expanded nodes:" + Integer.toString(numOfExpandNodes));
		startPoint = start;
	}

	public void repeatedFAstar2()
	{
		this.hValue = new int[width][length];
		path = new boolean[width][length];
		numOfExpandNodes = 0;
		search = new int[width][length];
		goal = new int[width][length];
		visit = new boolean[width][length];
		this.expanded = new boolean[width][length];
		counter = 0;
		setCost(startPoint);
		List<List<Integer>> route_final = new ArrayList<List<Integer>>();
		List<Integer> point;
		while(!startPoint.equals(targetPoint))
		{
			counter = counter + 1;
			search[startPoint.get(0)][startPoint.get(1)] = counter;

			search[targetPoint.get(0)][targetPoint.get(1)] = counter;
			if(this.backward == false)
			{
				goal[startPoint.get(0)][startPoint.get(1)] = 0;
				goal[targetPoint.get(0)][targetPoint.get(1)] = Integer.MAX_VALUE;
			}
			else{
				goal[startPoint.get(0)][startPoint.get(1)] = Integer.MAX_VALUE;
				goal[targetPoint.get(0)][targetPoint.get(1)] = 0;
			}
			open = new TreeMap<Integer,List<List<Integer>>>();
			close = new TreeMap();
			List<List<Integer>> L = new ArrayList<List<Integer>> ();
			if(this.backward == true)
			{
				L.add(this.targetPoint);
				open.put(goal[targetPoint.get(0)][targetPoint.get(1)] + h(targetPoint),L);
			}
			else
			{
				L.add(startPoint);
				open.put(goal[startPoint.get(0)][startPoint.get(1)] + h(startPoint),L);
			}

			tree = new int[width][length][2];
			ComputePath2();

			if(open.isEmpty())
			{
				System.out.println("Failed");
				success = false;
				break;
			}
			List<List<Integer>> route= new ArrayList<List<Integer>>();
			if(this.backward == false)
			{
				point = targetPoint;


				while(!point.equals(startPoint))
				{
					route.add(point);
					List<Integer>tmp =new ArrayList<Integer>();
					tmp.add(tree[point.get(0)][point.get(1)][0]);
					tmp.add(tree[point.get(0)][point.get(1)][1]);
					point = tmp;
				}

				for(int i = route.size() - 1; i >= 0 ; i--)
				{
					List<Integer> p = route.get(i);
					if(grid[p.get(0)][p.get(1)] == false)
					{
						if(i + 1 == route.size())
							System.out.println("out of index");
						startPoint = route.get(i + 1);
						break;
					}
					startPoint = p;
					route_final.add(p);
					setCost(p);
				}
			}
			else
			{
				point = startPoint;

				while(!point.equals(targetPoint))
				{
					route.add(point);
					List<Integer>tmp =new ArrayList<Integer>();
					tmp.add(tree[point.get(0)][point.get(1)][0]);
					tmp.add(tree[point.get(0)][point.get(1)][1]);
					point = tmp;
				}
				if(this.backward == true)
					route.add(point);

				for(int i = 0; i < route.size() ; i++)
				{
					List<Integer> p = route.get(i);
					if(grid[p.get(0)][p.get(1)] == false)
					{
						if(i - 1 == route.size())
							System.out.println("out of index");
						startPoint = route.get(i - 1);
						break;
					}
					startPoint = p;
					route_final.add(p);
					setCost(p);
				}


			}
		}
		point = targetPoint;
		if(success)
		{
			for(List<Integer> a : route_final)
			{
				path[a.get(0)][a.get(1)] = true;
			}
//			while(!point.equals(start))
//			{
//				path[point.get(0)][point.get(1)] = true;
//				List<Integer>tmp =new ArrayList<Integer>();
//				tmp.add(tree[point.get(0)][point.get(1)][0]);
//				tmp.add(tree[point.get(0)][point.get(1)][1]);
//				point = tmp;
//			}
			System.out.println("I reached the target");
		}
		this.printPath();
		System.out.println("Number of expanded nodes:" + Integer.toString(numOfExpandNodes));
		startPoint = start;
	}

	public void repeatedBAstar()
	{
		backward = true;
		//List<Integer> tmp = startPoint;
		//startPoint = targetPoint;
		//start = targetPoint;
		//targetPoint = tmp;
		repeatedFAstar();
		//tmp = start;
		//start = targetPoint;
		//startPoint = start;
		//targetPoint = tmp;
		backward = false;
	}

	private void adaptiveComputePath()
	{
		List<List<Integer>> expandedNodes = new ArrayList<List<Integer>>();
		//System.out.println("***");
		Entry <Integer,List<List<Integer>>> first = open.firstEntry();
		List<List<Integer>> pList = first.getValue();
		List<Integer> s = pList.get(0);
		
		while(goal[targetPoint.get(0)][targetPoint.get(1)] >  first.getKey())
		{
			
			numOfExpandNodes++;
			if(!close.containsKey(first.getKey()))
				close.put(first.getKey(),first.getValue());
			//open.remove(first.getKey());
			expanded[s.get(0)][s.get(1)] = true;
			expandedNodes.add(s);
			
			//System.out.println(s);
			//System.out.println(open);
			pList.remove(s);
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
			}
			else
			{
				open.put(first.getKey(),pList);
			}
			
			int x = s.get(0); int y = s.get(1);
			List<ArrayList<Integer>>  tmp = new ArrayList<ArrayList<Integer>>();
			if(x - 1 >= 0)
			{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x - 1);
					t.add(y);
						tmp.add(t);
			}
			
			if(x+ 1 <length )
			{
	
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x + 1);
					t.add(y);
					
						tmp.add(t);
		
			}
			if(y - 1 >= 0)
			{

					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x);
					t.add(y -1);
					tmp.add(t);
	
			}
			if(y + 1 < width)
			{
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x );
					t.add(y + 1);
					tmp.add(t);
			}
			
			for(List<Integer> p:tmp)
			{
				//if(path[p.get(0)][p.get(1)] == true) continue;
				if(search[p.get(0)][p.get(1)]  < counter)
				{
					goal[p.get(0)][p.get(1)] = Integer.MAX_VALUE;
					search[p.get(0)][p.get(1)] = counter;
				}
				
				if(visit[p.get(0)][p.get(1)]&&!grid[p.get(0)][p.get(1)]) continue;
				int cost = 1;
				if(goal[p.get(0)][p.get(1)] > goal[s.get(0)][s.get(1)] + cost)
				{
					int g = goal[p.get(0)][p.get(1)];
					goal[p.get(0)][p.get(1)] = goal[s.get(0)][s.get(1)] + cost;
					tree[p.get(0)][p.get(1)][0] = s.get(0);
					tree[p.get(0)][p.get(1)][1] = s.get(1);
					if(open.containsKey(g + hNew(p)))
					{
						List<List<Integer>> l = open.get(g + hNew(p));
						if(l.contains(p))
						{
							l.remove(p);
						}
					}
					List<List<Integer>> l = new ArrayList<List<Integer>>();
					if(open.containsKey(goal[p.get(0)][p.get(1)] + hNew(p)))
					{
						l = open.get(goal[p.get(0)][p.get(1)] + hNew(p));
					}
					l.add(p);
					open.put(goal[p.get(0)][p.get(1)] + hNew(p),l);
				}	
			}
			if(open.size() == 0)
				break;
			first = open.firstEntry();
			pList = first.getValue();//list of nodes with same f value;
			if(pList.size() == 0)
			{
				open.remove(first.getKey());
				break;
			}
			s = pList.get(0);
			///choose a strategy to pick nodes that have same values
			for(List<Integer> n : pList)
			{
				if(goal[n.get(0)][n.get(1)] > goal[s.get(0)][s.get(1)]);
					s = n;
			}
			
			
		}
		gValueOfG =  goal[targetPoint.get(0)][targetPoint.get(1)];
		for(List<Integer> node:expandedNodes)
		{
			int h = gValueOfG - this.goal[node.get(0)][node.get(1)];
			if(this.hValue[node.get(0)][node.get(1)] != 0)
			{
				this.hValue[node.get(0)][node.get(1)] = this.hValue[node.get(0)][node.get(1)] < h ? this.hValue[node.get(0)][node.get(1)] : h;
			}
			else
			{
				this.hValue[node.get(0)][node.get(1)] = h;
			}
		}
//		for(int i = 0; i < length; i++)
//		{
//			for(int j = 0; j < width; j++)
//			{
//				System.out.print(this.hValue[i][j]);
//				System.out.print(" ");
//			}
//			System.out.println("");
//		}
		
	}

	public void adaptiveAstar()
	{
		this.hValue = new int[width][length];
		path = new boolean[width][length];
		numOfExpandNodes = 0;
		search = new int[width][length];
		goal = new int[width][length];
		visit = new boolean[width][length];
		
		counter = 0;
		gValueOfG = -1;
		
		setCost(startPoint);
		List<Integer> point;
		List<List<Integer>> route_final = new ArrayList<List<Integer>>();
		while(!startPoint.equals(targetPoint))
		{
			tree = new int[width][length][2];
			counter = counter + 1;
			search[startPoint.get(0)][startPoint.get(1)] = counter;
			goal[startPoint.get(0)][startPoint.get(1)] = 0;
			search[targetPoint.get(0)][targetPoint.get(1)] = counter;
			goal[targetPoint.get(0)][targetPoint.get(1)] = Integer.MAX_VALUE;
			open = new TreeMap<Integer,List<List<Integer>>>();
			close = new TreeMap();
			expanded = new boolean[width][length];
			List<List<Integer>> L = new ArrayList<List<Integer>> ();
	
				L.add(startPoint);
				open.put(goal[startPoint.get(0)][startPoint.get(1)] + h(startPoint),L);
	
			adaptiveComputePath();

			if(open.isEmpty())
			{	
				System.out.println("Failed");
				success = false;
				break;
			}
			List<List<Integer>> route= new ArrayList<List<Integer>>();
			point = targetPoint;
//			for(int i = 0; i < length; i++)
//			{
//				for(int j = 0; j < width; j++)
//				{
//					System.out.print(tree[i][j][0]);
//					System.out.print(tree[i][j][1]);
//					System.out.print(" ");
//				}
//				System.out.println("");
//			}
//			for(int i = 0; i < length; i++)
//			{
//				for(int j = 0; j < width; j++)
//				{
//					System.out.print(expanded[i][j]);
//					//System.out.print(expanded[i][j][1]);
//					System.out.print(" ");
//				}
//				System.out.println("");
//			}
			
			while(!point.equals(startPoint))
			{
				route.add(point);
				List<Integer>tmp =new ArrayList<Integer>();
				tmp.add(tree[point.get(0)][point.get(1)][0]);
				tmp.add(tree[point.get(0)][point.get(1)][1]);
				point = tmp;
			}
			
			for(int i = route.size() - 1; i >= 0 ; i--)
			{
				List<Integer> p = route.get(i);
				if(grid[p.get(0)][p.get(1)] == false)
				{
					if(i + 1 == route.size())
						System.out.println("out of index");
					startPoint = route.get(i + 1);
					break;
				}
				route_final.add(p);
				startPoint = p;
				//path[p.get(0)][p.get(1)] = true;
				setCost(p);
			}
		}
		point = targetPoint;
		if(success)
		{
			for(List<Integer> a : route_final)
			{
				path[a.get(0)][a.get(1)] = true;
			}
//			while(!point.equals(start))
//			{
//				path[point.get(0)][point.get(1)] = true;
//				List<Integer>tmp =new ArrayList<Integer>();
//				tmp.add(tree[point.get(0)][point.get(1)][0]);
//				tmp.add(tree[point.get(0)][point.get(1)][1]);
//				point = tmp;
//			}
			System.out.println("I reached the target");
		}
		System.out.println("");
		this.printPath();
		System.out.println("Number of expanded nodes:" + Integer.toString(numOfExpandNodes));
		startPoint = start;//reset the start Point;
	}
	
	public void initTestCase()
	{
		this.width = 10;
		this.length = 10;
		this.startPoint = new ArrayList<Integer>();
		this.startPoint.add(2);
		this.startPoint.add(2);
		this.start = this.startPoint;
		this.targetPoint = new ArrayList<Integer>();
		this.targetPoint.add(2);
		this.targetPoint.add(4);
		this.grid = new boolean[width][length];
		for(int i = 0; i < length; i++)
		{
			for (int j =0; j < width; j++)
			{
				grid[i][j] = true;
			}
		}
		grid[1][6]=false;
				grid[1][7]=false;
				grid[2][1]=false;
				grid[2][3]=false;
				grid[2][5]=false;
				grid[2][8]=false;
				grid[2][9]=false;
				grid[3][3]=false;
				grid[3][7]=false;
				grid[4][1]=false;
				grid[4][3]=false;
				grid[4][4]=false;
				grid[4][6]=false;
				grid[4][7]=false;
				grid[5][0]=false;
				grid[5][8]=false;
				grid[6][5]=false;
				grid[6][6]=false;
				grid[6][8]=false;
				grid[7][0]=false;
				grid[7][4]=false;
				grid[7][8]=false;
				grid[7][9]=false;
				grid[8][3]=false;
				grid[8][4]=false;
				grid[8][9]=false;
				grid[9][0]=false;
				grid[9][5]=false;
				grid[9][6]=false;
				grid[9][7]=false;
		path = new boolean[width][length];
		visit = new boolean[width][length];
		//grid[startPoint.get(0)][startPoint.get(1)] = true;
		visit[startPoint.get(0)][startPoint.get(1)] = true;
		

		
		
	}
	// calculate nomarl H value
	private int h(List<Integer> s)
	{
		return Math.abs(targetPoint.get(1) - s.get(1)) + Math.abs(targetPoint.get(0) - s.get(0));
	}
	// caculate hNew value
	private int hNew(List<Integer> s)
	{
		if(this.hValue[s.get(0)][s.get(1)] > 0)
			return this.hValue[s.get(0)][s.get(1)] > h(s) ? this.hValue[s.get(0)][s.get(1)] : h(s);
//		if(gValueOfG > 0 && expanded[s.get(0)][s.get(1)])
//		{
//			int ret = (gValueOfG - goal[s.get(0)][s.get(1)]) > h(s) ? (gValueOfG - goal[s.get(0)][s.get(1)]):h(s);
//			//this.hValue[s.get(0)][s.get(1)] = ret;
//			return ret;
//		}
		else 
			return h(s);
	}

}






















