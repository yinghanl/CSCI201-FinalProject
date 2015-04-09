package main;

import java.util.ArrayList;

public class Board {
	private Space [][] map;
	private ArrayList<PathSpace> creepPath;
	
	public Board()
	{
		this.getCreepPath();
	}
	
	private void getMap()
	{
		map = new Space[20][32];
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 32;j++)
			{
				map[i][j] = new BlankSpace(i,j);
			}
		}
	}
	
	private void getCreepPath()
	{
		creepPath.add(new PathSpace(14,0));
		creepPath.add(new PathSpace(14,1));
		creepPath.add(new PathSpace(14,2));
		creepPath.add(new PathSpace(14,3));
		creepPath.add(new PathSpace(14,4));

		creepPath.add(new PathSpace(13,4));
		creepPath.add(new PathSpace(12,4));
		creepPath.add(new PathSpace(11,4));
		creepPath.add(new PathSpace(10,4));
		creepPath.add(new PathSpace(9,4));
		creepPath.add(new PathSpace(8,4));
		creepPath.add(new PathSpace(7,4));
		creepPath.add(new PathSpace(6,4));
		creepPath.add(new PathSpace(5,4));

		creepPath.add(new PathSpace(5,5));
		creepPath.add(new PathSpace(5,6));
		creepPath.add(new PathSpace(5,7));
		creepPath.add(new PathSpace(5,8));
		creepPath.add(new PathSpace(5,9));
		creepPath.add(new PathSpace(5,10));
		creepPath.add(new PathSpace(5,11));

		creepPath.add(new PathSpace(6,11));
		creepPath.add(new PathSpace(7,11));
		creepPath.add(new PathSpace(8,11));
		creepPath.add(new PathSpace(9,11));
		creepPath.add(new PathSpace(10,11));
		creepPath.add(new PathSpace(11,11));
		creepPath.add(new PathSpace(12,11));
		creepPath.add(new PathSpace(13,11));
		creepPath.add(new PathSpace(14,11));
		creepPath.add(new PathSpace(15,11));
		creepPath.add(new PathSpace(16,11));

		creepPath.add(new PathSpace(16,12));
		creepPath.add(new PathSpace(16,13));
		creepPath.add(new PathSpace(16,14));
		creepPath.add(new PathSpace(16,15));
		creepPath.add(new PathSpace(16,16));
		creepPath.add(new PathSpace(16,17));
		creepPath.add(new PathSpace(16,18));
		creepPath.add(new PathSpace(16,19));
		creepPath.add(new PathSpace(16,20));
		
		creepPath.add(new PathSpace(15,20));
		creepPath.add(new PathSpace(14,20));
		creepPath.add(new PathSpace(13,20));
		creepPath.add(new PathSpace(12,20));
		creepPath.add(new PathSpace(11,20));
		creepPath.add(new PathSpace(10,20));

		creepPath.add(new PathSpace(10,21));
		creepPath.add(new PathSpace(10,22));
		creepPath.add(new PathSpace(10,23));
		creepPath.add(new PathSpace(10,24));
		creepPath.add(new PathSpace(10,25));
		creepPath.add(new PathSpace(10,26));
		creepPath.add(new PathSpace(10,27));
		creepPath.add(new PathSpace(10,28));
		creepPath.add(new PathSpace(10,29));
		creepPath.add(new PathSpace(10,30));
		creepPath.add(new PathSpace(10,31));
	}
	
}
