package main;

public class TowerSpace extends Space{
	
	private Tower tower;
	
	public TowerSpace(int x, int y, String direction){
		super(x, y);
		tower = new BasicTower(this, direction);
	}
	
	public void performAction(){
		//operate tower
		
		//remove tower
		//deposit the salvage value
		
	}
	public Tower getTower()
	{
		return tower;
	}

}
