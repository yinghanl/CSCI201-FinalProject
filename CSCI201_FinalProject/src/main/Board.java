package main;

import java.util.ArrayList;

public class Board {
	private Space [][] map;
	private ArrayList<PathSpace> creepPath;
	
	public Board()
	{
		//this.getCreepPath();
		creepPath = new ArrayList<PathSpace>();
		this.getMap();
		this.setAdjacencies();
		this.setPathSpaces();
	}
	
	private void getMap()
	{
		map = new Space[20][32];
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 32;j++)
			{
				if((i == 14 && j <5 ) || (j == 4 && i>4 && i<14) || (i == 5 && j>4 && j <12) || (j == 11 && i<17 && i>5) || (i == 16 && j>11 && j<21) || (j == 20 && i>9 && i<16) || (i == 10 && j>20 && j<32)){
					PathSpace p = new PathSpace(i, j);
					map[i][j] = p;
					creepPath.add(p);
				}
				else{
					map[i][j] = new BlankSpace(i,j);
				}
				//assign some to be 
				
			}
		}
		
	}
	
	private void setAdjacencies(){
		for(int i = 0; i<20; i++){
			for(int j = 0; j<32; j++){
				if(!(map[i][j] instanceof PathSpace)){
					//north
					if(i != 0 && !(map[i-1][j] instanceof PathSpace))
						map[i][j].setNorth(map[i-1][j]);
					else
						map[i][j].setNorth(null);
					
					//south
					if(i != 19 && !(map[i+1][j] instanceof PathSpace))
						map[i][j].setSouth(map[i+1][j]);
					else
						System.out.println("setting south to null for: " + i + " "+j);
						map[i][j].setSouth(null);
					
					//west
					if(j != 0 && !(map[i][j-1] instanceof PathSpace))
						map[i][j].setWest(map[i][j-1]);
					else
						map[i][j].setWest(null);
					
					//east
					if(j != 31 && !(map[i][j+1] instanceof PathSpace))
						map[i][j].setEast(map[i][j+1]);
					else
						map[i][j].setEast(null);
					
					//northwest
					if(i != 0 && j != 0 && !(map[i-1][j-1] instanceof PathSpace))
						map[i][j].setNorthWest(map[i-1][j-1]);
					else
						map[i][j].setNorthWest(null);
					
					//northeast
					if(i != 0 && j != 31 && !(map[i-1][j+1] instanceof PathSpace))
						map[i][j].setNorthEast(map[i-1][j+1]);
					else
						map[i][j].setNorthEast(null);
					
					//southeast
					if(i != 19 && j != 31 && !(map[i+1][j+1] instanceof PathSpace))
						map[i][j].setSouthEast(map[i+1][j+1]);
					else
						map[i][j].setSouthEast(null);
					
					//southwest
					if(i != 19 && j != 0 && !(map[i+1][j-1] instanceof PathSpace))
						map[i][j].setSouthWest(map[i+1][j-1]);
					else
						map[i][j].setSouthWest(null);
				}

			}
		}
	}
	

/*	
	private void getCreepPath()
	{
		creepPath = new ArrayList<PathSpace>();
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
*/
	
	private void setPathSpaces(){
		for(int i = 0; i<creepPath.size()-1; i++){
			creepPath.get(i).setNext(creepPath.get(i+1));
		}
		creepPath.get(creepPath.size()-1).setNext(null);
	}
	

	public Space getSpace(int x, int y){
		return map[x][y];
	}
	
	public void setPlayer(int x, int y, String playerName)
	{
		map[x][y].setOccupant(new Player(playerName));
	}
}
