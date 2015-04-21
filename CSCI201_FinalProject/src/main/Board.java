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
				if((i == 10 && j <5 ) || (j == 4 && i>4 && i<11) || (i == 5 && j>4 && j <12) || (j == 11 && i<13 && i>5) || (i == 12 && j>11 && j<20) || (j == 19 && i>7 && i<13) || (i == 8 && j>19 && j<32)){
					PathSpace p = new PathSpace(i, j);
					map[i][j] = p;
					creepPath.add(p);
				}
				else{
					map[i][j] = new BlankSpace(i,j);
				}
				//assign some to be mineable spaces
				
			}
		}
		
	}
	
	private void setAdjacencies(){
		for(int i = 0; i<20; i++){
			for(int j = 0; j<32; j++){
					//north
					if(i != 0)
						map[i][j].setNorth(map[i-1][j]);
					else
						map[i][j].setNorth(null);
					
					//south
					if(i != 19)
						map[i][j].setSouth(map[i+1][j]);
					else
						map[i][j].setSouth(null);
					
					//west
					if(j != 0)
						map[i][j].setWest(map[i][j-1]);
					else
						map[i][j].setWest(null);
					
					//east
					if(j <30 )
						map[i][j].setEast(map[i][j+1]);
					else
						map[i][j].setEast(null);
					
					//northwest
					if(i != 0 && j != 0)
						map[i][j].setNorthWest(map[i-1][j-1]);
					else
						map[i][j].setNorthWest(null);
					
					//northeast
					if(i != 0 && j != 31)
						map[i][j].setNorthEast(map[i-1][j+1]);
					else
						map[i][j].setNorthEast(null);
					
					//southeast
					if(i != 19 && j != 31)
						map[i][j].setSouthEast(map[i+1][j+1]);
					else
						map[i][j].setSouthEast(null);
					
					//southwest
					if(i != 19 && j != 0)
						map[i][j].setSouthWest(map[i+1][j-1]);
					else
						map[i][j].setSouthWest(null); 

			}
		}
	}
	
	private void setPathSpaces(){
		for(int i = 0; i<creepPath.size()-1; i++){
			creepPath.get(i).setNext(creepPath.get(i+1));
		}
		creepPath.get(creepPath.size()-1).setNext(null);
	}
	

	public Space getSpace(int x, int y){
		return map[x][y];
	}
	
	public void setPlayer(Player p)
	{
		map[p.getLocation().getX()][p.getLocation().getY()].setOccupant(p);
	}
	
	public void placeTower(int x, int y)
	{
		map[x][y] = new TowerSpace(x, y);
		if(x>0){
			map[x-1][y].setSouth(null);
		}
		if(y>0){
			map[x][y-1].setEast(null);
		}
		if(y<31){
			map[x][y+1].setWest(null);
		}
		if(x<19){
			map[x+1][y].setNorth(null);
		}
		
	}
}
