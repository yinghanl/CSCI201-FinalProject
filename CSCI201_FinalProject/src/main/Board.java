package main;

import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Space [][] map;
	private PathSpace [][] tempMap;
	private ArrayList<PathSpace> creepPath;
	
	public Board()
	{
		//this.getCreepPath();
		creepPath = new ArrayList<PathSpace>();
		this.getMap();
		this.setAdjacencies();
		this.setPathSpaces();
		this.setMineableSpaces();
	}

	public void setMineableSpaces()
	{
		this.setMineable(map[1][1]);
	}
	
	public void setBlank(Space s)
	{
		int x = s.getX();
		int y = s.getY();
		if(map[x][y] instanceof PathSpace){
			return;
		}
		map[x][y] = new BlankSpace(x, y);
		
		setAdjacency(map[x][y], x, y);
		
		if(x>0){
			map[x-1][y].setSouth(map[x][y]);
		}
		if(y>0){
			map[x][y-1].setEast(map[x][y]);
		}
		if(y<31){
			map[x][y+1].setWest(map[x][y]);
		}
		if(x<19){
			map[x+1][y].setNorth(map[x][y]);
		}
		if(x<19 && y>0){
			map[x+1][y-1].setNorthEast(map[x][y]);
		}
		if(x<19 && y<31){
			map[x+1][y+1].setNorthWest(map[x][y]);
		}
		if(x>0 && y<31){
			map[x-1][y+1].setSouthWest(map[x][y]);
		}
		if(x>0 && y>0){
			map[x-1][y-1].setSouthEast(map[x][y]);
		}
	}
	
	
	private void setMineable(Space s)
	{
		int x = s.getX();
		int y = s.getY();
		if(map[x][y] instanceof PathSpace){
			return;
		}
		map[x][y] = new MineableSpace(x, y, 10, 1);
		
		setAdjacency(map[x][y], x, y);
		
		if(x>0){
			map[x-1][y].setSouth(map[x][y]);
		}
		if(y>0){
			map[x][y-1].setEast(map[x][y]);
		}
		if(y<31){
			map[x][y+1].setWest(map[x][y]);
		}
		if(x<19){
			map[x+1][y].setNorth(map[x][y]);
		}
		if(x<19 && y>0){
			map[x+1][y-1].setNorthEast(map[x][y]);
		}
		if(x<19 && y<31){
			map[x+1][y+1].setNorthWest(map[x][y]);
		}
		if(x>0 && y<31){
			map[x-1][y+1].setSouthWest(map[x][y]);
		}
		if(x>0 && y>0){
			map[x-1][y-1].setSouthEast(map[x][y]);
		}
	}
	
	private void getMap()
	{
		map = new Space[20][32];
		tempMap = new PathSpace[20][32];
		for(int i = 0; i < 20; i++)
		{
			for(int j = 0; j < 32;j++)
			{
				if((i == 10 && j <5 ) || (j == 4 && i>4 && i<11) || (i == 5 && j>4 && j <12) || (j == 11 && i<13 && i>5) || (i == 12 && j>11 && j<20) || (j == 19 && i>7 && i<13) || (i == 8 && j>19 && j<32)){
					PathSpace p = new PathSpace(i, j);
					map[i][j] = p;
					tempMap[i][j] = p;
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
		for(int j = 0; j<5; j++){
			creepPath.add(tempMap[10][j]);
		}
		for(int i = 9; i>4; i--){
			creepPath.add(tempMap[i][4]);
		}
		for(int j = 5; j<12; j++){
			creepPath.add(tempMap[5][j]);
		}
		for(int i = 6; i<13; i++){
			creepPath.add(tempMap[i][11]);
		}
		for(int j = 12; j<20; j++){
			creepPath.add(tempMap[12][j]);
		}
		for(int i = 11; i>7; i--){
			creepPath.add(tempMap[i][19]);
		}
		for(int j = 20; j<32; j++){
			creepPath.add(tempMap[8][j]);
		}
		for(int i = 0; i<creepPath.size()-2; i++){
			creepPath.get(i).setNext(creepPath.get(i+1));
		}
		creepPath.get(creepPath.size()-1).setNext(null);
		creepPath.get(creepPath.size()-2).setNext(null);
	}
	
	public Space getSpace(int x, int y){
		return map[x][y];
	}
	
	public PathSpace getPathSpace(int x){
		return creepPath.get(x);
	}
	
	public int getCreepPathSize(){
		return creepPath.size();
	}
	
	public void setPlayer(Player p)
	{
		map[p.getLocation().getX()][p.getLocation().getY()].setOccupant(p);
	}
	
	public void placeTower(Space s)
	{
		int x = s.getX();
		int y = s.getY();
		if(map[x][y] instanceof PathSpace){
			return;
		}
		map[x][y] = new TowerSpace(x, y);
		
		setAdjacency(map[x][y], x, y);
		
		if(x>0){
			map[x-1][y].setSouth(map[x][y]);
		}
		if(y>0){
			map[x][y-1].setEast(map[x][y]);
		}
		if(y<31){
			map[x][y+1].setWest(map[x][y]);
		}
		if(x<19){
			map[x+1][y].setNorth(map[x][y]);
		}
		if(x<19 && y>0){
			map[x+1][y-1].setNorthEast(map[x][y]);
		}
		if(x<19 && y<31){
			map[x+1][y+1].setNorthWest(map[x][y]);
		}
		if(x>0 && y<31){
			map[x-1][y+1].setSouthWest(map[x][y]);
		}
		if(x>0 && y>0){
			map[x-1][y-1].setSouthEast(map[x][y]);
		}
		
		
	}
	
	private void setAdjacency(Space s, int i, int j){
		//north
		if(i != 0)
			s.setNorth(map[i-1][j]);
		else
			s.setNorth(null);
		
		//south
		if(i != 19)
			s.setSouth(map[i+1][j]);
		else
			s.setSouth(null);
		
		//west
		if(j != 0)
			s.setWest(map[i][j-1]);
		else
			s.setWest(null);
		
		//east
		if(j <30 )
			s.setEast(map[i][j+1]);
		else
			s.setEast(null);
		
		//northwest
		if(i != 0 && j != 0)
			s.setNorthWest(map[i-1][j-1]);
		else
			s.setNorthWest(null);
		
		//northeast
		if(i != 0 && j != 31)
			s.setNorthEast(map[i-1][j+1]);
		else
			s.setNorthEast(null);
		
		//southeast
		if(i != 19 && j != 31)
			s.setSouthEast(map[i+1][j+1]);
		else
			s.setSouthEast(null);
		
		//southwest
		if(i != 19 && j != 0)
			s.setSouthWest(map[i+1][j-1]);
		else
			s.setSouthWest(null); 
	}
}
