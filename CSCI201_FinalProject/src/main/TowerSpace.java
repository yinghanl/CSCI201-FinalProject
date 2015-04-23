package main;

public class TowerSpace extends ClickableSpace{
	
	private Tower tower;
	
	public TowerSpace(int x, int y){
		super(x, y);
		tower = new BasicTower(this);
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
